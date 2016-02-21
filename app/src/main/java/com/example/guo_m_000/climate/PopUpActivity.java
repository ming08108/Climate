package com.example.guo_m_000.climate;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

public class PopUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        // Taking screen size of an object and reducing it
        DisplayMetrics metrics = new DisplayMetrics();
        // Retrieve window manager, fetching default display, and then metrics (mutator method)
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Accessing constants of the metrics
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        // Screen reduction.. setting layout.
        getWindow().setLayout((int) (width * .8),(int) (height * .8));


        // apply custom theme to popup
    }

}
