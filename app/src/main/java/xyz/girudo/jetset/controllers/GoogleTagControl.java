package xyz.girudo.jetset.controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by novyandinurahmad on 16/01/18.
 */

public class GoogleTagControl {
    private static GoogleTagControl INSTANCE;
    private FirebaseAnalytics firebaseAnalytics;

    private GoogleTagControl(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static GoogleTagControl getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GoogleTagControl(JetsetApp.getContext());
        }
        return INSTANCE;
    }

    public static FirebaseAnalytics getFirebaseAnalytics() {
        return getInstance().firebaseAnalytics;
    }

    public static void logEvent(@NonNull String eventName, @Nullable Bundle bundle) {
        getInstance().firebaseAnalytics.logEvent(eventName, bundle);
    }
}