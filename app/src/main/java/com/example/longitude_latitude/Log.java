package com.example.longitude_latitude;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.longitude_latitude.adapter.LocationAdapter;
import com.example.longitude_latitude.database.LocationDB;
import com.example.longitude_latitude.model.LocationModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Log extends AppCompatActivity {



    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private LocationDB locationDB;
    private List<LocationModel> locationModels;
    private LocationAdapter locationAdapter;
    private int pos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        initializeVies();
        displayList();
    }




    private void displayList() {
        locationDB = LocationDB.getInstance(Log.this);
        new RetrieveTask(this).execute();
    }

    private void initializeVies() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
      //  textViewMsg = (TextView) findViewById(R.id.tv__empty);
     //   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
     //   fab.setOnClickListener(listener);
        recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager((Log.this)));
        locationModels = new ArrayList<>();
        locationAdapter = new LocationAdapter(locationModels, Log.this);
        recyclerView.setAdapter(locationAdapter);
    }


    private static class RetrieveTask extends AsyncTask<Void, Void, List<LocationModel>> {

        private WeakReference<Log> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(Log context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<LocationModel> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().locationDB.getLocationDao().getLocation();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<LocationModel> notes) {
            if (notes != null && notes.size() > 0) {
                activityReference.get().locationModels.clear();
                activityReference.get().locationModels.addAll(notes);
                // hides empty text view
//                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().locationAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                locationModels.add((LocationModel) data.getSerializableExtra("Location"));
            } else if (resultCode == 2) {
                locationModels.set(pos, (LocationModel) data.getSerializableExtra("Location"));
            }
            listVisibility();
        }
    }

    private void listVisibility() {
        int emptyMsgVisibility = View.GONE;
        if (locationModels.size() == 0) { // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        locationAdapter.notifyDataSetChanged();
    }

}