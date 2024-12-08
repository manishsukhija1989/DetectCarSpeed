package com.manish.detectcarspeed.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.car.Car;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.manish.detectcarspeed.notification.NotificationBuilder;
import com.manish.detectcarspeed.preferences.DetectCarSpeedPreference;
import com.manish.detectcarspeed.utils.Constants;

public class DetectCarSpeedService extends Service {
    private final String TAG = DetectCarSpeedService.class.getCanonicalName();
    private Context mContext;
    private DetectCarSpeedPreference mDetectCarSpeedPreference;
    private CarPropertyManager mCarPropertyManager;


    @Override
    public void onCreate() {
        mContext = this;
        mDetectCarSpeedPreference = DetectCarSpeedPreference.getInstance(this);
        mCarPropertyManager = (CarPropertyManager) Car.createCar(mContext)
                .getCarManager(Car.PROPERTY_SERVICE);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService();
        }
        registerForCarPropertyCallback();
        return super.onStartCommand(intent, flags, startId);
    }

    private void registerForCarPropertyCallback() {
        mCarPropertyManager.registerCallback(mCarPropertyEventCallback,
                VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_NORMAL);
    }

    CarPropertyEventCallback mCarPropertyEventCallback = new CarPropertyEventCallback() {
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            Log.d(TAG, "onChangeEvent " + carPropertyValue.getValue());
            mDetectCarSpeedPreference.setMaxSpeed((int) carPropertyValue.getValue());
            Intent intent = new Intent(Constants.SPEED_BROADCAST);
            intent.putExtra(Constants.CAR_CURRENT_SPEED, (int) carPropertyValue.getValue());
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }

        public void onErrorEvent(int propId, int zone) {
            Log.d(TAG, "onErrorEvent called");
        }
    };

    @SuppressLint("ForegroundServiceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForegroundService() {
        NotificationBuilder.getInstance().createNotificationChannel(this);
        startForeground(1, NotificationBuilder.getInstance().buildNotification(this));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        mCarPropertyManager.unregisterCallback(mCarPropertyEventCallback);
        super.onDestroy();
    }
}