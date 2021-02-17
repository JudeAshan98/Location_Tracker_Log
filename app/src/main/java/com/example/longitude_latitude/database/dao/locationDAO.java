package com.example.longitude_latitude.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.longitude_latitude.model.LocationModel;
import com.example.longitude_latitude.util.Constants;

import java.util.List;

@Dao
public interface locationDAO {

    @Query("SELECT * FROM " + Constants.TABLE_NAME_LOCATION)
    List<LocationModel> getLocation();

    @Insert
    long insertLocation(LocationModel loc);

    @Update
    void updateLocation(LocationModel loc);

    @Delete
    void deleteLocation(LocationModel loc);

    @Delete
    void deletLocations(LocationModel ... loc);


}

