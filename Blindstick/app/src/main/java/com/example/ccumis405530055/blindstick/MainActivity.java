package com.example.ccumis405530055.blindstick;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    Button btn_startstreaming,btn_sendemergency,btn_logout;
    public boolean systemstat;
    BluetoothSocket mmSocket;
    TextToSpeech ttsobject;
    BluetoothDevice mmDevice = null;
    String senddata="ask";
    private Handler handler;
    final byte delimiter = 104;
    int readBufferPosition = 0;
    TextView tv_lng,tv_lat,tv_city,tv_user;
    private String android_id,username;
    RequestQueue queue;
    String Location;
    private BluetoothAdapter mBluetoothAdapter;
    @SuppressLint({"MissingInflatedId", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_startstreaming=findViewById(R.id.btn_setup);
        btn_logout=findViewById(R.id.btn_logout);
        btn_sendemergency=findViewById(R.id.btn_sendemergencydata);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btn_sendemergency.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                senthelp();
                return true;
            }
        });
        btn_logout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                logout();
                return true;
            }
        });
        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }else {

            @SuppressLint("MissingPermission") Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().equals("root")) {
                        Log.e("Bluetooth", "deviceName:" + device.getName());
                        mmDevice = device;
                        break;
                    } else {
                        mmDevice = null;
                        break;
                    }
                }
            }
        }

        tv_user=findViewById(R.id.tv_username);
        tv_lng = findViewById(R.id.tv_long);
         tv_lat = findViewById(R.id.tv_lat);
        tv_city= findViewById(R.id.tv_city);

        username=LoginSession.getInstance().getUsername();
        android_id = Settings.Secure.getString(MainActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        tv_user.setText("Welcome "+username);

        queue = Volley.newRequestQueue(this);
        LocationManager GPSlocationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationListener GPSlocationListener = new mLocationListenerGPS();
        LocationManager NetworklocationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationListener NetworklocationListener = new mLocationListenerNetwork();

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            ActivityCompat.requestPermissions(this, new String[] { ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION }, 200);
        }else {
            GPSlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, GPSlocationListener);
            NetworklocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, NetworklocationListener);
        }


        ttsobject=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i==TextToSpeech.SUCCESS){
                    ttsobject.setLanguage(Locale.getDefault());
                    if (mmDevice!=null) {
                        ttsobject.speak("Welcome "+username, TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        ttsobject.speak("Device not connected please contact your caretaker", TextToSpeech.QUEUE_FLUSH, null);

                    }
                }else {
                    Toast.makeText(getApplicationContext(), "TTS Language error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (mmDevice==null){
            TextView tv_error =findViewById(R.id.tv_error);
            tv_error.setText("Device not connected!");
            tv_error.setVisibility(View.VISIBLE);
            Button btn_bluetoothsetup=findViewById(R.id.btn_bluetoothsetup);
            btn_bluetoothsetup.setVisibility(View.VISIBLE);
        }

        }

    public void getcookie(){

        String url = ServerInfo.serverurl+"/login";
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error",error.getMessage());
                    }
                }){

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
                String csrftoken=rawCookies.split(";")[0].split("=")[1];
                Log.i("cookies",csrftoken);
                LoginSession.getInstance().setCookie(csrftoken);
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(req);
    }
    private void requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.GPS_permissions).setCancelable(false).setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }).show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.GPS_permissions).setCancelable(false).setPositiveButton(R.string.btn_watch_permissions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName())));
                }
            }).setNegativeButton(R.string.btn_close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        }
    }

    public void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginFile",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("Username", "");
        myEdit.putString("Status", "Logout");
        myEdit.commit();
        LoginSession.getInstance().Logout();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    @SuppressLint("MissingPermission")
    public void sendBtMsg(String msg2send){
        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");
        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            if (!mmSocket.isConnected()){
                mmSocket.connect();
            }

            String msg = msg2send;
            OutputStream mmOutputStream = mmSocket.getOutputStream();
            mmOutputStream.write(msg.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("sendBtMsg","Raspberry pi's RfcommSocket isn't opened");
            e.printStackTrace();
        }
    }
    public void startstreaming(View v ){
        if(!systemstat) {
            btn_startstreaming.setText("Stop");
            ttsobject.speak("Start ", TextToSpeech.QUEUE_FLUSH, null);
            systemstat = true;
            streamingask();
        }else {
            btn_startstreaming.setText("Start");
            ttsobject.speak("Stop ", TextToSpeech.QUEUE_FLUSH, null);
            systemstat = false;
        }
    }
    public void sentmessage( String data){
        if (mmDevice==null){
            btn_startstreaming.setText("Start");
            ttsobject.speak("Stop streaming ", TextToSpeech.QUEUE_FLUSH, null);
            systemstat = false;
            android.app.AlertDialog.Builder MyAlertDialog = new android.app.AlertDialog.Builder(this);
            MyAlertDialog.setTitle("Bluetooth Connection Error");
            MyAlertDialog.setCancelable(false);  // disable click back button
            MyAlertDialog.setMessage("Please check your connection with Raspberry pi(root)!");
            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    return;
                }
            };
            MyAlertDialog.setNeutralButton("okay", OkClick);
            MyAlertDialog.show();
        }else {
            (new Thread(new MainActivity.workerThread(data))).start();
        }
        return;
    }


    public void senthelp() {
        ttsobject.speak("Sending emergency message", TextToSpeech.QUEUE_FLUSH, null);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(new Date());
        postrequest(username,android_id,Location,time);
    }

    public void pair(View view) {
        Intent intent = new Intent(MainActivity.this, BluetoothPairActivity.class);
        startActivity(intent);
    }

    private class mLocationListenerGPS implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            float g_long= (float) loc.getLongitude();
            float g_lat= (float) loc.getLatitude();
            tv_lng.setText("Longitude  = "+String.format("%.5f", g_long));
            tv_lat.setText("Latitude = "+String.format("%.5f", g_lat));

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("Location", "GPS Location changed: Lat: " + loc.getLatitude() + " Lng: "
                    + loc.getLongitude());
            Location = loc.getLatitude()+","+loc.getLongitude();
            tv_city.setText("City = "+cityName);

            //postrequest(username,android_id,Location,time);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Enabled",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(getApplicationContext(), "onStatusChanged",
                    Toast.LENGTH_SHORT).show();
        }
        public void get(String provider, int status, Bundle extras) {
            Toast.makeText(getApplicationContext(), "onStatusChanged",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private class mLocationListenerNetwork implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            float g_long= (float) loc.getLongitude();
            float g_lat= (float) loc.getLatitude();
            tv_lng.setText("Longitude  = "+String.format("%.5f", g_long));
            tv_lat.setText("Latitude = "+String.format("%.5f", g_lat));
            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Location = loc.getLatitude()+","+loc.getLongitude();
            Log.i("Location", "Network Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude());
            tv_city.setText("City = "+cityName);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Enabled",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(getApplicationContext(), "onStatusChanged",
                    Toast.LENGTH_SHORT).show();
        }
        public void get(String provider, int status, Bundle extras) {
            Toast.makeText(getApplicationContext(), "onStatusChanged",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void streamingask(){

        if (systemstat!=false){
            sentmessage(senddata);
        }else{

        }
    }

    final class workerThread implements Runnable {
        private String btMsg;
        public workerThread(String msg) {
            btMsg = msg;
        }
        public void run()
        {
            sendBtMsg(btMsg);
            while(!Thread.currentThread().isInterrupted())
            {
                int bytesAvailable;
                boolean workDone = false;


                    InputStream mmInputStream;
                try {
                    mmInputStream = mmSocket.getInputStream();
                    byte[] buffer = new byte[256];
                    int bytes;
                    bytes = mmInputStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    Log.d("Bluetooth", "Received: " + readMessage);
                    if(readMessage.equals("1")){
                        senthelp();
                    }
                    mmSocket.close();
                    streamingask();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Bluetooth", "Problems occurred!");
                    return;
                }


//                    bytesAvailable = mmInputStream.available();
//                    if(bytesAvailable > 0)
//                    {
//                        byte[] packetBytes = new byte[bytesAvailable];
//                        byte[] readBuffer = new byte[512];
//                        mmInputStream.read(packetBytes);
//


//
//                        for(int i=0;i<bytesAvailable;i++)
//                        {
//                            byte b = packetBytes[i];
//                            if(b == delimiter)
//                            {
//                                byte[] encodedBytes = new byte[readBufferPosition];
//                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
//                                final String data = new String(encodedBytes, "UTF-8");
//                                readBufferPosition = 0;
//
//                                //The variable data now contains our full command
//                                handler.post(new Runnable()
//                                {
//                                    public void run()
//                                    {
//                                        Log.e("workerThread","data:");
//                                        senddata="msg from android app";
//
//                                        streamingask();
//                                    }
//                                });
//                                workDone = true;
//                                break;
//                            }
//                            else
//                            {
//                                readBuffer[readBufferPosition++] = b;
//                            }
//                        }

//                        if (workDone == true){
//                            Thread.interrupted();
//                            mmSocket.close();
//                            break;
//                        }
//                    }
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    Log.e("workerThread","workerThread");
//                    e.printStackTrace();
//
//                    if (workDone == false){
//                        Thread.interrupted();
//                        try {
//                            mmSocket.close();
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                        break;
//                    }
//                }

            }
        }
    };

    public void postrequest(String user,String device,String location,String time){
        String url = ServerInfo.serverurl+"/emergencyevent/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("success",response);
                        Toast.makeText(MainActivity.this,"Emergency Message Sent!",Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error",error.toString());
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-CSRFToken", LoginSession.getInstance().getCookie());
                params.put("Content-Type", "text/plain");
                params.put("Cookie", "csrftoken="+LoginSession.getInstance().getCookie());
                return params;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                String body="{\n" +
                        "    \"user\":\""+username+"\",\n" +
                        "    \"device\":\""+device+"\",\n" +
                        "    \"location\":\""+location+"\",\n" +
                        "    \"time\": \""+time+"\"\n" +
                        "}";
                return body.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getcookie();
    }
    }
