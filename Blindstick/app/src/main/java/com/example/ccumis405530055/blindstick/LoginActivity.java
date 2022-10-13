package com.example.ccumis405530055.blindstick;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText et_username,et_password;
    ArrayList<String> permissionsList;
    TextView tv_servererror;
    RequestQueue queue;
    String[] permissionsStr = {
            Manifest.permission.INTERNET,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION};
    int permissionsCount = 0;
    ActivityResultLauncher<String[]> permissionsLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                    new ActivityResultCallback<Map<String, Boolean>>() {
                        @Override
                        public void onActivityResult(Map<String, Boolean> result) {
                            ArrayList<Boolean> list = new ArrayList<>(result.values());
                            permissionsList = new ArrayList<>();
                            permissionsCount = 0;
                            for (int i = 0; i < list.size(); i++) {
                                if (shouldShowRequestPermissionRationale(permissionsStr[i])) {
                                    permissionsList.add(permissionsStr[i]);
                                } else if (!hasPermission(LoginActivity.this, permissionsStr[i])) {
                                    permissionsCount++;
                                }
                            }
                            if (permissionsList.size() > 0) {
                                //Some permissions are denied and can be asked again.
                                askForPermissions(permissionsList);
                            } else if (permissionsCount > 0) {
                                //Show alert dialog
                                showPermissionDialog();
                            } else {
                                //All permissions granted. Do your stuff 🤞
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_servererror=findViewById(R.id.tv_servererror);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        et_username.setText("Ke912");
        et_password.setText("A10723002");
        queue = Volley.newRequestQueue(this);
        getcookie();
    }

    private boolean hasPermission(Context context, String permissionStr) {
        return ContextCompat.checkSelfPermission(context, permissionStr) == PackageManager.PERMISSION_GRANTED;
    }

    private void askForPermissions(ArrayList<String> permissionsList) {
        String[] newPermissionStr = new String[permissionsList.size()];
        for (int i = 0; i < newPermissionStr.length; i++) {
            newPermissionStr[i] = permissionsList.get(i);
        }
        if (newPermissionStr.length > 0) {
            permissionsLauncher.launch(newPermissionStr);
        } else {
        /* User has pressed 'Deny & Don't ask again' so we have to show the enable permissions dialog
        which will lead them to app details page to enable permissions from there. */
            showPermissionDialog();
        }
    }

    AlertDialog alertDialog;

    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission required")
                .setMessage("Some permissions are need to be allowed to use this app without any problems.")
                .setPositiveButton("Settings", (dialog, which) -> {
                    dialog.dismiss();
                    return;
                });
        if (alertDialog == null) {
            alertDialog = builder.create();
            if (!alertDialog.isShowing()) {
                alertDialog.show();
            }
        }
    }


    public void signup(View view) {
        if(LoginSession.getInstance().getCookie()==null){
            Toast.makeText(LoginActivity.this, "Server is not connected!", Toast.LENGTH_SHORT).show();
            tv_servererror.setVisibility(View.VISIBLE);
            return;
        }
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
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
                    tv_servererror.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Server is not connected!", Toast.LENGTH_SHORT).show();
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

    private void loginRequest() {
        if(LoginSession.getInstance().getCookie()==null){
            tv_servererror.setVisibility(View.VISIBLE);
            Toast.makeText(LoginActivity.this, "Server is not connected!", Toast.LENGTH_SHORT).show();
            return;
        }
        String uri = String
                .format(ServerInfo.serverurl+"/userinfo/?username=%1$s&password=%2$s",
                        et_username.getText().toString(),
                        et_password.getText().toString());
        //String url = ServerInfo.serverurl+"/userinfo/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response",response.toString());
                        if(response.contains("<h2>Home</h2>")){
                            SharedPreferences sharedPreferences = getSharedPreferences("LoginFile",MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("Username", et_username.getText().toString());
                            myEdit.putString("Status", "Login");
                            myEdit.commit();

                            LoginSession.getInstance().setUsername(et_username.getText().toString());
                            Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this,"Wrong password or username",Toast.LENGTH_LONG).show();

                        }
                                           }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error",error.toString());
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-CSRFToken", LoginSession.getInstance().getCookie());
                params.put("Cookie", "csrftoken="+LoginSession.getInstance().getCookie());
                return params;
            }

//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("username",et_username.getText().toString());
//                params.put("password",et_password.getText().toString());
//                return params;
//            }
        };
        queue.add(stringRequest);
    }


    public void login(View view) {
        loginRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Permissions is enabled on the device.  If not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        SharedPreferences sh = getSharedPreferences("LoginFile", Context.MODE_PRIVATE);

        String Status = sh.getString("Status", "");
        String UserName = sh.getString("Username", "");
        LoginSession.getInstance().setUsername(UserName);

        getcookie();
        if(Status.equals("Login")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }
}