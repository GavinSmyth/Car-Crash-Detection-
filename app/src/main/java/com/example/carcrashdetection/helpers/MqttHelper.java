//package com.example.carcrashdetection.helpers;
//
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.os.Handler;
//import android.os.IBinder;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.example.carcrashdetection.alert;
//
//import org.eclipse.paho.android.service.MqttAndroidClient;
//import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
//import org.eclipse.paho.client.mqttv3.IMqttActionListener;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.IMqttToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
//public class MqttHelper extends Service {
////        public MqttAndroidClient mqttAndroidClient;
////         BroadcastReceiver m_ScreenOffReceiver;
////        final String serverUri = "tcp://hairdresser.cloudmqtt.com:15767";
////
////        final String clientId = "CarCrashDetection";
////        final String subscriptionTopic = "sensor/+";
////
////        final String username = "kprpjfue";
////        final String password = "1fIq2_CIwHZj";
////
////    @Override
////    public void onCreate() {
////        super.onCreate();
////        registerReceiver();
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                MqttHelper.this.connect();
////            }
////        }).start();
////    }
////        public MqttHelper(Context context){
////            mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
////            mqttAndroidClient.setCallback(new MqttCallback() {
////
////
////                public void connectComplete(boolean b, String s) {
////
////
////                }
////
////                @Override
////                public void connectionLost(Throwable throwable) {
////
////                }
////
////                @Override
////                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
////                    Log.w("Mqtt", mqttMessage.toString());
////                    Log.e("message ", String.valueOf(mqttMessage));
////                    Toast.makeText(MqttHelper.this, "Crash Occurred", Toast.LENGTH_SHORT).show();
////                    Intent intent = new Intent(MqttHelper.this, alert.class);
////                    startActivity(intent);
////                }
////
////                @Override
////                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
////
////                }
////            });
////            connect();
////        }
////
////        public void setCallback(MqttCallback callback) {
////            mqttAndroidClient.setCallback(callback);
////        }
////
////        private void connect(){
////            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
////            mqttConnectOptions.setAutomaticReconnect(true);
////            mqttConnectOptions.setCleanSession(false);
////            mqttConnectOptions.setUserName(username);
////            mqttConnectOptions.setPassword(password.toCharArray());
////
////            try {
////
////                mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
////                    @Override
////                    public void onSuccess(IMqttToken asyncActionToken) {
////
////                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
////                        disconnectedBufferOptions.setBufferEnabled(true);
////                        disconnectedBufferOptions.setBufferSize(100);
////                        disconnectedBufferOptions.setPersistBuffer(false);
////                        disconnectedBufferOptions.setDeleteOldestMessages(false);
////                        mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
////                        subscribeToTopic();
////                    }
////
////                    @Override
////                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
////                        Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
////
////                    }
////                });
////
////
////            } catch (MqttException ex){
////                ex.printStackTrace();
////            }
////        }
////
////
////        private void subscribeToTopic() {
////            try {
////                mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
////                    @Override
////                    public void onSuccess(IMqttToken asyncActionToken) {
////                        Log.w("Mqtt","Subscribed!");
////                    }
////
////                    @Override
////                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
////                        Log.w("Mqtt", "Subscribed fail!");
////                    }
////                });
////
////            } catch (MqttException ex) {
////                System.err.println("Exception whilst subscribing");
////                ex.printStackTrace();
////            }
////        }
////    @Override
////    public int onStartCommand(Intent intent, int flags, int startId) {
////        //do something
////        return START_STICKY;
////    }
////
////    @Override
////    public IBinder onBind(Intent intent) {
////        return null;
////    }
////
////    private void registerReceiver(){
////        m_ScreenOffReceiver = new BroadcastReceiver(){
////            @Override
////            public void onReceive(final Context context, Intent intent){
////                //Log.d(TAG,"onReceive of Wifi_State_Change called");
////                if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
////                {
////                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
////                    if(wifiState != WifiManager.WIFI_STATE_ENABLED)
////                        return;
////
////                    final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
////                            String ssid = wifiInfo.getSSID();
////
////                            Toast.makeText(context, "active wifi:" + ssid, Toast.LENGTH_SHORT).show();
////
////                            //You can connect to the your mqtt broker again:
////                            connect();
////                        }
////                    }, 10000);
////                }
////            }
////        };
////
////        IntentFilter intentFilter = new IntentFilter();
////        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
////        registerReceiver(m_ScreenOffReceiver, intentFilter);
////    }
////    @Override
////    public void onDestroy() {
////        if(mqttAndroidClient!=null) {
////        /*unregisterResources is needed,otherwise receive this error:
////          has leaked ServiceConnection org.eclipse.paho.android.service.MqttAndroidClient*/
////            try {
////                mqttAndroidClient.unregisterResources();
////                mqttAndroidClient.close();
////                mqttAndroidClient.disconnect();
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////        unregisterReceiver(m_ScreenOffReceiver);
////        m_ScreenOffReceiver = null;
////
////        super.onDestroy();
////    }
//    }
//
