package com.manish.detectcarspeed.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.manish.detectcarspeed.Utils.Constants;

public class NotificationBuilder {

    private static NotificationBuilder sNotificationBuilder;

    private NotificationBuilder() {

    }

    public static NotificationBuilder getInstance() {
        if (sNotificationBuilder == null) {
            sNotificationBuilder = new NotificationBuilder();
        }
        return sNotificationBuilder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel =
                new NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID,
                        Constants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public Notification buildNotification(Context context) {
        return new NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Detect car speed").setContentText("Car speed detection ongoing")
                .build();
    }

}
