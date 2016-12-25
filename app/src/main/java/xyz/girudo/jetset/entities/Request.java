package xyz.girudo.jetset.entities;

import java.util.HashMap;

/**
 * Created by Novyandi Nurahmad on 11/09/2016
 * Indonesia
 */
public class Request extends HashMap<String, String> {
    public Request() {
        super();
    }

    public Request(String limit) {
        super();
        setLimit(limit);
    }

    public void setLimit(String limit) {
        put("limit", limit);
    }

    public void putFilter(String filter, String value) {
        put("filter[" + filter + "]", value);
    }
}