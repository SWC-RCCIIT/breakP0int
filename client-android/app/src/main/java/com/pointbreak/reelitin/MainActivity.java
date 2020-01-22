package com.pointbreak.reelitin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydatabase = openOrCreateDatabase("REELITIN",MODE_PRIVATE,null);
        Cursor c = mydatabase.rawQuery("SELECT * FROM REELITIN;", null);
        c.moveToFirst();
        Log.e("AccessToken", c.getString(0));
        Log.e("RefreshToken", c.getString(1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return(true);
            case R.id.sign_out:
                try {
                    SignOut();
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(MainActivity.this, "Sign Out Failed!", Toast.LENGTH_SHORT).show();
                }
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }


    String disease, drug;
    int age, weight, dosage, result;

    public void submitEntry(View V) {
        EditText e_disease = findViewById(R.id.disease_name);
        EditText e_drug = findViewById(R.id.drug_name);
        EditText e_age= findViewById(R.id.age_box);
        EditText e_weight= findViewById(R.id.weight_box);
        EditText e_dosage= findViewById(R.id.dosage_box);
        Switch effect = findViewById(R.id.switch1);

        disease = e_disease.getText().toString();
        drug = e_drug.getText().toString();
        age = Integer.parseInt(e_age.getText().toString());
        weight = Integer.parseInt(e_weight.getText().toString());
        dosage = Integer.parseInt(e_dosage.getText().toString());
        result = (effect.isChecked())? 1 : 0;


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://booming-weight.glitch.me/prescribed/" + disease;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected JSONObject getParams()
            {
                JSONObject params = new JSONObject();
                String jsonObj = "{ \"disease\": \""+disease+"\", " +
                        "\"medicine\": \""+drug+"\", " +
                        "\"dosage\": "+Integer.toString(dosage)+", " +
                        "\"age\": "+Integer.toString(age)+", " +
                        "\"weight\": "+Integer.toString(weight)+", " +
                        "\"result\": "+Integer.toString(result)+" }";

                JSONParser parser = new JSONParser();
                try {
                    JSONObject json = (JSONObject) parser.parse(jsonObj);
                    params = json;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.e("Json string", jsonObj);

                return params;
            }
        };
        queue.add(postRequest);

    }


    String refreshToken;

    public void SignOut() throws UnsupportedEncodingException {
        refreshToken = "";
        Cursor c = mydatabase.rawQuery("SELECT * FROM REELITIN;", null);
        c.moveToFirst();
        refreshToken = c.getString(1);
        String url= "https://furtive-trail.glitch.me/logout?token=" + URLEncoder.encode(refreshToken, "UTF-8");
        //String url = "http://sudeshna.gq/code/auth2.php?username=" + URLEncoder.encode(refreshToken, "UTF-8");
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mydatabase.execSQL("DELETE FROM REELITIN;");
                        Toast.makeText(MainActivity.this, "Sign Out Successful!", Toast.LENGTH_SHORT).show();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
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


