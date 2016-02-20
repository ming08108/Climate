package com.example.guo_m_000.climate.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guo_m_000.climate.Data.Offer;
import com.example.guo_m_000.climate.R;

import java.awt.font.TextAttribute;
import java.util.List;

/**
 * Created by guo_m_000 on 2/20/2016.
 */
public class OfferListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Offer> mList;
    private LayoutInflater mLayoutInflater = null;

    public OfferListAdapter(Context context, List<Offer> list){
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void updateList(List<Offer> list){
        mList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null){
            v = mLayoutInflater.inflate(R.layout.item_offer, null);
        }

        TextView foodType = (TextView)v.findViewById(R.id.foodType);


        Offer offer = (Offer)getItem(position);
        foodType.setText(offer.foodTypes);

        return v;

    }
}
