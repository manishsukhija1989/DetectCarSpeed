package com.manish.detectcarspeed.notification;

public abstract class RouteNotificationHelper {
    /**
     * method to send notification to available client
     *
     * @param title   -- String variable
     * @param message -- String variable
     */
    public abstract void sendNotification(String title, String message);
}
