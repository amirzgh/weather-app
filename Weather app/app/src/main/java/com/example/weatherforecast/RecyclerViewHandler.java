package com.example.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewHandler extends RecyclerView.Adapter<RecyclerViewHandler.ViewHolder> {

    ArrayList<ArrayList<String>> days = new ArrayList<>();
    Context viewContext;
    WeatherIconService weatherIconService = new WeatherIconService();

    public RecyclerViewHandler(ArrayList<ArrayList<String>> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public RecyclerViewHandler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_days_row,parent,false);
        viewContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHandler.ViewHolder holder, int position) {
        holder.row_week_image.setImageDrawable(weatherIconService.getIcon(days.get(position).get(1), viewContext));
        holder.row_week_date.setText(getDate(position));
        holder.row_week_wind_speed.setText(days.get(position).get(4));
        holder.row_week_temperature.setText(days.get(position).get(2));
        holder.week_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(viewContext, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView row_week_date;
        ImageView row_week_image;
        TextView row_week_temperature;
        TextView row_week_wind_speed;
        CardView week_card_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row_week_date = itemView.findViewById(R.id.row_week_date);
            row_week_image = itemView.findViewById(R.id.row_week_image);
            row_week_temperature = itemView.findViewById(R.id.row_week_temperature);
            row_week_wind_speed = itemView.findViewById(R.id.row_week_wind_speed);
            week_card_view = itemView.findViewById(R.id.weekday_card_view);
        }
    }


    public String getDate(int index){
        LocalDate today = LocalDate.now();
        LocalDate date = today.plusDays(index);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (index == 0) return "Today";
        else if (index == 1) return "Tomorrow";
        else return String.valueOf(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()));
    }

}


