package xyz.girudo.jetset.entities.Events;

/**
 * Created by Novyandi Nurahmad on 5/12/17
 */

public class OnSelectLeftMenu {
    private int position;

    public OnSelectLeftMenu(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}