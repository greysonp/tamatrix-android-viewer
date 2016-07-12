package com.greysonparrelli.tamatrix.network;

import com.greysonparrelli.tamatrix.models.AllTama;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TamaApi {
    @GET("/gettama.php")
    Call<AllTama> getTama(@Query("lastseq") long lastseq);
}