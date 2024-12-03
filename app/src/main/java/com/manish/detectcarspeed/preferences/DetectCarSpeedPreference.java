package com.manish.detectcarspeed.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.manish.detectcarspeed.Utils.Constants;

public class DetectCarSpeedPreference {

    private final SharedPreferences mSharedPreference;
    private static DetectCarSpeedPreference mDetectCarSpeedPreference;

    private DetectCarSpeedPreference(Context context) {
        mSharedPreference = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static DetectCarSpeedPreference getInstance(Context context) {
        if (mDetectCarSpeedPreference == null) {
            mDetectCarSpeedPreference = new DetectCarSpeedPreference(context);
        }
        return mDetectCarSpeedPreference;
    }

    public void setMaxSpeed(int maxSpeedAllocated) {
        //Allocate max speed to vehicle
        mSharedPreference.edit()
                .putInt(Constants.CAR_MAX_SPEED, maxSpeedAllocated).apply();
    }

    public int getMaxSpeed() {
        return mSharedPreference.getInt(Constants.CAR_MAX_SPEED, Constants.DEFAULT_SPEED);
    }

    public boolean isFirebaseActive() {
        return mSharedPreference.getBoolean(Constants.FIREBASE_ACTIVE, true);
    }

    public void setFirebaseService(boolean isFirebaseActive) {
        mSharedPreference.edit().putBoolean(Constants.FIREBASE_ACTIVE, isFirebaseActive).apply();
    }

}
