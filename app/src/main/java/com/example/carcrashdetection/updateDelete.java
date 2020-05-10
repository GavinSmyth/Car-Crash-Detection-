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

public class updateDelete extends AppCompatActivity {
    Button update;
    Button delete;
    EditText name;
    EditText number;
    TextView cancel;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String userId;
    private String passedKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        update = (Button) findViewById(R.id.Update);
        delete = (Button) findViewById(R.id.DeleteContact);
        cancel = (TextView) findViewById(R.id.cancel);
        name = (EditText) findViewById(R.id.updateName);
        number = (EditText) findViewById(R.id.updateNumber);

        //putting the text contained in the recyclerview item into the textfeilds on this page
        name.setText(getIntent().getStringExtra("contactName"));
        number.setText(getIntent().getStringExtra("contactPhone"));

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        //getting the key of the item so that the update or delete is linked to that item
        passedKey = getIntent().getStringExtra("key");

        databaseReference = FirebaseDatabase.getInstance().getReference("phone").child(userId);

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
                databaseReference.child(passedKey).child("contactName").setValue(name.getText().toString());
                databaseReference.child(passedKey).child("contactPhone").setValue(number.getText().toString());
                Toast.makeText(updateDelete.this, "Contact Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });

        //deletes the item from firebase
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(passedKey).removeValue();
                Toast.makeText(updateDelete.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
    }

}
