package com.example.guo_m_000.climate;

import com.example.guo_m_000.climate.Data.Fields;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by guo_m_000 on 2/21/2016.
 */
public interface ClimateService {
    @GET("/api/fields")
    Call<Fields> getFields(@Header("Authorization") String authorization);

}
