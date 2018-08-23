package com.rishi.onedirecttask.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rishi.onedirecttask.db.Model.Airline;
import com.rishi.onedirecttask.db.Model.Airport;
import com.rishi.onedirecttask.db.Model.Booking;
import com.rishi.onedirecttask.db.Model.Flight;

import java.util.List;

@Dao
public interface MyDao {

    //Airport
    @Insert
    void insertAirport(Airport airport);

    @Query("select * from Airport")
    List<Airport> getAllAirport();

    @Query("select * from Airport where code = :code")
    Airport getAirportbyCode(String code);

    @Query("select * from Airport where id = :id")
    Airport getAirportbyId(Integer id);


    //Airline
    @Insert
    void insertAirline(Airline airline);

    @Query("select * from Airline")
    List<Airline> getAllAirline();

    @Query("select name from Airline where id=:flightId")
    Airline getAirlineById(Integer flightId);

    @Query("select * from Airline where name = :airlineName")
    Airline getAirlineByName(String airlineName);


    //Flight
    @Insert
    void insertFlight(Flight flight);

    @Query("select * from Flight")
    List<Flight> getAllFlights();

    @Query("select * from Flight where (sourceId = :sourceId AND destId = :destId) AND (sourceTime>=:today AND destTime<=:nextDay)")
    List<Flight> getConnectingFlights(Integer sourceId,Integer destId,Long today,Long nextDay);

    @Query("select * from Flight where id=:id")
    Flight getFlightById(Integer id);


    //Booking
    @Insert
    void insertBooking(Booking booking);

    @Query("select * from Booking")
    List<Booking> getAllBooking();

    @Query("select * from Booking where userId = :userId")
    List<Booking> getUserBooking(String userId);

    @Query("select max(id) from Booking")
    Integer maxBookingId();

}
