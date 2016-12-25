package xyz.girudo.jetset.entities;

import io.realm.RealmObject;

/**
 * Created by Novyandi Nurahmad on 11/20/16
 */

public class Offer extends RealmObject {
    private String imageOffer;

    public String getImageOffer() {
        return imageOffer;
    }

    public void setImageOffer(String imageOffer) {
        this.imageOffer = imageOffer;
    }
}
