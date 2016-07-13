package com.greysonparrelli.tamatrix;

import android.app.Application;

import com.greysonparrelli.tamatrix.storage.Preferences;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        Preferences.init(getApplicationContext());
    }
}
