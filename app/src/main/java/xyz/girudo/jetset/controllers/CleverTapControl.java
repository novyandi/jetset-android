package xyz.girudo.jetset.controllers;

import android.content.Context;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;

/**
 * Created by novyandinurahmad on 16/01/18.
 */

public class CleverTapControl {
    private static CleverTapControl INSTANCE;
    private CleverTapAPI cleverTap;

    private CleverTapControl(Context context) {
        try {
            cleverTap = CleverTapAPI.getInstance(context);
        } catch(CleverTapMetaDataNotFoundException e) {
            // thrown if you haven't specified your CleverTap Account ID or Token in your AndroidManifest.xml
        } catch(CleverTapPermissionsNotSatisfied e) {
            // thrown if you haven’t requested the required permissions in your AndroidManifest.xml
        }
    }

    public static CleverTapControl getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CleverTapControl(JetsetApp.getContext());
        }
        return INSTANCE;
    }

    public static CleverTapAPI getCleverTap() {
        return getInstance().cleverTap;

    }
}