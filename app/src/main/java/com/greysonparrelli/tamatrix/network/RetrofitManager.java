package com.greysonparrelli.tamatrix.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static volatile Retrofit sInstance;

    public static Retrofit getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitManager.class) {
                if (sInstance == null) {
                    sInstance = buildInstance();
                }
            }
        }
        return sInstance;
    }

    private static Retrofit buildInstance() {
        return new Retrofit.Builder()
                .baseUrl("http://tamatrix.dokku.mordorm.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
