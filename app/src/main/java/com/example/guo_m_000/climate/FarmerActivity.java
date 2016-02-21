package com.example.guo_m_000.climate;

// remove v7 dependencies
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.guo_m_000.climate.Adapters.OfferListAdapter;
import com.example.guo_m_000.climate.Data.Offer;
import com.example.guo_m_000.climate.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.List;

public class FarmerActivity extends AppCompatActivity {

    private MobileServiceClient mClient;

    MobileServiceTable<Offer> mOfferTable;

    Context mContext;

    String authToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
        Log.d("error", "got here1");
        authToken = getIntent().getStringExtra("sesh");

        mContext = this;
        try {
            mClient = new MobileServiceClient(
                    "https://farmee.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mOfferTable = mClient.getTable(Offer.class);

        final ListView listView = (ListView)findViewById(R.id.listView);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(MainActivity.SHARED, MODE_PRIVATE);
        String email = sharedPref.getString("email", "");

        mOfferTable.where().field("email").eq(email).execute(new TableQueryCallback<Offer>() {
            @Override
            public void onCompleted(List<Offer> result, int count, Exception exception, ServiceFilterResponse response) {
                if(exception == null) {
                    OfferListAdapter adapter = new OfferListAdapter(mContext, result);
                    listView.setAdapter(adapter);
                }
                else{
                    Log.d("azure", exception.toString());
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_farmer, menu);
        Log.d("error", "got here2");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("error", "got here3");

            switch(id)
            {
                case R.id.create_request:
                    Log.d("warning", "Correct initialization");
                   // Intent intent = new Intent(FarmerActivity.this, PopUpActivity.class);
                    //FragmentManager manager = getFragmentManager();
                    //PopUpFragment popUpFragment = new PopUpFragment();
                    //Bundle bundle = new Bundle();
                    //bundle.putString("auth", authToken);
                    //popUpFragment.setArguments(bundle);
                   // popUpFragment.show(manager, "Test");

                    Intent intent = new Intent(FarmerActivity.this, PopUpActivity.class);
                    intent.putExtra("auth", authToken);
                    startActivity(intent);
                    break;
                case R.id.logout:
                    SharedPreferences sharedPref = FarmerActivity.this.getSharedPreferences(MainActivity.SHARED, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("auth", "");
                    editor.commit();
                    finish();
                    break;
                default:
                    Log.d("error", "Action bar items not being handled");
            }
            return true;

    }

}
