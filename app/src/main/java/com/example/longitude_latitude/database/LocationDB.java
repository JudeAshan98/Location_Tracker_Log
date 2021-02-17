package com.example.longitude_latitude.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.longitude_latitude.database.dao.locationDAO;
import com.example.longitude_latitude.model.LocationModel;
import com.example.longitude_latitude.util.Constants;
import com.example.longitude_latitude.util.DateRoomConverter;


@Database(entities = { LocationModel.class}, version = 1, exportSchema = false)
@TypeConverters({DateRoomConverter.class})

public abstract class LocationDB extends RoomDatabase {

    public abstract locationDAO getLocationDao();


    private static LocationDB loc_db;

    // synchronized is use to avoid concurrent access in multithreaded environment
    public static /*synchronized*/ LocationDB getInstance(Context context) {
        if (null == loc_db) {
            loc_db = buildDatabaseInstance(context);
        }
        return loc_db;
    }

    private static LocationDB buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                LocationDB.class,
                Constants.DB_NAME).allowMainThreadQueries().build();
    }

    public  void cleanUp(){
        loc_db = null;
    }

}

