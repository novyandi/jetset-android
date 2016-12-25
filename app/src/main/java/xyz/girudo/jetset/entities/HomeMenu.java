package xyz.girudo.jetset.entities;

import io.realm.RealmObject;

/**
 * Created by Novyandi Nurahmad on 11/21/16
 */

public class HomeMenu extends RealmObject {
    private String title;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
