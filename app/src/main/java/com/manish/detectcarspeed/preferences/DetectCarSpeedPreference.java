package com.manish.detectcarspeed.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.manish.detectcarspeed.utils.Constants;

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

    /**
     * method to set max allocated speed of the car
     *
     * @param maxSpeedAllocated - int variable
     */
    public void setMaxSpeedAllocated(int maxSpeedAllocated) {
        //Allocate max speed to vehicle
        mSharedPreference.edit().putInt(Constants.CAR_MAX_SPEED_ALLOCATED, maxSpeedAllocated).apply();
    }

    /**
     * method to get max allocated speed of the car
     *
     * @return max allocated speed, if no speed allocated, then will return default speed
     */
    public int getMaxSpeedAllocated() {
        return mSharedPreference.getInt(Constants.CAR_MAX_SPEED_ALLOCATED, Constants.DEFAULT_SPEED);
    }

    /**
     * method to set max allocated speed of the car
     *
     * @param maxSpeedAllocated - int variable
     */
    public void setMaxSpeed(int maxSpeedAllocated) {
        //Allocate max speed to vehicle
        mSharedPreference.edit().putInt(Constants.CAR_MAX_SPEED_ALLOCATED, maxSpeedAllocated).apply();
    }

    /**
     * method to get speed of the car
     *
     * @return -- String constant
     */
    public String getCarNumber() {
        return mSharedPreference.getString(Constants.CAR_NUMBER, "Guest");
    }

    /**
     * method to set car number to detect speed of the car
     *
     * @param carNumber -- String variable
     */
    public void setCarNumber(String carNumber) {
        mSharedPreference.edit().putString(Constants.CAR_NUMBER, carNumber).apply();
    }
}
