package xyz.girudo.jetset.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.realm.RealmObject;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.entities.HomeMenu;
import xyz.girudo.jetset.holders.BaseHolder;
import xyz.girudo.jetset.holders.HomeMenuHeaderHolder;
import xyz.girudo.jetset.holders.HomeMenuItemHolder;
import xyz.girudo.jetset.holders.TypeHolder;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 11/21/16
 */
public class HomeMenuPagerAdapter extends BaseAdapter<HomeMenu, RealmObject, RealmObject> {
    private OnItemClickListener onItemClickListener;

    public HomeMenuPagerAdapter(Context context, boolean withHeader, boolean withFooter, OnItemClickListener onItemClickListener) {
        super(context, withHeader, withFooter);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TypeHolder.TYPE_HEADER)
            return new HomeMenuHeaderHolder(context, LayoutInflater.from(context).inflate(R.layout.list_header_home_menu, parent, false), onItemClickListener);
        else
            return new HomeMenuItemHolder(context, LayoutInflater.from(context).inflate(R.layout.list_item_home_menu, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (holder instanceof HomeMenuItemHolder)
            ((HomeMenuItemHolder) holder).setData(getItem(position));
    }
}