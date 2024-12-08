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
import com.manish.detectcarspeed.utils.Constants;
import com.manish.detectcarspeed.notification.NotificationManager;
import com.manish.detectcarspeed.utils.NotificationType;
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
    private NotificationType notificationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize view model to detect car speed
        mDetectCarSpeedViewModel = new ViewModelProvider(this).get(DetectCarSpeedViewModel.class);
        // initialize shared preference to store dynamic values
        mDetectCarSpeedPreference = DetectCarSpeedPreference.getInstance(this);

        //After login set car number and maximum allowed speed in shared preferences
        mDetectCarSpeedPreference.setCarNumber(Constants.CAR_NUMBER);
        mDetectCarSpeedPreference.setMaxSpeedAllocated(Constants.MAX_ALLOCATED_SPEED);
        // set notification type based on available client
        notificationType = NotificationType.FIREBASE;
        NotificationManager.getInstance().initiateNotification(notificationType);

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