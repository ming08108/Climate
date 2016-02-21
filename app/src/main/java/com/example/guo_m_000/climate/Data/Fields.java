package com.example.guo_m_000.climate.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by guo_m_000 on 2/21/2016.
 */
public class Fields {

    @SerializedName("fields")
    public Field[] fields;


    public class Field{
        public String name;
        Centroid centroid;
    }

    public class Centroid{
        double[] coordinates;
    }
}
