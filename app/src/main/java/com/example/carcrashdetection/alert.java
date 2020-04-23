package com.example.carcrashdetection;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class alert extends AppCompatActivity{
    public int counter;
    Button stopTimer;
    TextView counttime;
    CountDownTimer countDownTimer;
    MediaPlayer player;
    Home home;
//    public static String uri;
//    private FusedLocationProviderClient fusedLocationClient;
    MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        stopTimer = (Button) findViewById(R.id.stop);

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            uri = "http://maps.google.com/maps?saddr=" + location.getLatitude()+","+location.getLongitude();
//                        }
//                    }
//                });

        Intent intent = new Intent(getApplicationContext(), alert.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        player = MediaPlayer.create(this, R.raw.alram);
        home = new Home();
        counttime=findViewById(R.id.text1);
        //countDownTimer.start();
         countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText("seconds remaining: " + millisUntilFinished / 1000);
                player.start();
            }
            @Override
            public void onFinish() {

                Toast.makeText(alert.this," Message sent", Toast.LENGTH_SHORT).show();
                player.stop();
                FragmentManager fm = getSupportFragmentManager();
                Home home = new Home();
                fm.beginTransaction().replace(R.id.alert, home).commit();

                String phonenumber = home.number;
                SmsManager smsManager = SmsManager.getDefault();
                StringBuffer smsBody = new StringBuffer();
                smsBody.append(Uri.parse("Car Crash Detected, Last known Location: " + mainActivity.uri));
                smsManager.sendTextMessage(phonenumber, null, smsBody.toString(), null, null);

            }
        }.start();
        stopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                player.stop();
                FragmentManager fm = getSupportFragmentManager();
                Home home = new Home();
                fm.beginTransaction().replace(R.id.alert, home).commit();
                Toast.makeText(alert.this,"Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
