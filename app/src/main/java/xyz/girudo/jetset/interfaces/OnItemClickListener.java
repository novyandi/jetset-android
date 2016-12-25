package xyz.girudo.jetset.interfaces;

import android.view.View;

import io.realm.RealmObject;

/**
 * Created by Novyandi Nurahmad on 11/19/16
 */

public interface OnItemClickListener {

    void onItemClickListener(View parent, View view, int viewTypeHolder, int position, RealmObject item);
}