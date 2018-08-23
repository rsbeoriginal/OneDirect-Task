package com.rishi.onedirecttask.View.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rishi.onedirecttask.R;
import com.rishi.onedirecttask.db.Model.Airport;

public class AdminMain extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        findViewById(R.id.bAddAirport).setOnClickListener(this);
        findViewById(R.id.bViewAirport).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=null;

        switch (view.getId()){
            case R.id.bAddAirport:
                intent=new Intent(this,AddAirport.class);
                break;
            case R.id.bViewAirport:
                intent=new Intent(this,ViewAirport.class);
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }
    }
}
