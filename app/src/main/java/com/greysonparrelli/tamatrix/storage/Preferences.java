package com.greysonparrelli.tamatrix.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedHashSet;
import java.util.Set;

public class Preferences {

    private static final String KEY_PREFS = "com.greysonparrelli.tamatrix";
    private static final String KEY_BASE_URL = "base_url";

    private static Preferences sInstance;

    private final Context mContext;
    private final Set<BaseUrlChangedListener> mBaseUrlChangedListeners = new LinkedHashSet<>();

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
        String originalBaseUrl = getBaseUrl();
        if (!baseUrl.equals(originalBaseUrl)) {
            getEditor().putString(KEY_BASE_URL, baseUrl).apply();
            Set<BaseUrlChangedListener> listeners = new LinkedHashSet<>(mBaseUrlChangedListeners);
            for (BaseUrlChangedListener listener : listeners) {
                listener.onBaseUrlChanged(baseUrl);
            }
        }
    }

    public void addBaseUrlChangedListener(BaseUrlChangedListener listener) {
        mBaseUrlChangedListeners.add(listener);
    }

    public void removeBaseUrlChangedListener(BaseUrlChangedListener listener) {
        mBaseUrlChangedListeners.remove(listener);
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
