package com.example.ccumis405530055.blindstick;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
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
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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
    TextView tv_lng,tv_lat,tv_city,tv_user;
    private String android_id,username;
    RequestQueue queue;
    String Location;
    private BluetoothAdapter mBluetoothAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        tv_user=findViewById(R.id.tv_username);
         tv_lng = findViewById(R.id.tv_long);
         tv_lat = findViewById(R.id.tv_lat);
        tv_city= findViewById(R.id.tv_city);

        username=LoginSession.getInstance().getUsername();
        android_id = Settings.Secure.getString(MainActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        tv_user.setText("Welcome "+username);
        tv_lng.setText("Longitude  = ");
        tv_lat.setText("Latitude = ");
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

    public void logout(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginFile",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("Username", "");
        myEdit.putString("Status", "Logout");
        myEdit.commit();
        LoginSession.getInstance().Logout();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void help(View view) {
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