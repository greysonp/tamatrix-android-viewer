package com.greysonparrelli.tamatrix.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static final String KEY_PREFS = "com.greysonparrelli.tamatrix";
    private static final String KEY_BASE_URL = "base_url";

    private static Preferences sInstance;

    private final Context mContext;

    public static Preferences getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Must call Preferences.init() in Application.onCreate()");
        }
        return sInstance;
    }

    private Preferences(Context context) {
        mContext = context;
    }

    public static void init(Context context) {
        sInstance = new Preferences(context);
    }

    public String getBaseUrl() {
        return getPrefs().getString(KEY_BASE_URL, null);
    }

    public void setBaseUrl(String baseUrl) {
        getEditor().putString(KEY_BASE_URL, baseUrl).apply();
    }

    private SharedPreferences getPrefs() {
        return mContext.getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return getPrefs().edit();
    }

    public interface BaseUrlChangedListener {
        void onBaseUrlChanged(String baseUrl);
    }
}
