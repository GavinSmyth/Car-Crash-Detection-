package com.example.carcrashdetection;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class alert extends AppCompatActivity{
    public int counter;
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
            @Override
            public void onFinish() {
                i++;
                progressBar.setProgress(100);
                Toast.makeText(alert.this," Message sent", Toast.LENGTH_SHORT).show();
                player.stop();
                String phonenumber = home.number;
                PreferenceManager.getDefaultSharedPreferences(alert.this).edit().putString("MYLABEL", phonenumber).apply();
                PreferenceManager.getDefaultSharedPreferences(alert.this).getString("MYLABEL", "defaultStringIfNothingFound");
                SmsManager smsManager = SmsManager.getDefault();
                StringBuffer smsBody = new StringBuffer();
                smsBody.append(Uri.parse("Car Crash Detected, Last known Location: " + mainActivity.uri));
                smsManager.sendTextMessage(phonenumber, null, smsBody.toString(), null, null);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));


            }
        }.start();
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
}
