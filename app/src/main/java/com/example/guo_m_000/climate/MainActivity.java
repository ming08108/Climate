package com.example.guo_m_000.climate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.climate.login.LoginButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new AsyncHttpClient(true, 80, 443);


        LoginButton loginButton = (LoginButton) findViewById(R.id.login);
        loginButton.setCredentials("dpitl9gm6s321m", "lcik8jaau3bimv6rhdllufv4qt");

        loginButton.registerListener(new LoginButton.LoginListener() {
            @Override
            public void onLogin(JSONObject session) {
                Log.d("login", "Logged in");
                Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Log.d("login", e.toString());
            }
        });

    }
}
