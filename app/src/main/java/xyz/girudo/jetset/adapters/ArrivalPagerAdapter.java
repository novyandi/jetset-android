package xyz.girudo.jetset.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.realm.RealmObject;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.entities.ItemSell;
import xyz.girudo.jetset.holders.BaseHolder;
import xyz.girudo.jetset.holders.ItemSellHolder;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 05/11/17
 */
public class ArrivalPagerAdapter extends BaseAdapter<ItemSell, RealmObject, RealmObject> {
    private OnItemClickListener onItemClickListener;

    public ArrivalPagerAdapter(Context context, boolean withHeader, boolean withFooter, OnItemClickListener onItemClickListener) {
        super(context, withHeader, withFooter);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemSellHolder(context, LayoutInflater.from(context).inflate(R.layout.list_item_sell, parent, false), onItemClickListener);

    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        ((ItemSellHolder) holder).setData(getItem(position));
    }
}