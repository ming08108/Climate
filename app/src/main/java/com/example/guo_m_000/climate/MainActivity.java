package com.example.guo_m_000.climate;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.climate.login.LoginButton;
import com.loopj.android.http.AsyncHttpClient;
import org.json.JSONObject;

public class MainActivity extends Activity {

    // References to HTTP Client object and TextView
    AsyncHttpClient client;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.title);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/Muro.otf");
        title.setTypeface(face);

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
