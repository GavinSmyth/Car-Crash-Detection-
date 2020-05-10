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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseDatabase database;
    FirebaseUser user;
    TextView dataReceived;
    Activity context;
    String key;







    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contacts, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactName = (EditText) getView().findViewById(R.id.nameContact);
        contactPhone = (EditText) getView().findViewById(R.id.phoneContact);
        upload = (Button) getView().findViewById(R.id.uploadContact);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("phone").child(userId);
        key = databaseReference.push().getKey();
        Contact = new contact();

        //uploading the new contact to firebase
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        getValues();
                        databaseReference.push().setValue(Contact);

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

    //getting the values from the textfeilds and setting them to the contact object
    private void getValues(){
        Contact.setContactName(contactName.getText().toString());
        Contact.setContactPhone(contactPhone.getText().toString());

    }

}
