package xyz.girudo.jetset.controllers;

import android.util.Log;

import io.realm.log.LogLevel;
import io.realm.log.RealmLogger;

/**
 * Created by Novyandi Nurahmad on 11/25/16
 */

public class RealmDebugLogger implements RealmLogger {

    public static void log(String message) {
        if (Config.isDevelopment) {
            Log.i(Config.APP_NAME, message);
        }
    }

    @Override
    public void log(int level, String tag, Throwable throwable, String message) {
        if (Config.isDevelopment) {
            Log.i(tag, message);
            if (level == LogLevel.DEBUG || level == LogLevel.ERROR || level == LogLevel.FATAL)
                throwable.printStackTrace();
        }
    }
}
