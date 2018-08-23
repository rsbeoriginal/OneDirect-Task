package com.rishi.onedirecttask.View.Admin;

import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rishi.onedirecttask.MyApplication;
import com.rishi.onedirecttask.R;
import com.rishi.onedirecttask.db.Model.Airport;

import java.sql.SQLException;

public class AddAirport extends AppCompatActivity {

    EditText etCode,etName;
    Button bAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airport);
        etCode=findViewById(R.id.etCode);
        etName=findViewById(R.id.etName);
        bAdd=findViewById(R.id.bAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCode.getText().toString().length()>0 && etCode.getText().toString().length()>0){
                    Airport airport=new Airport();
                    airport.code=etCode.getText().toString();
                    airport.name=etName.getText().toString();
                    try{
                        MyApplication.myDatabase.myDao().insertAirport(airport);
                        Toast.makeText(AddAirport.this, "Airport Added successfully", Toast.LENGTH_SHORT).show();
                    }catch (SQLiteConstraintException e){
                        Toast.makeText(AddAirport.this, "Please add unique code", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
