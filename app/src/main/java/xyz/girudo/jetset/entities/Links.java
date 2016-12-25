package xyz.girudo.jetset.entities;

/**
 * Created by Novyandi Nurahmad on 11/09/2016
 * Indonesia
 */
public class Links {
    public static final String FIELD_CURRENTPAGE = "current_page";
    public static final String FIELD_FIRST = "first";
    public static final String FIELD_PREV = "prev";
    public static final String FIELD_NEXT = "next";
    public static final String FIELD_LASTPAGE = "last_page";

    private String currentPage;
    private String first;
    private String prev;
    private String next;
    private String lastPage;

    public Links() {
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }
}