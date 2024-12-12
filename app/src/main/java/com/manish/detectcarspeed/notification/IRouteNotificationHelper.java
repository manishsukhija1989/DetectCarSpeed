package com.manish.detectcarspeed.notification;

public interface IRouteNotificationHelper {
    /**
     * callback method to send notification to available client
     *
     * @param title   -- String variable
     * @param message -- String variable
     */
    public void sendNotification(String title, String message);
}
