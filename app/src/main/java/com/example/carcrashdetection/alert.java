package com.example.carcrashdetection;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class alert extends AppCompatActivity{
    Button stopTimer;
    TextView counttime;
    CountDownTimer countDownTimer;
    MediaPlayer player;
    Home home;
    MainActivity mainActivity;
    private ProgressBar progressBar;
    int i = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        stopTimer = (Button) findViewById(R.id.stop);
        progressBar = findViewById(R.id.progressBar);
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
                i++;
                counttime.setText("seconds remaining: " + millisUntilFinished / 1000);
                progressBar.setProgress((int)i*100/(10000/1000));
                player.start();
            }
            //Finishing countdown timer
            @Override
            public void onFinish() {
                i++;
                progressBar.setProgress(100);

                //stops alarm sound
                player.stop();
                //getting the number and the car name using a sharedpreference
                final SharedPreferences sharedPreferences =  alert.this.getSharedPreferences("car", MODE_PRIVATE);
                final SharedPreferences prefs =  alert.this.getSharedPreferences("check", MODE_PRIVATE);
                String number = prefs.getString("number", "check");
                String name1 = sharedPreferences.getString("name", "car");
                if(number !=null) {
                    //sending the text to emergency contact
                    SmsManager smsManager = SmsManager.getDefault();
                    StringBuffer smsBody = new StringBuffer();
                    smsBody.append(Uri.parse("Car Crash Detected on car: " + name1 + ", Last known Location: " + MQTTService.uri));
                    smsManager.sendTextMessage(number, null, smsBody.toString(), null, null);
                    Toast.makeText(alert.this," Message sent", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(alert.this,"No Contact Picked", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(getApplicationContext(),MainActivity.class));



            }
        }.start();
         //if stop button is pressed timer is canceled. alarm sound stops and it brings you back to the gome screen.
        stopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                player.stop();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(alert.this,"Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    //pressing the back button also cancels the timer.
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        player.stop();
    }
}
