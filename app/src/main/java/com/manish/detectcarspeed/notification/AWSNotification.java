package com.manish.detectcarspeed.notification;

import android.util.Log;

/**
 * Class for sending notification via AWS server
 */
public class AWSNotification implements IRouteNotificationHelper {
    private final String TAG = AWSNotification.class.getCanonicalName();

    @Override
    public void sendNotification(String title, String message) {
        //Send AWS Notification
        Log.d(TAG, "Notification sent via AWS server");
    }
}
