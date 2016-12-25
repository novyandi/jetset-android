package xyz.girudo.jetset.entities;

import io.realm.RealmObject;

/**
 * Created by Novyandi Nurahmad on 11/19/16
 */

public class LeftMenu extends RealmObject {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
