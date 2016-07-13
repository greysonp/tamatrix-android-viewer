package com.greysonparrelli.tamatrix.network;

import com.greysonparrelli.tamatrix.storage.Preferences;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

   public static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Preferences.getInstance().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
