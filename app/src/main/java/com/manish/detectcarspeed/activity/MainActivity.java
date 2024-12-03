package com.manish.detectcarspeed.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.internal.ViewModelProviders;

import com.manish.detectcarspeed.DetectCarSpeedService;
import com.manish.detectcarspeed.R;
import com.manish.detectcarspeed.preferences.DetectCarSpeedPreference;
import com.manish.detectcarspeed.viewmodel.DetectCarSpeedViewModel;

public class MainActivity extends AppCompatActivity {

    //Object to get value from Shared preference
    private DetectCarSpeedPreference mDetectCarSpeedPreference;
    private DetectCarSpeedViewModel mDetectCarSpeedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Check permissions for location is enabled
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
        mDetectCarSpeedViewModel = new ViewModelProvider(this).get(DetectCarSpeedViewModel.class);
        mDetectCarSpeedPreference = DetectCarSpeedPreference.getInstance(this);
        Intent intent = new Intent(this, DetectCarSpeedService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        mDetectCarSpeedViewModel.getMaxSpeedLiveData().observeForever(carSpeed -> {
            if (carSpeed <= mDetectCarSpeedViewModel.getMaxSpeedAllocated()) {
                return;
            }
            //trigger notification to owner
            if (mDetectCarSpeedPreference.isFirebaseActive()) {
                //send notification via firebase
            } else {
                //send notification via AWS
            }
        });
    }
}