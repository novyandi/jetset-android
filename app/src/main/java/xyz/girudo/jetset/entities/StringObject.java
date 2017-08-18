package xyz.girudo.jetset.entities;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by Novyandi Nurahmad on 7/5/17
 */

public class StringObject extends RealmObject {
    @Index
    private String string;

    public StringObject() {
    }

    public StringObject(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}