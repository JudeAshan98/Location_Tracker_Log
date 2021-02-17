package com.example.longitude_latitude.model;

import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

import com.example.longitude_latitude.util.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = Constants.TABLE_NAME_LOCATION)
public class LocationModel implements Serializable {

    @PrimaryKey (autoGenerate = true)
    private long log_id;

//    @SerializedName("Longitude")
    private double longitude;

//    @SerializedName("Latitude")
    private double latitude;

//    @SerializedName("URL")
    private String url;

//    @SerializedName("Time")
    private Date time;

    public LocationModel(double longitude, double latitude, String url) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.url      = url;
        this.time = new Date(System.currentTimeMillis());
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
