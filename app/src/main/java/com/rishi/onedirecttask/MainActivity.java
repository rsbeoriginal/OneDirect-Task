package com.rishi.onedirecttask;

import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rishi.onedirecttask.View.Admin.AdminMain;
import com.rishi.onedirecttask.View.Client.ClientMain;
import com.rishi.onedirecttask.db.Model.Airline;
import com.rishi.onedirecttask.db.Model.Airport;
import com.rishi.onedirecttask.db.Model.Flight;
import com.rishi.onedirecttask.db.MyDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bAdmin).setOnClickListener(this);
        findViewById(R.id.bClient).setOnClickListener(this);
        setUpDatabase();
    }

    private void setUpDatabase() {
        int mYear, mMonth, mDay;
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        //Airport
        String[] airportCode = {"MAA","CCU","BLR"};
        String[] airportName = {"Chennai Airport","Kolkata Airport","Bengaluru Airport"};

        //Airline
        String[] airlineName = {"AirIndia", "Indigo", "Vistara"};
        String[] airlineLogo = {"https://i.ytimg.com/vi/FrTN98wK9FY/maxresdefault.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVaE8blkgTEVjUFkdbsRTVvC3-YnKrFXMWCzTiHhk6pQduEdQzZKzcERo",
                "https://upload.wikimedia.org/wikipedia/mr/b/bf/Vistara_logo.svg"};

        //Flight

        if(MyApplication.myDatabase.myDao().getAllAirport().size()==0){
            for(int i=0;i<airportCode.length;i++){
                Airport airport = new Airport();
                airport.code=airportCode[i];
                airport.name=airportName[i];
                MyApplication.myDatabase.myDao().insertAirport(airport);
            }

            for (int i=0;i<airlineName.length;i++){
                Airline airline=new Airline();
                airline.name=airlineName[i];
                airline.imgUrl=airlineLogo[i];
                MyApplication.myDatabase.myDao().insertAirline(airline);
            }

            for (int i=0;i<airportCode.length;i++){

                for (int j=0;j<airportCode.length;j++){
                    if(i!=j){

                        for (int k=0;k<airlineName.length;k++){

                            for(int d=0;d<7;d++) {

                                //set Timestamp
                                String todayDate=mYear + "-" + (mMonth + 1) + "-" + mDay;
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                                try{
                                    //Setting the date to the given date
                                    c.setTime(simpleDateFormat.parse(todayDate));
                                }catch(ParseException e){
                                    e.printStackTrace();
                                }
                                c.add(Calendar.HOUR,i%10);

                                //Number of Days to add
                                c.add(Calendar.DAY_OF_MONTH, d);

                                Long sTime = c.getTimeInMillis();
                                c.add(Calendar.HOUR,2);
                                Long dTime = c.getTimeInMillis();
                                Flight flight = new Flight();
                                flight.sourceId = MyApplication.myDatabase.myDao().getAirportbyCode(airportCode[i]).id;
                                flight.destId = MyApplication.myDatabase.myDao().getAirportbyCode(airportCode[j]).id;
                                flight.airlineId = MyApplication.myDatabase.myDao().getAirlineByName(airlineName[k]).id;
                                flight.fare = (i+1)*1500;
                                flight.sourceTime=sTime;
                                flight.destTime=dTime;

                                MyApplication.myDatabase.myDao().insertFlight(flight);



                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bAdmin:
                startActivity(new Intent(MainActivity.this, AdminMain.class));
                break;
            case R.id.bClient:
                startActivity(new Intent(MainActivity.this, ClientMain.class));
                break;
        }
    }
}
