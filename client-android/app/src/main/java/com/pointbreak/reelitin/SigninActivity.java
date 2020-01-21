package com.pointbreak.reelitin;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URL;
import java.net.URLEncoder;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class SigninActivity extends AppCompatActivity {

    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void emailBtn(View V) throws Exception {
        EditText editText = findViewById(R.id.email_text);
        try {
            emailAuth(editText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(SigninActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    String server_response;
    public void emailAuth(String email) throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        server_response = "{ request: '1111', access: '121'}";
        StrictMode.setThreadPolicy(policy);
        String url= "http://192.168.43.166:4000/login?username=" + URLEncoder.encode(email, "UTF-8");
        //String url = "https://google.com";
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        server_response = response;
                        Log.i("Response", server_response);
                        Toast.makeText(SigninActivity.this, server_response, Toast.LENGTH_LONG).show();
                        JSONParser parser = new JSONParser();
                        JSONObject auth_object = null;
                        try {
                            auth_object = (JSONObject) parser.parse(server_response);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String refreshToken = null;
                        try {
                            refreshToken = auth_object.getJSONObject("refreshToken").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mydatabase = openOrCreateDatabase("REELITIN",MODE_PRIVATE,null);
                        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS REELITIN(ACCESS VARCHAR,REFRESH VARCHAR);");
                        mydatabase.execSQL("INSERT INTO REELITIN VALUES('NONE', '" + refreshToken + "');");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error", error.toString());
            }
        });


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
