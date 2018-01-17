package xyz.girudo.jetset.tags;

import android.util.Log;

import com.google.android.gms.tagmanager.CustomTagProvider;

import java.util.Map;

import xyz.girudo.jetset.controllers.CleverTapControl;

/**
 * Created by novyandinurahmad on 16/01/18.
 */
public class CleverTapCallbackEventTag implements CustomTagProvider {

    @Override
    public void execute(Map<String, Object> map) {
        Log.v(CleverTapCallbackEventTag.class.getSimpleName(), "CallbackToCleverTap: " + map.get(ParamTag.EVENTNAMEPARAM.paramName()) + " || " + map.get(EventTag.TAGEVENTNAME.eventName()));
        CleverTapControl.getCleverTap().event.push(ParamTag.EVENTNAMEPARAM.paramName(), map);
        if(map.containsKey(EventTag.TAGEVENTNAME.eventName()))
            CleverTapControl.getCleverTap().event.push(EventTag.TAGEVENTNAME.eventName(), map);
    }
}
