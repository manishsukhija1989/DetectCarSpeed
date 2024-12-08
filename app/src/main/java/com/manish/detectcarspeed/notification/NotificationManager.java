package com.manish.detectcarspeed.notification;

import android.util.Log;

import com.manish.detectcarspeed.utils.NotificationType;

public class NotificationManager {
    private final String TAG = NotificationManager.class.getCanonicalName();
    private RouteNotificationHelper routeNotificationHelper;
    private static NotificationManager sNotificationManager;

    private NotificationManager() {

    }

    public static NotificationManager getInstance() {
        if (sNotificationManager == null) {
            sNotificationManager = new NotificationManager();
        }
        return sNotificationManager;
    }

    /**
     * method to initiate notification client based on client available
     *
     * @param notificationType - Enum (AWS or FIREBASE)
     */
    public void initiateNotification(NotificationType notificationType) {
        Log.d(TAG, "Notification Client: " + notificationType);
        switch (notificationType) {
            case AWS:
                routeNotificationHelper = new AWSNotification();
                break;
            case FIREBASE:
                routeNotificationHelper = new FirebaseNotification();
                break;
            default:
                Log.d(TAG, "No notification client available");
        }
    }

    /**
     * Method to send notification
     *
     * @param title   -- String title variable
     * @param message -- String message variable
     */
    public void sendNotification(String title, String message) {
        routeNotificationHelper.sendNotification(title, message);
    }

    /**
     * method to send in-app notification to driver
     *
     * @param title
     * @param message
     */
    public void sendInAppNotification(String title, String message) {
        //Notification to send in-app notification to driver
    }
}
