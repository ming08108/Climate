package com.example.guo_m_000.climate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.guo_m_000.climate.Data.Offer;

public class PopUpActivity extends AppCompatActivity {
    final Offer offer = new Offer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        final EditText emailEditText;
        final EditText phoneEditText;
        final EditText donationEditText;
        Button next;

        emailEditText  = (EditText) findViewById(R.id.email_address);
        phoneEditText = (EditText) findViewById(R.id.phone_number);
        donationEditText = (EditText) findViewById(R.id.donation_input);
        next = (Button) findViewById(R.id.next);

        next.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        offer.email = emailEditText.getText().toString();
                        offer.phone = phoneEditText.getText().toString();
                        offer.foodTypes = donationEditText.getText().toString();

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pop_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
