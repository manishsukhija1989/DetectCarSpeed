package com.manish.detectcarspeed.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manish.detectcarspeed.DetectCarSpeedApplication;
import com.manish.detectcarspeed.utils.Constants;
import com.manish.detectcarspeed.notification.NotificationManager;
import com.manish.detectcarspeed.preferences.DetectCarSpeedPreference;

public class DetectCarSpeedViewModel extends ViewModel {
    MutableLiveData<Integer> mCurrentSpeedLiveData = new MutableLiveData<>(Constants.DEFAULT_SPEED);
    private DetectCarSpeedPreference mDetectCarSpeedPreference =
            DetectCarSpeedPreference.getInstance(DetectCarSpeedApplication
                    .getAppContext());
    private final String TAG = "DetectCarSpeedViewModel";

    //live data to notify
    public MutableLiveData<Integer> getCarCurrentSpeedLiveData() {
        return mCurrentSpeedLiveData;
    }

    // method to check current speed of the car
    public void checkCarSpeed(int currentSpeed) {
        int maxSpeedAllocated = getMaxSpeedAllocated();
        if (currentSpeed <= maxSpeedAllocated) {
            Log.d(TAG, "Driving smoothly");
            return;
        }
        String carNumber = mDetectCarSpeedPreference.getCarNumber();
        //notify company for rash driving
        NotificationManager.getInstance().sendNotification("Car speed exceed",
                carNumber + " is exceeding his speed");
        //notify notification to driver
        NotificationManager.getInstance().sendInAppNotification("Car speed exceed",
                "Your car speed is exceeding. Please drive slow!!");
    }

    // method to set allocated speed of the car
    public int getMaxSpeedAllocated() {
        return mDetectCarSpeedPreference.getMaxSpeedAllocated();
    }

    //method to set car current speed
    public void setCarCurrentSpeed(int carSpeed) {
        mCurrentSpeedLiveData.postValue(carSpeed);
    }
}
