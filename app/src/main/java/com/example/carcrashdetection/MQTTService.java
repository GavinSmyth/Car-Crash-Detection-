package com.example.carcrashdetection;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

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
        final String serverUri = "tcp://hairdresser.cloudmqtt.com:15767";
        final String clientId = "CarCrashDetection";
        final String subscriptionTopic = "sensor/+";
        final String username = "kprpjfue";
        final String password = "1fIq2_CIwHZj";


        public void     MQTTService() {
            clientPhone = new MqttAndroidClient(this, serverUri, clientId);
            clientPhone.setCallback(new MqttCallback() {
                public void connectComplete(boolean b, String s) {

                }
                @Override
                public void connectionLost(Throwable throwable) {

                }
                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    Log.w("Mqtt", mqttMessage.toString());
                    Log.e("message ", String.valueOf(mqttMessage));
                    Toast.makeText(MQTTService.this, "Crash Occurred", Toast.LENGTH_SHORT).show();
                    Intent dialogIntent = new Intent(MQTTService.this, alert.class);
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
            final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "carcrashdetection:PARTIAL_WAKE_LOCK_TAG");
            createNotificationChannel(this);
            Notification notification = new NotificationCompat.Builder(this, "NOTIFICATION_CHANNEL").setSmallIcon
                    (R.drawable.add_car).setContentTitle("Title")
                    .setContentText("Content").build();

            startForeground(1001, notification);
            registerReceiver();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MQTTService.this.MQTTService();
                }
            }).start();
        }


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
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            //do something
            return START_STICKY;
        }



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
        @Override
        public void onDestroy() {

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

            super.onDestroy();
            if (wakeLock.isHeld()) {
                wakeLock.release();
            }

        }


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

    }
