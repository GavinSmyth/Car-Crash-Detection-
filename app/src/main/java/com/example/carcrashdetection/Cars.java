package com.example.carcrashdetection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cars extends Fragment {
    EditText makeCar, typeCar, yearCar;
    Button upload;
    Event event;
    FirebaseUser user;
    String userId;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cars, container, false);

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        makeCar = (EditText) getView().findViewById(R.id.makeOfCar);
        typeCar = (EditText) getView().findViewById(R.id.typeOfCar);
        yearCar = (EditText) getView().findViewById(R.id.yearOfCar);
        upload = (Button) getView().findViewById(R.id.addCar);
        event = new Event();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cars").child(userId);;


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            getValues();
                            databaseReference.push().setValue(event);
                            Toast.makeText(getActivity(),"Data Inserted.....", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(),"Failed" + databaseError, Toast.LENGTH_LONG).show();

                        }
                    });
                }


        });

    }


    private void getValues(){
        event.setCarMake(makeCar.getText().toString());
        event.setCarType(typeCar.getText().toString());
        event.setCarYear(yearCar.getText().toString());
    }


}
