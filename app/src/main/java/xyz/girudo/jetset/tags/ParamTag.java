package xyz.girudo.jetset.tags;

/**
 * Created by novyandinurahmad on 16/01/18.
 */

public enum ParamTag {
    IDPARAM("id_param"),
    NAMEPARAM("name_param"),
    STATEPARAM("state_param"),
    EVENTNAMEPARAM("event_name_param"),
    UNKNOWN("");

    private String paramName;

    ParamTag(String paramName) {
        this.paramName = paramName;
    }

    public String paramName() {
        return paramName;
    }
}