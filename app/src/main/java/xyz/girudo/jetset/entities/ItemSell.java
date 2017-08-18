package xyz.girudo.jetset.entities;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Novyandi Nurahmad on 05/11/17
 */

public class ItemSell extends RealmObject {
    public static final String TYPE = "type";
    private int id;
    private String title;
    private RealmList<StringObject> images;
    private int type;
    private double price;
    private String size;
    private String color;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<StringObject> getImages() {
        return images;
    }

    public void setImages(RealmList<StringObject> images) {
        this.images = images;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
