package com.example.guo_m_000.climate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.guo_m_000.climate.Data.Offer;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;


public class FoodBankActivity extends AppCompatActivity {

    private MobileServiceClient mClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


        final Offer item = new Offer();
        item.phone = "1234567890";
        mClient.getTable(Offer.class).insert(item, new TableOperationCallback<Offer>() {
            public void onCompleted(Offer entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    Toast.makeText(getApplicationContext(), "Added sucessfully", Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG);
                }
            }
        });
    }

}
