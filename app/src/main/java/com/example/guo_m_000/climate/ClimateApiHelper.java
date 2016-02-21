package com.example.guo_m_000.climate;

import com.squareup.okhttp.Request;

/**
 * Created by guo_m_000 on 2/20/2016.
 */
public class ClimateApiHelper {

    public static Request getFields(String auth){
        Request request = new Request.Builder().url("https://hackillinois.climate.com/api/fields")
                .addHeader("Authorization", auth).build();
        return request;
    }

    
}
