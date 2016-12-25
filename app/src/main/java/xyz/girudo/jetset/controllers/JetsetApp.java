package xyz.girudo.jetset.controllers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import xyz.girudo.jetset.R;

/**
 * Created by Novyandi Nurahmad on 11/09/2016
 * Indonesia
 */
public class JetsetApp extends Application {

    public static final String TOKEN_KEY = "token";
    public static String TOKEN;
    private Context mContext;
    private static SharedPreferences PREFERENCES;

    public static void log(String message) {
        if (Config.isDevelopment) {
            Log.i(Config.APP_NAME, message);
        }
    }

    public static void log(String message, String type) {
        if (Config.isDevelopment) {
            switch (type) {
                case "i":
                    Log.i(Config.APP_NAME, message);
                    break;
                case "e":
                    Log.e(Config.APP_NAME, message);
                    break;
                case "d":
                    Log.d(Config.APP_NAME, message);
                    break;
                default:
                    break;
            }
            Log.i(Config.APP_NAME, message);
        }
    }

    public Context getContext() {
        return mContext;
    }

    public static Session getSession(Context context) {
        Session session = Session.getInstance();
        if (session.getToken() == null) {
            if (TOKEN == null) {
                TOKEN = JetsetApp.getConfig(context, JetsetApp.TOKEN_KEY);
            }
            session.setToken(TOKEN);
        }
        return session;
    }

    public static SharedPreferences getPreference(Context context) {
        if (PREFERENCES == null) {
            PREFERENCES = context.getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return PREFERENCES;
    }

    public static void setConfig(Context context, String key, String value) {
        SharedPreferences preferences = getPreference(context);
        SharedPreferences.Editor editor = preferences.edit();
        String e_value = "";
        try {
            e_value = JetsetEncoder.encrypt(value, context.getString(R.string.JetsetEncryptKey));
        } catch (Exception e) {
            log(e.getMessage(), "e");
        }
        try {
            editor.putString(key, e_value);
        } catch (Exception e) {
            log(e.getMessage(), "e");
        }
        editor.commit();
        if (key.equals(JetsetApp.TOKEN_KEY)) {
            try {
                JetsetApp.TOKEN = value;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getConfig(Context context, String key) {
        SharedPreferences preferences = getPreference(context);
        String value = "";
        try {
            value = JetsetEncoder.decrypt(preferences.getString(key, ""), context.getString(R.string.JetsetEncryptKey));
        } catch (Exception e) {
            log(e.getMessage(), "e");
        }
        return value;
    }

    public static void removeConfig(Context context, String key) {
        SharedPreferences preferences = getPreference(context);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            editor.remove(key);
        } catch (Exception e) {
            log(e.getMessage(), "e");
        }
        editor.commit();
    }

    public static boolean checkConfig(Context context, String key) {
        SharedPreferences preferences = getPreference(context);
        return preferences.contains(key);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        if (html == null) html = "";
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public String getUrl() {
        return getSession(mContext).getUrl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        // Set Application Mode
        Config.setMode(this, Config.STAGING);
    }
}