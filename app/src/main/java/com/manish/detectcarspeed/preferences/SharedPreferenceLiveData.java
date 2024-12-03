package com.manish.detectcarspeed.preferences;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public abstract class SharedPreferenceLiveData<T> extends LiveData<T> {
    T valueFromPreferences = null;
    String mKey = "";
    T mDefValue = null;
    SharedPreferences mSharedPreferences = null;

    public SharedPreferenceLiveData(SharedPreferences sharedPreferences, String key, T defValue) {
        valueFromPreferences = getValueFromPreferences(key, defValue);
        mKey = key;
        mDefValue = defValue;
        mSharedPreferences = sharedPreferences;
    }

    public abstract T getValueFromPreferences(String key, T defValue);

    SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = (sharedPreferences, key) -> {
        if (mKey == key) {
            valueFromPreferences = getValueFromPreferences(key, mDefValue);
        }
    };

    @Override
    protected void onActive() {
        super.onActive();
        valueFromPreferences = getValueFromPreferences(mKey, mDefValue);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Override
    protected void onInactive() {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        super.onInactive();
    }

    class SharedPreferenceIntLiveData extends SharedPreferenceLiveData<Integer> {
        SharedPreferences mSharedPreferences;

        public SharedPreferenceIntLiveData(SharedPreferences sharedPreferences, String key, Integer defValue) {
            super(sharedPreferences, key, defValue);
            mSharedPreferences = sharedPreferences;
        }

        @Override
        public Integer getValueFromPreferences(String key, Integer defValue) {
            return mSharedPreferences.getInt(key, defValue);
        }
    }

    public class SharedPreferenceBooleanLiveData extends SharedPreferenceLiveData<Boolean> {
        SharedPreferences mSharedPreferences;

        public SharedPreferenceBooleanLiveData(SharedPreferences sharedPreferences, String key, Boolean defValue) {
            super(sharedPreferences, key, defValue);
        }

        @Override
        public Boolean getValueFromPreferences(String key, Boolean defValue) {
            return mSharedPreferences.getBoolean(key, defValue);
        }
    }


    public class SharedPreferenceStringLiveData extends SharedPreferenceLiveData<String> {
        SharedPreferences mSharedPreferences;

        public SharedPreferenceStringLiveData(SharedPreferences sharedPreferences, String key, String defValue) {
            super(sharedPreferences, key, defValue);
        }

        @Override
        public String getValueFromPreferences(String key, String defValue) {
            return mSharedPreferences.getString(key, defValue);
        }
    }

    public SharedPreferenceLiveData<Integer> intLiveData(String key, int defValue) {
        return new SharedPreferenceIntLiveData(mSharedPreferences, key, defValue);
    }

    public SharedPreferenceStringLiveData stringLiveData(String key, String defValue) {
        return new SharedPreferenceStringLiveData(mSharedPreferences, key, defValue);
    }

    public SharedPreferenceBooleanLiveData booleanLiveData(String key, Boolean defValue) {
        return new SharedPreferenceBooleanLiveData(mSharedPreferences, key, defValue);
    }
}
