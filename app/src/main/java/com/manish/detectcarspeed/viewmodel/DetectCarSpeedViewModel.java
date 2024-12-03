package com.manish.detectcarspeed.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.manish.detectcarspeed.DetectCarSpeedApplication;
import com.manish.detectcarspeed.Utils.Constants;
import com.manish.detectcarspeed.preferences.DetectCarSpeedPreference;

public class DetectCarSpeedViewModel extends ViewModel {
    MutableLiveData<Integer> mMaxSpeedLiveData = new MutableLiveData<>(Constants.DEFAULT_SPEED);
    private DetectCarSpeedPreference mDetectCarSpeedPreference;

    public MutableLiveData<Integer> getMaxSpeedLiveData() {
        return mMaxSpeedLiveData;
    }

    public int getMaxSpeedAllocated() {
        return DetectCarSpeedPreference.getInstance(DetectCarSpeedApplication.getAppContext()).getMaxSpeed();
    }

    public void setCarCurrentSpeed(int carSpeed) {
        mMaxSpeedLiveData.postValue(carSpeed);
    }
}
