package com.example.guo_m_000.climate;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guo_m_000.climate.Adapters.OfferListAdapter;
import com.example.guo_m_000.climate.Data.Offer;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class FoodBankActivity extends AppCompatActivity {

    private MobileServiceClient mClient;

    MobileServiceTable<Offer> mOfferTable;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bank);

        try {
            mClient = new MobileServiceClient(
                    "https://farmee.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final ListView listView = (ListView)findViewById(R.id.listView);

        mOfferTable = mClient.getTable(Offer.class);
        try {
            mOfferTable.execute(new TableQueryCallback<Offer>() {
                @Override
                public void onCompleted(List<Offer> result, int count, Exception exception, ServiceFilterResponse response) {
                    OfferListAdapter adapter = new OfferListAdapter(mContext, result);
                    listView.setAdapter(adapter);
                }
            });
        } catch (MobileServiceException e) {
            e.printStackTrace();
        }



        final Offer item = new Offer();
        item.phone = "1234567890";
        item.foodTypes = "cool beans";
        item.lat = "1.000";
        item.lon = "2.000";

        mOfferTable.insert(item, new TableOperationCallback<Offer>() {
            public void onCompleted(Offer entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
                    Log.d("azure", "updated");
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    Log.d("azure", exception.toString());
                }
            }
        });
    }

}
