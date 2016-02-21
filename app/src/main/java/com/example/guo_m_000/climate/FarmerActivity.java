package com.example.guo_m_000.climate;

// remove v7 dependencies
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guo_m_000.climate.R;

public class FarmerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_farmer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.create_request)
        {
            switch(id)
            {
                case R.id.create_request:
                    Log.d("warning", "Correct initialization");
                    Intent intent = new Intent(FarmerActivity.this, PopUpActivity.class);
                default:
                    Log.d("error", "Action bar items not being handled");
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
