package com.example.carcrashdetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class updateDeleteCars extends AppCompatActivity {

    Button update;
    Button delete;
    EditText make;
    EditText type;
    EditText year;
    TextView cancel;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String userId;
    private String passedKey;
    int button_status=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_cars);

        update = (Button) findViewById(R.id.updateCar);
        delete = (Button) findViewById(R.id.deleteCar);
        cancel = (TextView) findViewById(R.id.cancelCar);
        make = (EditText) findViewById(R.id.updateMake);
        type = (EditText) findViewById(R.id.updateType);
        year = (EditText) findViewById(R.id.updateYear);

        //getting the key of the item so that the update or delete is linked to that item
        passedKey = getIntent().getStringExtra("key");

        //putting the text contained in the recyclerview item into the textfeilds on this page
        make.setText(getIntent().getStringExtra("make"));
        type.setText(getIntent().getStringExtra("type"));
        year.setText(getIntent().getStringExtra("year"));
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Cars").child(userId);



        //brings you back to the home page
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


        //updates firebase with the new data that has been placed in the text views
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(passedKey).child("carMake").setValue(make.getText().toString());
                databaseReference.child(passedKey).child("carType").setValue(type.getText().toString());
                databaseReference.child(passedKey).child("carYear").setValue(year.getText().toString());
                Toast.makeText(updateDeleteCars.this, "Car Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
        //deletes the item from firebase
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(passedKey).removeValue();
                Toast.makeText(updateDeleteCars.this, "Car Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));


            }
        });

    }

}
