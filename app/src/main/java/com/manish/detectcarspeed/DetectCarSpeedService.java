package com.manish.detectcarspeed;

import static android.content.Context.LOCATION_SERVICE;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.ViewModelProvider;

import com.manish.detectcarspeed.Utils.Constants;
import com.manish.detectcarspeed.notification.NotificationBuilder;
import com.manish.detectcarspeed.preferences.DetectCarSpeedPreference;

public class DetectCarSpeedService extends Service {
    private Context mContext;
    private LocationManager mLocationManager;
    private DetectCarSpeedPreference mDetectCarSpeedPreference;

    @Override
    public void onCreate() {
        mContext = DetectCarSpeedApplication.getAppContext();
        mDetectCarSpeedPreference = DetectCarSpeedPreference.getInstance(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService();
        }
        mLocationManager = (LocationManager) DetectCarSpeedApplication.getAppContext()
                .getSystemService(LOCATION_SERVICE);
        startLocationService();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startLocationService() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.LOCATION_TIME,
                Constants.LOCATION_DISTANCE,
                location -> {
                    float speed = location.getSpeed();
                    mDetectCarSpeedPreference.setMaxSpeed((int) speed);
                });
    }

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
        super.onDestroy();
    }
}