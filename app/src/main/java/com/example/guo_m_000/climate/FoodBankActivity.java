package com.example.guo_m_000.climate;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

        String locationProvider = LocationManager.NETWORK_PROVIDER;
        // Or use LocationManager.GPS_PROVIDER
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        try {
            mClient = new MobileServiceClient(
                    "https://farmee.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Offer offer = (Offer)listView.getAdapter().getItem(position);

                AlertDialog alertDialog = new AlertDialog.Builder(FoodBankActivity.this).create();
                alertDialog.setTitle("Contact Info");
                alertDialog.setMessage("Phone " + offer.phone);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Call",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent =  new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+ offer.phone));
                                startActivity(intent);
                            }
                        });
                alertDialog.show();
            }
        });

        //populate the listview
        updateTable(listView, lastKnownLocation);

    }


    public void updateTable(final ListView listView, final Location lastKnownLocation){
        mOfferTable = mClient.getTable(Offer.class);
        try {
            mOfferTable.execute(new TableQueryCallback<Offer>() {
                @Override
                public void onCompleted(List<Offer> result, int count, Exception exception, ServiceFilterResponse response) {
                    OfferListAdapter adapter = new OfferListAdapter(mContext, result, lastKnownLocation);
                    listView.setAdapter(adapter);
                }
            });
        } catch (MobileServiceException e) {
            e.printStackTrace();
        }

    }

}
