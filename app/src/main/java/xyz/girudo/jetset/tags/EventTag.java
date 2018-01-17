package xyz.girudo.jetset.tags;

/**
 * Created by novyandinurahmad on 16/01/18.
 */

public enum EventTag {
    CLICKEVENT("jetset_click_event"),
    LOGINEVENT("jetset_login_event"),
    LOGOUTEVENT("jetset_logout_event"),
    JETSETEVENTNAME("jetset_event_name"),
    TAGEVENTNAME("jetset_tag_event_name"),
    UNKNOWN("");

    private String eventName;

    EventTag(String eventName) {
        this.eventName = eventName;
    }

    public String eventName() {
        return eventName;
    }
}