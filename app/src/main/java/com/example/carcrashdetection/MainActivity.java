package com.example.carcrashdetection;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

//import com.example.carcrashdetection.helpers.MqttHelper;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth firebaseAuth;
    Button log;
    NavigationView navigationView;
    View  headerView;
    private DrawerLayout drawerLayout;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;
    TextView userEmail;
    private LocationRequest locationRequest;
    ImageView imageView;
    public static final int IMAGE_CODE = 1;
    Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    FirebaseUser user;
    String userid;





    //Uri contentURI;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // NavigationView Header
        headerView =  navigationView.getHeaderView(0);
        userEmail = (TextView) headerView.findViewById(R.id.userEmail);
        imageView = (ImageView) headerView.findViewById(R.id.profilepic);
        loadUserInfo();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child(userid);

        Home home = new Home();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        log = (Button) findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //for the navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                    new Home()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openimage();
            }
        });
        profileImage();

        //asks the user for permissions when app is installed
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
       // ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.FOREGROUND_SERVICE},1);
        //ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},1);

        callPermissions2();
        callPermissions();




    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    // this is for the imageview in the navigation drawer. if image is selected it gets the image to firebase and pulls in into the imageview
    private void profileImage() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    Glide.with(getApplicationContext()).load(upload.getUplaodUri()).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    //when you click on the image in the navigation drawer it will bring you to you gallery
    private void openimage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, IMAGE_CODE);
    }




    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            //manage overlay permission

        //this is for placing the image selcted from gallery is placed into the image view
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && null != data && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(imageView);

            fileUploader();

        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    //when an image is picked from you gallery it is then pushed up to firebase storage and the url is moved into the database where it is
    //used to pull down the image into the app
    private void fileUploader(){
        if(imageUri != null){
            final StorageReference reference = storageReference.child(userid + ".jpeg");

            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Upload upload = new Upload(uri.toString());
                            databaseReference.setValue(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    profileImage();
                                }
                            });
                        }
                    });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    ;;
                }
            });

        }else{
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

        }

    }




    //this is for the navigation drawer and the actions to be completed when buttons are pressed
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_car:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                        new Cars()).commit();
                break;
            case R.id.nav_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                        new contacts()).commit();
                break;
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                        new Home()).commit();
                break;

            case R.id.logoutNav:
                firebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    //asks user for permissions for location
    public void callPermissions(){
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                Log.d("permision Status", "permission granted");
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callPermissions();
            }
        });
    }
    public void callPermissions2(){
        String[] permissions = {Manifest.permission.SEND_SMS, Manifest.permission.SYSTEM_ALERT_WINDOW};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                Log.d("permision Status", "permission granted");
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callPermissions();
            }
        });
    }


//    public void requestLocationUpdates() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED){
//            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(2000);
//        locationRequest.setFastestInterval(4000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                uri = "http://maps.google.com/maps?saddr=" + locationResult.getLastLocation().getLatitude() +","+locationResult.getLastLocation().getLongitude();
//            }
//        }, getMainLooper());
//    }
//    }
    //this puts the current users email in the navigation drawer to personalize it
    private void loadUserInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getEmail() != null) {
            userEmail.setText(user.getEmail());
        }

    }

}
