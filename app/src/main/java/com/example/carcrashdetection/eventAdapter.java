package com.example.carcrashdetection;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class eventAdapter extends RecyclerView.ViewHolder{
    public TextView make, type, year;
    public Button startsCar;

    public eventAdapter(@NonNull View itemView) {
        super(itemView);
        make = itemView.findViewById(R.id.make);
        type = itemView.findViewById(R.id.type);
        year = itemView.findViewById(R.id.year);
        startsCar = itemView.findViewById(R.id.startsCar);

    }
}
