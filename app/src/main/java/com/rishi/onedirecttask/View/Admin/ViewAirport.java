package com.rishi.onedirecttask.View.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rishi.onedirecttask.MyApplication;
import com.rishi.onedirecttask.R;
import com.rishi.onedirecttask.db.Model.Airport;

import java.util.List;

public class ViewAirport extends AppCompatActivity {

    TextView tvResult;
    List<Airport> airportList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_airport);
        tvResult=findViewById(R.id.tvResult);
        airportList= MyApplication.myDatabase.myDao().getAllAirport();
        String s="";
        for(Airport airport:airportList){
            s+="ID: "+airport.id +"\n";
            s+="Code: "+airport.code +"\n";
            s+="Name: "+airport.name +"\n\n\n";
        }
        tvResult.setText(s);
    }
}
