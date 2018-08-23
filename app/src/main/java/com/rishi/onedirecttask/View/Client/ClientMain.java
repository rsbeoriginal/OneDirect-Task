package com.rishi.onedirecttask.View.Client;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rishi.onedirecttask.MyApplication;
import com.rishi.onedirecttask.R;
import com.rishi.onedirecttask.db.Model.Airport;
import com.rishi.onedirecttask.db.Model.Flight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClientMain extends AppCompatActivity implements View.OnClickListener {

    Button bSearch;
    EditText etPassengers;
    TextView tvDate;
    int mYear, mMonth, mDay;
    Spinner spinSource,spinDest;
    List<String> airportList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Long todayTimestamp=0L,nextDayTimestamp=0L;
    public static List<Flight> flightList=new ArrayList<>();
    public static Integer noOfPassengers;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
        (tvDate=findViewById(R.id.tvDate)).setOnClickListener(this);
        findViewById(R.id.bSearch).setOnClickListener(this);
        spinDest=findViewById(R.id.spinDest);
        spinSource=findViewById(R.id.spinSource);
        etPassengers=findViewById(R.id.etPassenger);
        bSearch=findViewById(R.id.bSearch);
        tvResult = findViewById(R.id.tvResult);
        printAllFlights();

        generateAirportList();

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,airportList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSource.setAdapter(arrayAdapter);
        spinDest.setAdapter(arrayAdapter);
    }

    private void printAllFlights() {
        String s="";
        List<Flight> flights = MyApplication.myDatabase.myDao().getAllFlights();
        for(int i=0;i<flights.size();i++){
            Flight flight=flights.get(i);
            Calendar c = Calendar.getInstance();
            Long time = c.getTimeInMillis();
            String t="";
            t += MyApplication.myDatabase.myDao().getAirportbyId(flight.sourceId).code;
            t += "\t" + MyApplication.myDatabase.myDao().getAirportbyId(flight.destId).code;
            t += "\t" + strDate(flight.sourceTime);
            t += "\t" + strDate(flight.destTime);
            t += "\t" + flight.fare;
            if(flight.sourceTime>time) {
                s +=t +"\n";
            }
            Log.d("FLIGHT",t);

        }
        tvResult.setText(s);
    }

    String strDate(Long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(new Date(time));
    }

    private void generateAirportList() {
        List<Airport> airports= MyApplication.myDatabase.myDao().getAllAirport();
        for (Airport airport:airports){
            String temp = airport.code + " - " + airport.name;
            airportList.add(temp);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvDate:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                //set Timestamp
                                String todayDate=year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                try{
                                    //Setting the date to the given date
                                    c.setTime(simpleDateFormat.parse(todayDate));
                                }catch(ParseException e){
                                    e.printStackTrace();
                                }
                                todayTimestamp=c.getTimeInMillis();
                                //Number of Days to add
                                c.add(Calendar.DAY_OF_MONTH, 1);
                                //Date after adding the days to the given date
                                nextDayTimestamp=c.getTimeInMillis();




                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.bSearch:
                if(validate()){
                    String src = spinSource.getSelectedItem().toString().split("-")[0].trim();
                    String dest = spinDest.getSelectedItem().toString().split("-")[0].trim();

                    Airport srcAirport=MyApplication.myDatabase.myDao().getAirportbyCode(src);
                    Airport destAirport=MyApplication.myDatabase.myDao().getAirportbyCode(dest);

                    noOfPassengers = Integer.parseInt(etPassengers.getText().toString());
                    flightList=MyApplication.myDatabase.myDao().getConnectingFlights(srcAirport.id,destAirport.id,todayTimestamp,nextDayTimestamp);

                    startActivity(new Intent(ClientMain.this,FlightList.class));

                }
                break;
        }
    }

    private boolean validate() {
        String src=spinSource.getSelectedItem().toString();
        String dest=spinDest.getSelectedItem().toString();
        if(src.contentEquals(dest)){
            AlertDialog.Builder builder = new AlertDialog.Builder(ClientMain.this)
                    .setTitle("Error")
                    .setMessage("Please select different cities!!")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();

            return false;
        }

        if(tvDate.getText().toString().contentEquals("Select A Date")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ClientMain.this)
                    .setTitle("Error")
                    .setMessage("Please select a Date!!")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();
            return false;
        }

        if(etPassengers.getText().toString().length()==0){

            AlertDialog.Builder builder = new AlertDialog.Builder(ClientMain.this)
                    .setTitle("Error")
                    .setMessage("Please enter number of passengers!!")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();

            return false;
        }
        return true;
    }
}
