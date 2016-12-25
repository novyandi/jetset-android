package xyz.girudo.jetset.entities;


/**
 * Created by Novyandi Nurahmad on 11/09/2016
 * Indonesia
 */
public class BaseResponse<T> {
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_TRACKEDAT = "tracked_at";
    public static final String FIELD_DATA = "data";
    public static final String FIELD_META = "meta";
    public static final String FIELD_LINKS = "links";

    private int status;
    private String message;
    private String trackedAt;
    private T data;
    private Meta meta;
    private Links links;

    public BaseResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrackedAt() {
        return trackedAt;
    }

    public void setTrackedAt(String trackedAt) {
        this.trackedAt = trackedAt;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}