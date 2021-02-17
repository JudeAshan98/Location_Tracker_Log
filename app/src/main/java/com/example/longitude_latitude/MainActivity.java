package com.example.longitude_latitude;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.longitude_latitude.database.LocationDB;
import com.example.longitude_latitude.model.LocationModel;

import java.lang.ref.WeakReference;

public class MainActivity extends Activity {

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    Button Log_btn;

    // GPSTracker class
    GPSTracker gps;
    private String URL="";


    // Database
    private LocationDB locationDB;
    private LocationModel locationModel;
    private boolean update;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationDB = LocationDB.getInstance(MainActivity.this);
       /* try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
               // execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        btnShowLocation = (Button) findViewById(R.id.button);
        Log_btn         = (Button) findViewById(R.id.log);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                  //  https://www.google.com/maps/?q=-15.623037,18.388672
                    URL = "https://www.google.com/maps/?q="+ latitude + "," + longitude;


                    //Insert to the DB

                    locationModel = new LocationModel(longitude, latitude, URL);
                    new InsertTask(MainActivity.this, locationModel).execute();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });


        Log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Log.class);
                startActivity(intent);
            }
        });
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<MainActivity> activityReference;
        private LocationModel Location_models;

        // only retain a weak reference to the activity
        InsertTask(MainActivity context, LocationModel Location) {
            activityReference = new WeakReference<>(context);
            this.Location_models = Location;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented Location id
            long j = activityReference.get().locationDB.getLocationDao().insertLocation(Location_models);
            Location_models.setLog_id(j);
            android.util.Log.e("ID ", "doInBackground: " + j);
            return true;
        }

        // onPostExecute runs on main thread
     /*   @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                activityReference.get().setResult(Location, 1);
                activityReference.get().finish();
            }
        }*/
    }

    private void setResult(LocationModel Location, int flag) {
        setResult(flag, new Intent().putExtra("Location", Location));
        finish();
    }

}