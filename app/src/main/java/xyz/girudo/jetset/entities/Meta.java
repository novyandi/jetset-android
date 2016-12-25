package xyz.girudo.jetset.entities;

/**
 * Created by Novyandi Nurahmad on 11/09/2016
 * Indonesia
 */
public class Meta {
    public static final String FIELD_TOTALPAGE = "total_page";
    private int totalPage;

    public Meta() {
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}