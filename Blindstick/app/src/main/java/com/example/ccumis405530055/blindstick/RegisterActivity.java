package com.example.ccumis405530055.blindstick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText et_uname,et_fname,et_lname,et_email,et_pwd,et_repwd;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_uname=findViewById(R.id.et_uname);
        et_fname =findViewById(R.id.et_fname);
        et_lname=findViewById(R.id.et_lname);
        et_email=findViewById(R.id.et_email);
        et_pwd=findViewById(R.id.et_pwd);
        et_repwd=findViewById(R.id.et_repwd);
        queue = Volley.newRequestQueue(this);
    }

    public void register(View view) {
        if(et_uname.getText().toString().equals("") || et_email.getText().toString().equals("") || et_pwd.getText().toString().equals("")){
            Toast.makeText(this, "Required Field Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!et_pwd.getText().toString().equals(et_repwd.getText().toString())){
            Toast.makeText(this, "Passwords Don't Match!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_pwd.getText().toString().length()<8){
            Toast.makeText(this, "Passwords Need to be at least 8 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = ServerInfo.serverurl+"/userinfo/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response",response.toString());
                        if(response.equals("Success")){
                            Toast.makeText(RegisterActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(RegisterActivity.this,"Register Failed! "+response,Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error",error.toString());
                        Toast.makeText(RegisterActivity.this,"Account already Exist!",Toast.LENGTH_LONG).show();

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
                        "    \"username\":\""+et_uname.getText().toString()+"\",\n" +
                        "    \"firstname\":\""+et_fname.getText().toString()+"\",\n" +
                        "    \"lastname\":\""+et_lname.getText().toString()+"\",\n" +
                        "    \"email\":\""+et_email.getText().toString()+"\",\n" +
                        "    \"password\": \""+et_pwd.getText().toString()+"\"\n" +
                        "}";
                return body.getBytes();
            }
        };
        queue.add(stringRequest);


    }
}