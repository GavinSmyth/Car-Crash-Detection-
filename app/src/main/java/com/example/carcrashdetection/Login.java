 package com.example.carcrashdetection;

 import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.carcrashdetection.MainActivity.ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE;

 public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginButton;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        title = (TextView) findViewById(R.id.title1);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        checkPermission1();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(Login.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d("not", "onAuthStateChanged:signed_out");
        }
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.button);
        mCreateBtn = (TextView) findViewById(R.id.create);
        progressBar = findViewById(R.id.progressBar3);
        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() !=null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//
//        }
        //loging in the user to the app. checks if any of the edit texts are empty
        //password must also be more than 6 charactrs long
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailReg = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(emailReg)){
                    mEmail.setError("Email Required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    mPassword.setError("password Required");
                    return;
                }
                if(pass.length() <6){
                    mPassword.setError("Password must be more the 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //checks if everything matches and either lets you into the app or checks for errors
                firebaseAuth.signInWithEmailAndPassword(emailReg,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{
                            Toast.makeText(Login.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
        //brings you to the register page
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
     @TargetApi(Build.VERSION_CODES.M)
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         //manage overlay permission
         if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
             if (!Settings.canDrawOverlays(this)) {
                 // You don't have permission
                 checkPermission1();
             } else {
                 // Do as per your logic
             }

         }
     }



     //asks user for permissions for overlay
     public void checkPermission1() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             if (!Settings.canDrawOverlays(this)) {
                 Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                         Uri.parse("package:" + getPackageName()));
                 startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
             }
         }
     }
}
