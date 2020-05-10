package com.example.carcrashdetection;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTService extends Service {
        private PowerManager.WakeLock wakeLock;
        private MqttAndroidClient clientPhone;
        BroadcastReceiver m_ScreenOffReceiver;
        //all the mqtt variables needed for the client
        final String serverUri = "tcp://hairdresser.cloudmqtt.com:15767";
        final String clientId = "CarCrashDetection";
        final String subscriptionTopic = "sensor/+";
        final String username = "kprpjfue";
        final String password = "1fIq2_CIwHZj";
        public static String uri;
        private FusedLocationProviderClient fusedLocationClient;
        private LocationRequest locationRequest;



        public void     MQTTService() {
            clientPhone = new MqttAndroidClient(this, serverUri, clientId);
            clientPhone.setCallback(new MqttCallback() {
                public void connectComplete(boolean b, String s) {

                }
                @Override
                public void connectionLost(Throwable throwable) {

                }
                //if message is recieved from broker then it should open the alert activity
                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    Log.w("Mqtt", mqttMessage.toString());
                    Log.e("message ", String.valueOf(mqttMessage));
                    Toast.makeText(MQTTService.this, "Crash Occurred", Toast.LENGTH_SHORT).show();
                    Intent dialogIntent = new Intent(getApplicationContext(), alert.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
        }
        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onCreate() {
            super.onCreate();
            //checks location permissions
            checkPermission(getApplicationContext());
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            //reuests location updates
            requestLocationUpdates();
            final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

            //wakelock is used so that phone will stay awake. User can lock the phone if they want but this is an option
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "carcrashdetection:PARTIAL_WAKE_LOCK_TAG");

            //create the notification that will be displayed when the service is turned on
            createNotificationChannel(this);
            Intent notificationIntent = new Intent(this, alert.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(this, 0, notificationIntent, 0);
            Notification notification = new NotificationCompat.Builder(this, "NOTIFICATION_CHANNEL").setSmallIcon
                    (R.drawable.add_car).setContentTitle("Car Crash Detection")
                    .setContentText("Car Crash Detection Service is Running").build();

            startForeground(1001, notification);
            registerReceiver();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MQTTService.this.MQTTService();
                }
            }).start();
        }

        //used for the connection of the client to the broker
        public void setCallback(MqttCallback callback) {
            clientPhone.setCallback(callback);
        }
        private MqttConnectOptions getOptions(){
            MqttConnectOptions options = new MqttConnectOptions();

            if(clientPhone.getServerURI().contains("ssl")) {
                //set ssl config.for example:
                //options.setSocketFactory(clientPhone.getSSLSocketFactory(YOUR_KEYSTORE_FILE, "YOUR_KEYSTORE_PASSWORD"));
                //...
            }
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            options.setAutomaticReconnect(true);
            options.setCleanSession(false);;
            //options.setWill(...);
            options.setUserName(username);
            options.setPassword(password.toCharArray());

            return options;
        }
        //subscribes to the broker using the topic
        private void connectMQTT() {
            try {
                //getOptions is a method that returns your MqttConnectOptions object
                IMqttToken token = clientPhone.connect(getOptions());
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        //do something
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        clientPhone.setBufferOpts(disconnectedBufferOptions);
                        subscribeToTopic();
                    }
                    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        //do something
                    }
                });
            } catch (MqttException e) {
                //do something
                e.printStackTrace();
            }
        }
        //checks if the client is subscribed or not
        private void subscribeToTopic() {
            try {
                clientPhone.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w("Mqtt","Subscribed!");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w("Mqtt", "Subscribed fail!");
                    }
                });

            } catch (MqttException ex) {
                System.err.println("Exception whilst subscribing");
                ex.printStackTrace();
            }
        }
        //tells system to create fresh copy of the service
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            //do something
            return START_STICKY;
        }


        //this sets up the broadcast reciever and gets the wifi the user is connected to
        private void registerReceiver(){
            m_ScreenOffReceiver = new BroadcastReceiver(){
                @Override
                public void onReceive(final Context context, Intent intent){
                    //Log.d(TAG,"onReceive of Wifi_State_Change called");
                    if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
                    {
                        int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
                        if(wifiState != WifiManager.WIFI_STATE_ENABLED)
                            return;

                        final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                String ssid = wifiInfo.getSSID();

                                Toast.makeText(context, "active wifi:" + ssid, Toast.LENGTH_SHORT).show();

                                //You can connect to the your mqtt broker again:
                                connectMQTT();
                            }
                        }, 10000);
                    }
                }
            };

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            registerReceiver(m_ScreenOffReceiver, intentFilter);
        }
        //if service is destroyed wake lock is released
        @Override
        public void onDestroy() {
            super.onDestroy();
            if (wakeLock.isHeld()) {
                wakeLock.release();
            }
            //if mqqt client is null disconnect
            if(clientPhone!=null) {
            /*unregisterResources is needed,otherwise receive this error:
              has leaked ServiceConnection org.eclipse.paho.android.service.MqttAndroidClient*/
                try {
                    clientPhone.unregisterResources();
                    clientPhone.close();
                    clientPhone.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            unregisterReceiver(m_ScreenOffReceiver);
            m_ScreenOffReceiver = null;

        }

        //this creates the notification for the user
        public void createNotificationChannel(MQTTService mqttService) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                CharSequence name = "Channel name";
                String description = "Description";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("NOTIFICATION_CHANNEL", name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }
        // this creates the updates in location needed for the message that is being sent. it also sets it up so that the  location can
    //be pressed by the emergency contact
        public void requestLocationUpdates() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

                locationRequest = new LocationRequest();
                locationRequest.setInterval(2000);
                locationRequest.setFastestInterval(4000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        uri = "http://maps.google.com/maps?saddr=" + locationResult.getLastLocation().getLatitude() + "," + locationResult.getLastLocation().getLongitude();
                    }
                }, getMainLooper());
            }
        }


    //checks for location permissions
public static boolean checkPermission(final Context context) {
    return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
}


    }
