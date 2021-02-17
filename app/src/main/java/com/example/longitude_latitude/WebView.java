package com.example.longitude_latitude;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.longitude_latitude.model.LocationModel;

public class WebView extends AppCompatActivity {

    LocationModel Location_model;
    String Loc_URL;
    double latitude, Longitude;
    long id;
    android.webkit.WebView Mywebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        if (getIntent().getExtras() != null) {
            Location_model = (LocationModel) getIntent().getExtras().getSerializable("Issue_Pallet");
            Loc_URL = Location_model.getUrl();
            latitude = Location_model.getLatitude();
            Longitude = Location_model.getLongitude();
            id = Location_model.getLog_id();

            Toast.makeText(this,Loc_URL+ "",Toast.LENGTH_LONG).show();

        }

        Mywebview =(android.webkit.WebView) findViewById(R.id.web);
        WebSettings webSettings = Mywebview.getSettings();
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        Mywebview.loadUrl(Loc_URL);
        Mywebview.setWebViewClient(new WebViewClient());
    }
}