package com.example.guo_m_000.climate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.climate.login.LoginButton;
import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login);
        loginButton.setCredentials("dpitl9gm6s321m", "lcik8jaau3bimv6rhdllufv4qt");

        loginButton.registerListener(new LoginButton.LoginListener() {
            @Override
            public void onLogin(JSONObject jsonObject) {
                Log.d("login", "Logged in");

                
            }

            @Override
            public void onError(Exception e) {
                Log.d("login", e.toString());
            }
        });

    }
}
