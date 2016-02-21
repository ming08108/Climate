package com.example.guo_m_000.climate.Adapters;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guo_m_000.climate.Data.Fields;
import com.example.guo_m_000.climate.Data.Offer;
import com.example.guo_m_000.climate.R;

import java.util.List;

/**
 * Created by guo_m_000 on 2/21/2016.
 */
public class FieldsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Fields.Field> mList;
    private LayoutInflater mLayoutInflater = null;


    public FieldsAdapter(Context context, List<Fields.Field> list){
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            v = mLayoutInflater.inflate(R.layout.item_fields, null);
        }

        TextView name = (TextView)v.findViewById(R.id.name);


        Fields.Field field = (Fields.Field)getItem(position);

        name.setText(field.name);

        return v;
    }
}
