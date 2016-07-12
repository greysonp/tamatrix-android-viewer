package com.greysonparrelli.tamatrix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.greysonparrelli.tamatrix.adapters.TamaAdapter;
import com.greysonparrelli.tamatrix.models.AllTama;
import com.greysonparrelli.tamatrix.network.RetrofitManager;
import com.greysonparrelli.tamatrix.network.TamaApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mList;
    private TamaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (RecyclerView) findViewById(R.id.list);
        mAdapter = new TamaAdapter();

        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestTamas();
    }

    private void requestTamas() {
        TamaApi tamaApi = RetrofitManager.getInstance().create(TamaApi.class);
        tamaApi.getTama().enqueue(new Callback<AllTama>() {
            @Override
            public void onResponse(Call<AllTama> call, Response<AllTama> response) {
                mAdapter.updateItems(response.body());
                requestTamas();
            }

            @Override
            public void onFailure(Call<AllTama> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Request failed.", Toast.LENGTH_SHORT).show();
                Log.e("REMOVE", "Request failed.", t);
            }
        });
    }
}
