package com.rishi.onedirecttask.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.rishi.onedirecttask.db.Model.Airline;
import com.rishi.onedirecttask.db.Model.Airport;
import com.rishi.onedirecttask.db.Model.Booking;
import com.rishi.onedirecttask.db.Model.Flight;

@Database(entities = {Airline.class, Airport.class, Booking.class, Flight.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract MyDao myDao();

}
