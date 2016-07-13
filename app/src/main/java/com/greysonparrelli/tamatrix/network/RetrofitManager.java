package com.greysonparrelli.tamatrix.network;

import com.greysonparrelli.tamatrix.storage.Preferences;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager implements Preferences.BaseUrlChangedListener {

    private static RetrofitManager sInstance = new RetrofitManager();

    private Retrofit mRetrofit;

    public static Retrofit getInstance() {
        if (sInstance.mRetrofit == null) {
            if (Preferences.getInstance().getBaseUrl() != null) {
                sInstance.buildRetrofit();
            } else {
                throw new IllegalStateException("No base url was specified in the preferences.");
            }
        }
        return sInstance.mRetrofit;
    }

    private RetrofitManager() {
        Preferences.getInstance().addBaseUrlChangedListener(this);
    }

    private void buildRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Preferences.getInstance().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void onBaseUrlChanged(String baseUrl) {
        buildRetrofit();
    }
}
