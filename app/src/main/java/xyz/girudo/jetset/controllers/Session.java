package xyz.girudo.jetset.controllers;

public class Session {
    private static final String HOST = "kado-api.pikpun.net/";
    private static final String HOST_STAGING = "kado-api.pikpun.net/";
    private static Session INSTANCE;
    private boolean sandbox;
    private boolean staging;
    private boolean debug = true;
    private boolean https = false; // used https by default
    private String token;
    private String apiUrl;
    private String name;
    private String profilePic;

    public Session() {
    }

    public static Session getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Session();
        }
        return INSTANCE;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public boolean isStaging() {
        return staging;
    }

    public void setStaging(boolean staging) {
        this.staging = staging;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    public String getUrl() {
        if (getApiUrl() != null) {
            return getApiUrl();
        }
        String protocol = https ? "https://" : "http://";
        return protocol + (staging ? HOST_STAGING : HOST);
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
