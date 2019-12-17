package com.example.carcrashdetection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.EventViewHolder>{

    Context context;
    List<Event> eventList;

    public eventAdapter(Context context, List<Event> eventList){
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_event, null);
        return new EventViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int i) {

        Event event = eventList.get(i);
        holder.CarType.setText(event.getCarType());
        holder.CarMake.setText(event.getCarMake());
        holder.CarYear.setText(event.getCarYear());



    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{
        TextView CarType, CarMake, CarYear;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            CarType = itemView.findViewById(R.id.type);
            CarMake = itemView.findViewById(R.id.make);
            CarYear = itemView.findViewById(R.id.year);
        }
    }
}
