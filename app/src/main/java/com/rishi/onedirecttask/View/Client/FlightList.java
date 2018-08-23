package com.rishi.onedirecttask.View.Client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rishi.onedirecttask.R;
import com.rishi.onedirecttask.Utils.FlightAdapter;
import com.rishi.onedirecttask.db.Model.Flight;

import java.util.ArrayList;

public class FlightList extends AppCompatActivity {

    RecyclerView rvFlight;
    FlightAdapter flightAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);
        rvFlight=findViewById(R.id.rvFlight);
        flightAdapter = new FlightAdapter(this, (ArrayList<Flight>) ClientMain.flightList);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvFlight.setLayoutManager(linearLayoutManager);
        rvFlight.setAdapter(flightAdapter);
    }
}
