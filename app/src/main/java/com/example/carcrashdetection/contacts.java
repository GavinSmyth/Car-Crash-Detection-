package com.example.carcrashdetection;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class contacts extends Fragment {

    EditText contactName, contactPhone;
    Button upload;
    private DatabaseReference databaseReference;
    String userId;
    contact Contact;


    TextView dataReceived;
    Activity context;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contacts, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactName = (EditText) getView().findViewById(R.id.nameReg);
        contactPhone = (EditText) getView().findViewById(R.id.phoneReg);
        upload = (Button) getView().findViewById(R.id.buttonReg);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("phone");
        userId = databaseReference.push().getKey();
        Contact = new contact();
        dataReceived = (TextView) getView().findViewById(R.id.datarecieved);



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        getValues();
                        databaseReference.child(userId).setValue(Contact);
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
        Contact.setContactName(contactName.getText().toString());
        Contact.setContactPhone(contactPhone.getText().toString());

    }

}
