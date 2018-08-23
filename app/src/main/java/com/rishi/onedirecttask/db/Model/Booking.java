package com.rishi.onedirecttask.db.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Flight.class,parentColumns = "id",childColumns = "flightId",onDelete = CASCADE))
public class Booking {

    @PrimaryKey
    public Integer id;
    public Integer flightId;
    public Integer noOfPassengers;
    public String userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(Integer noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }
}
