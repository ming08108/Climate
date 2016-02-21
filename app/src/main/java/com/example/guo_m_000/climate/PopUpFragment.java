package com.example.guo_m_000.climate;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.guo_m_000.climate.Data.Offer;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;

public class PopUpFragment extends DialogFragment{
    public final Offer offer = new Offer();

    private MobileServiceClient mClient;

    MobileServiceTable<Offer> mOfferTable;

    public PopUpFragment() {
        // Required empty public constructor
    }

    public static PopUpFragment newInstance(String authKey){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mClient = new MobileServiceClient(
                    "https://farmee.azurewebsites.net",
                    getActivity()
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mOfferTable = mClient.getTable(Offer.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final EditText emailEditText;
        final EditText phoneEditText;
        final EditText donationEditText;
        Button next;

        OkHttpClient client = new OkHttpClient();
        client.newCall(ClimateApiHelper.getFields());

        View root = inflater.inflate(R.layout.fragment_pop_up, container, false);

        emailEditText  = (EditText) root.findViewById(R.id.email_address);
        phoneEditText = (EditText) root.findViewById(R.id.phone_number);
        donationEditText = (EditText) root.findViewById(R.id.donation_input);
        next = (Button) root.findViewById(R.id.next);

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


        return root;
    }



}
