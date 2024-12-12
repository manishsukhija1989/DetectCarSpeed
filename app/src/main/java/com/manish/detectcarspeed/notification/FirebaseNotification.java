package com.manish.detectcarspeed.notification;

/**
 * Class for sending notification via Firebase server
 */
public class FirebaseNotification implements IRouteNotificationHelper {
    private final String TAG = FirebaseNotification.class.getCanonicalName();
    @Override
    public void sendNotification(String title, String message) {
        //Send firebase notification
    }
}
