package com.greysonparrelli.tamatrix;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.greysonparrelli.tamatrix.adapters.TamaAdapter;
import com.greysonparrelli.tamatrix.models.AllTama;
import com.greysonparrelli.tamatrix.network.RetrofitManager;
import com.greysonparrelli.tamatrix.network.TamaApi;
import com.greysonparrelli.tamatrix.storage.Preferences;
import com.greysonparrelli.tamatrix.ui.DialogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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
        startFlow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.server:
                DialogUtil.showServerUrlDialog(this, new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        startFlow();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startFlow() {
        mAdapter.clear();
        if (Preferences.getInstance().getBaseUrl() != null) {
            requestTamas(0);
        } else {
            DialogUtil.showServerUrlDialog(this, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    startFlow();
                }
            });
        }
    }

    private void requestTamas(long lastSeq) {
        TamaApi tamaApi = RetrofitManager.getInstance().create(TamaApi.class);
        tamaApi.getTama(lastSeq).enqueue(new Callback<AllTama>() {
            @Override
            public void onResponse(Call<AllTama> call, Response<AllTama> response) {
                mAdapter.updateItems(response.body());
                requestTamas(response.body().lastseq);
            }

            @Override
            public void onFailure(Call<AllTama> call, Throwable t) {
                mAdapter.clear();
                Toast.makeText(MainActivity.this, "Request failed.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Request failed.", t);
            }
        });
    }
}
