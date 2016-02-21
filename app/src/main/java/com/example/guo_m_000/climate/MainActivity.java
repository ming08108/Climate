package com.example.guo_m_000.climate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.climate.login.LoginButton;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    // References to HTTP Client object and TextView
    AsyncHttpClient client;
    TextView title;

    final static String SHARED = "PREFSKEY";

    SharedPreferences sharedPref;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.title);

        sharedPref = this.getSharedPreferences(MainActivity.SHARED, Context.MODE_PRIVATE);


        Typeface face = Typeface.createFromAsset(getAssets(), "font/DimboRegular.ttf");
        title.setTypeface(face);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login);
        loginButton.setCredentials("dpitl9gm6s321m", "lcik8jaau3bimv6rhdllufv4qt");

        loginButton.registerListener(new LoginButton.LoginListener() {
            @Override
            public void onLogin(JSONObject session) {

                String sesh;

                Log.d("login", "Logged in");
                Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                sesh = "Bearer " + session.opt("access_token");

                String email = "";
                try {
                    email = session.getJSONObject("user").getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(MainActivity.SHARED, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("auth", sesh);
                editor.putString("email", email);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), FarmerActivity.class);
                intent.putExtra("sesh", sesh);
                startActivity(intent);
            }

            @Override
            public void onError(Exception e) {
                Log.d("login", e.toString());
            }
        });



    }

    public void onClickFoodBankLogin(View view)
    {
        Intent intent = new Intent(this, FoodBankActivity.class);
        startActivity(intent);
    }
    public void onClickFarmerLogin(View view)
    {
        String sesh = sharedPref.getString("auth", "");

        if(sesh.equals("")) {
            Button realLoginButton = (Button) findViewById(R.id.login);
            realLoginButton.performClick();
        }
        else{
            Intent intent = new Intent(getApplicationContext(), FarmerActivity.class);
            intent.putExtra("sesh", sesh);
            startActivity(intent);
        }

    }
}
