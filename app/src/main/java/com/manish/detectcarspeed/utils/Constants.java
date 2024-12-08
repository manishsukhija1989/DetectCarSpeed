package com.manish.detectcarspeed.utils;

public class Constants {
    /**
     * Constant String key variable for sending broadcast
     */
    public static final String SPEED_BROADCAST = "speedbroadcast";
    /**
     * Constant String key variable to access current car speed
     */
    public static final String CAR_CURRENT_SPEED = "car_current_speed";
    /**
     * Constant String key variable to access car number
     */
    public static final String CAR_NUMBER = "car_number";
    /**
     * Constant String key variable to access max car speed allocated
     */
    public static String CAR_MAX_SPEED_ALLOCATED = "car_max_speed_allocated";
    /**
     * Constant string variable for foreground notification channel ID
     */
    public static String NOTIFICATION_CHANNEL_ID = "detect_speed_id";
    /**
     * Constant string variable for foreground notification channel name
     */
    public static String NOTIFICATION_CHANNEL_NAME = "detect_speed_name";
    /**
     * Constant string variable for accessing shared preferences
     */
    public static String SHARED_PREF_NAME = "detectcarspeed";
    /**
     * Constant int variable for accessing default max speed allocated
     */
    public static int DEFAULT_SPEED = 90;
    /**
     * Constant int variable for company max speed allocated
     */
    public static int MAX_ALLOCATED_SPEED = 90;
}
