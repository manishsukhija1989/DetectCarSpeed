package com.manish.detectcarspeed.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.manish.detectcarspeed.R;
import com.manish.detectcarspeed.Utils.Constants;
import com.manish.detectcarspeed.preferences.DetectCarSpeedPreference;
import com.manish.detectcarspeed.service.DetectCarSpeedService;
import com.manish.detectcarspeed.viewmodel.DetectCarSpeedViewModel;

public class MainActivity extends AppCompatActivity {

    //Object to get value from Shared preference
    private DetectCarSpeedPreference mDetectCarSpeedPreference;
    //Viewmodel updates for the carspeed
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
        //register local broadcast to receive speed updates from service
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mSpeedReceiver, new IntentFilter(Constants.SPEED_BROADCAST));
        Intent intent = new Intent(this, DetectCarSpeedService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        mDetectCarSpeedViewModel.getCarCurrentSpeedLiveData().observeForever(carSpeed -> {
            mDetectCarSpeedViewModel.checkCarSpeed(carSpeed);
        });
    }

    private BroadcastReceiver mSpeedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //receive speed from service
            int currentSpeed = intent.getIntExtra(Constants.CAR_CURRENT_SPEED, 0);
            mDetectCarSpeedViewModel.setCarCurrentSpeed(currentSpeed);
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mSpeedReceiver);
        super.onDestroy();
    }
}