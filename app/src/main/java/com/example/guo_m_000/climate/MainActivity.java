package com.example.guo_m_000.climate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

        client = new AsyncHttpClient();


        LoginButton loginButton = (LoginButton) findViewById(R.id.login);
        loginButton.setCredentials("dpitl9gm6s321m", "lcik8jaau3bimv6rhdllufv4qt");

        loginButton.registerListener(new LoginButton.LoginListener() {
            @Override
            public void onLogin(JSONObject session) {
                Log.d("login", "Logged in");
                String auth = "Bearer " + session.opt("access_token");


                RequestParams params = new RequestParams();
                params.put("Authorization", auth);


                client.get("https://hackillinois.climate.com/api/fields",params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("login", responseBody.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("error", error.toString());
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.d("login", e.toString());
            }
        });

    }
}
