package com.rishi.onedirecttask.View.Client;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rishi.onedirecttask.MyApplication;
import com.rishi.onedirecttask.R;
import com.rishi.onedirecttask.db.Model.Airline;
import com.rishi.onedirecttask.db.Model.Airport;
import com.rishi.onedirecttask.db.Model.Booking;
import com.rishi.onedirecttask.db.Model.Flight;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinalBooking extends AppCompatActivity implements View.OnClickListener {

    Integer flightId;
    Integer noOfPassengers=ClientMain.noOfPassengers;
    Flight flight;
    Airline airline;
    Airport src,dest;

    ImageView imgAirlineLogo;
    TextView tvAirline,tvSource,tvDest,tvSourceTime,tvDestTime,tvDuration,tvNoOfPassengers,tvFare,tvSourceName,tvDestName;
    EditText etUsername;
    Button bBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking);
        flightId = getIntent().getExtras().getInt("flightId",0);
        flight= MyApplication.myDatabase.myDao().getFlightById(flightId);
        airline=MyApplication.myDatabase.myDao().getAirlineById(flight.airlineId);
        src = MyApplication.myDatabase.myDao().getAirportbyId(flight.sourceId);
        dest = MyApplication.myDatabase.myDao().getAirportbyId(flight.destId);

        (bBook=findViewById(R.id.bBook)).setOnClickListener(this);
        imgAirlineLogo=findViewById(R.id.imgAirlineLogo);
        tvAirline=findViewById(R.id.tvAirline);
        tvSource=findViewById(R.id.tvSource);
        tvDest=findViewById(R.id.tvDest);
        tvSourceTime=findViewById(R.id.tvSourceTime);
        tvDestTime=findViewById(R.id.tvDestTime);
        tvDuration=findViewById(R.id.tvDuration);
        tvFare=findViewById(R.id.tvFare);
        tvNoOfPassengers=findViewById(R.id.tvNoOfPassengers);
        etUsername = findViewById(R.id.etName);
        tvSourceName=findViewById(R.id.tvSourceName);
        tvDestName=findViewById(R.id.tvDestName);

        setDataOnUI();
    }

    private void setDataOnUI() {
        tvAirline.setText(airline.name);
        Picasso.get().load(airline.imgUrl).into(imgAirlineLogo);
        tvSource.setText(src.code);
        tvDest.setText(dest.code);
        tvSourceName.setText(src.name);
        tvDestName.setText(dest.name);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date sDate=new Date(flight.sourceTime),dDate=new Date(flight.destTime);
        tvSourceTime.setText(simpleDateFormat.format(sDate));
        tvDestTime.setText(simpleDateFormat.format(dDate));
        tvFare.setText("Fare: Rs. " + (flight.fare*noOfPassengers) );
        tvDuration.setText("" + ((int)(flight.destTime-flight.sourceTime)/60000d + " Minutes"));
        tvNoOfPassengers.setText("Number of Passengers: " +noOfPassengers);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bBook:
                if(validate()){
                    Booking booking =new Booking();
                    booking.flightId=flightId;
                    booking.noOfPassengers=noOfPassengers;
                    booking.userId=etUsername.getText().toString();

                    MyApplication.myDatabase.myDao().insertBooking(booking);
                    Integer bookingId=MyApplication.myDatabase.myDao().maxBookingId();

                    AlertDialog.Builder builder=new AlertDialog.Builder(FinalBooking.this)
                            .setTitle("Flight Booked")
                            .setMessage("You have successfully booked the flight ticket.\nYour Booking id is: " + bookingId)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                break;
        }
    }

    private boolean validate() {
        if(etUsername.getText().toString().length()==0){
            etUsername.setError("Enter username/Email ID");
            return  false;
        }
        return true;
    }
}
