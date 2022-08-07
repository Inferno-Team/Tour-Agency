package com.inferno.mobile.touragency;

import android.app.Application;

import com.inferno.mobile.touragency.repositories.AgencyRepo;
import com.inferno.mobile.touragency.services.API;
import com.inferno.mobile.touragency.services.CartService;
import com.inferno.mobile.touragency.services.CartService_Impl;
import com.inferno.mobile.touragency.services.LocalDatabase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourApplication extends Application {
    public static API retrofitAPI;
    public static CartService cartService;
    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(API.class);
        cartService = new CartService_Impl(new LocalDatabase(this));

    }
}
