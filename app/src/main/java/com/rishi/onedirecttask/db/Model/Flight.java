package com.rishi.onedirecttask.db.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = { @ForeignKey(entity = Airport.class,parentColumns = "id",childColumns = "sourceId",onDelete = CASCADE),
        @ForeignKey(entity = Airport.class,parentColumns = "id",childColumns = "destId",onDelete = CASCADE),
        @ForeignKey(entity = Airline.class,parentColumns = "id",childColumns = "airlineId",onDelete = CASCADE) })
public class Flight {

    @PrimaryKey
    public Integer id;
    public Integer sourceId;
    public Integer destId;
    public Long sourceTime;
    public Long destTime;
    public Integer airlineId;
    public Integer fare;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getDestId() {
        return destId;
    }

    public void setDestId(Integer destId) {
        this.destId = destId;
    }

    public Long getSourceTime() {
        return sourceTime;
    }

    public void setSourceTime(Long sourceTime) {
        this.sourceTime = sourceTime;
    }

    public Long getDestTime() {
        return destTime;
    }

    public void setDestTime(Long destTime) {
        this.destTime = destTime;
    }

    public Integer getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Integer airlineId) {
        this.airlineId = airlineId;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    @Override
    public String toString() {
        return id + "   " + sourceId + "   " + destId + "   " + sourceTime + "   " + destTime + airlineId + "   " + fare;
    }
}
