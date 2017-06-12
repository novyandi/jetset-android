package xyz.girudo.jetset.entities;

import io.realm.RealmObject;

/**
 * Created by Novyandi Nurahmad on 05/11/17
 */

public class ItemSell extends RealmObject {
    public static final String TYPE = "type";
    private String title;
    private String image;
    private int type;
    private double price;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
