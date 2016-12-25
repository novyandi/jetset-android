package xyz.girudo.jetset.controllers;

import android.content.Context;
import android.os.Environment;

/**
 * Created by Novyandi Nurahmad on 11/09/2016
 * Indonesia
 */
public class Config {
    public static final String FILES_PATH = Environment.getExternalStorageDirectory() + "/Android/data/com.girudo.xyz.girudo.jetset/Files";
    public static final int PRODUCTION = 1;
    public static final int STAGING = 2;
    public static final int SANDBOX = 3;

    /**
     * Set Application Name
     *
     * @return String
     **/
    public static String APP_NAME = "Jetset";
    /**
     * Set folder name for store cache data
     *
     * @return String
     **/
    public static String CACHE_FOLDER = APP_NAME + "_data";
    /**
     * Set preference name on application
     *
     * @return String
     **/
    public static String PREFERENCE_NAME = APP_NAME + "_preference";
    /**
     * Set database name if application use SQL Lite Database
     *
     * @return String
     **/
    public static String DATABASE_NAME = APP_NAME + "_DB.realm";
    /**
     * Set Api key if webservice need authentication with key
     *
     * @return String
     **/
    public static String API_KEY = "";
    /**
     * Set URL server name if application access webservice
     *
     * @return String
     **/
    public static String SERVER_ADDRESS;
    public static String HTTP = "http://";
    public static boolean isDevelopment;
    private static String DEVELOPMENT_URL = "";
    private static String PRODUCTION_URL = "";

    public static String getURL() {
        if (isDevelopment) {
            return HTTP + DEVELOPMENT_URL;
        } else {
            return HTTP + PRODUCTION_URL;
        }
    }

    public static String getAPIUrl() {
        return getURL() + "api";
    }

    public static void setMode(Context context, int mode) {
        switch (mode) {
            case STAGING:
                isDevelopment = true;
                JetsetApp.getSession(context).setDebug(true);
                JetsetApp.getSession(context).setStaging(true);
                break;
            case PRODUCTION:
                isDevelopment = false;
                JetsetApp.getSession(context).setDebug(false);
                JetsetApp.getSession(context).setStaging(false);
                break;
        }
    }
}
