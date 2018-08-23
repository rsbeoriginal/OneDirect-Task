package com.rishi.onedirecttask.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rishi.onedirecttask.MyApplication;
import com.rishi.onedirecttask.R;
import com.rishi.onedirecttask.View.Client.FinalBooking;
import com.rishi.onedirecttask.db.Model.Flight;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.MyViewHolder> {
    
    ArrayList<Flight> flights;
    Context context;
    
    public FlightAdapter(Context context,ArrayList<Flight> flights){
        this.context=context;
        this.flights=flights;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_flight,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(flights.get(position));
    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imgFlight;
        TextView tvAirline,tvSourceTime,tvDestTime,tvDuration,tvFare;
        CardView cardFlight;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgFlight=itemView.findViewById(R.id.imgFlightLogo);
            tvAirline=itemView.findViewById(R.id.tvAirline);
            tvSourceTime=itemView.findViewById(R.id.tvSourceTime);
            tvDestTime=itemView.findViewById(R.id.tvDestTime);
            tvDuration=itemView.findViewById(R.id.tvDuration);
            tvFare=itemView.findViewById(R.id.tvFare);
            cardFlight=itemView.findViewById(R.id.cardFlight);
        }

        public void setData(final Flight flight) {
            tvAirline.setText(MyApplication.myDatabase.myDao().getAirlineById(flight.airlineId).name);
            tvFare.setText("Rs. " + flight.fare);
            tvDuration.setText("" + ((int)(flight.destTime-flight.sourceTime)/60000d + "Minutes"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date sDate=new Date(flight.sourceTime),dDate=new Date(flight.destTime);
            tvSourceTime.setText(simpleDateFormat.format(sDate));
            tvDestTime.setText(simpleDateFormat.format(dDate));
            Picasso.get().load(MyApplication.myDatabase.myDao().getAirlineById(flight.airlineId).imgUrl).into(imgFlight);
            cardFlight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context, FinalBooking.class);
                    intent.putExtra("flightId",flight.id);
                    context.startActivity(intent);
                }
            });
        }
    }
}
