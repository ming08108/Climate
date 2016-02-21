package com.example.guo_m_000.climate;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guo_m_000.climate.Adapters.FieldsAdapter;
import com.example.guo_m_000.climate.Data.Fields;
import com.example.guo_m_000.climate.Data.Offer;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PopUpActivity extends AppCompatActivity {
    final Offer offer = new Offer();

    ClimateService service;

    String authToken;


    private MobileServiceClient mClient;

    MobileServiceTable<Offer> mOfferTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        try {
            mClient = new MobileServiceClient(
                    "https://farmee.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mOfferTable = mClient.getTable(Offer.class);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hackillinois.climate.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ClimateService.class);

        authToken = getIntent().getStringExtra("auth");

        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fields.Field field = (Fields.Field)listView.getAdapter().getItem(position);
                offer.lon = String.valueOf(field.centroid.coordinates[0]);
                offer.lat = String.valueOf(field.centroid.coordinates[1]);
                Toast.makeText(getApplicationContext(), "Selected " + field.name, Toast.LENGTH_LONG).show();
                Log.d("fields", "Selected " + field.name);
            }
        });

        service.getFields(authToken).enqueue(new Callback<Fields>() {
            @Override
            public void onResponse(Call<Fields> call, Response<Fields> response) {
                FieldsAdapter adapter = new FieldsAdapter(getApplicationContext(), Arrays.asList(response.body().fields));
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Fields> call, Throwable t) {

            }
        });


        final EditText emailEditText;
        final EditText phoneEditText;
        final EditText donationEditText;
        Button next;

        phoneEditText = (EditText) findViewById(R.id.phone_number);
        donationEditText = (EditText) findViewById(R.id.donation_input);
        next = (Button) findViewById(R.id.next);

        next.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(MainActivity.SHARED, MODE_PRIVATE);
                        offer.email = sharedPref.getString("email", "");
                        offer.phone = phoneEditText.getText().toString();
                        offer.foodTypes = donationEditText.getText().toString();

                        if(offer.lon == null){
                            Toast.makeText(getApplicationContext(), "Please select a field", Toast.LENGTH_LONG).show();
                        }
                        else{
                            mOfferTable.insert(offer, new TableOperationCallback<Offer>() {
                                @Override
                                public void onCompleted(Offer entity, Exception exception, ServiceFilterResponse response) {
                                    if(exception == null) {
                                        Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                                        Log.d("azure", exception.toString());
                                    }
                                }
                            });
                        }

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
