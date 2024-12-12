package com.manish.detectcarspeed.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.manish.detectcarspeed.R;
import com.manish.detectcarspeed.utils.Constants;
import com.manish.detectcarspeed.notification.NotificationManager;
import com.manish.detectcarspeed.utils.NotificationChannel;
import com.manish.detectcarspeed.preferences.DetectCarSpeedPreference;
import com.manish.detectcarspeed.service.DetectCarSpeedService;
import com.manish.detectcarspeed.viewmodel.DetectCarSpeedViewModel;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getCanonicalName();

    //Object to get value from Shared preference
    private DetectCarSpeedPreference mDetectCarSpeedPreference;
    //Viewmodel updates for the car speed
    private DetectCarSpeedViewModel mDetectCarSpeedViewModel;
    //variable for notification type enum
    private NotificationChannel notificationChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize view model to detect car speed
        mDetectCarSpeedViewModel = new ViewModelProvider(this).get(DetectCarSpeedViewModel.class);
        // initialize shared preference to store dynamic values
        mDetectCarSpeedPreference = DetectCarSpeedPreference.getInstance(this);

        //Car number would be fetched from API - Assuming car number - (xx-yy-zz)
        mDetectCarSpeedPreference.setCarNumber(Constants.CAR_NUMBER);
        //Max allocated speed fetched from API, Assuming max speed - 90 km/h
        mDetectCarSpeedPreference.setMaxSpeedAllocated(Constants.MAX_ALLOCATED_SPEED);
        // Notification channel fetched from API - Assuming FIREBASE
        notificationChannel = NotificationChannel.FIREBASE;
        NotificationManager.getInstance().initiateNotification(notificationChannel);

        //register local broadcast to receive speed updates from service on change of car propertied
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

    /**
     * mSpeedReceiver broadcast receiver to get updated from service for the change car speed
     */
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