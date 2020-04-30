package com.example.carcrashdetection;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class contactAdapter extends RecyclerView.ViewHolder{

    public TextView contactName, contactPhone;
    private View view;
    CheckBox chk;
    AdapterView.OnItemClickListener itemClickListener;

    public contactAdapter(@NonNull View itemView) {
        super(itemView);
        contactName = itemView.findViewById(R.id.contactName);
        contactPhone = itemView.findViewById(R.id.contactPhone);
        chk = itemView.findViewById(R.id.myCheckBox);
        this.setIsRecyclable(false);


    }



}
