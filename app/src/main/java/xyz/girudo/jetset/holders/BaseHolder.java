package xyz.girudo.jetset.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import io.realm.RealmObject;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 11/19/16
 */

public abstract class BaseHolder<T extends RealmObject> extends RecyclerView.ViewHolder {
    protected Context context;
    protected LayoutInflater inflater;
    protected T entity;

    BaseHolder(Context context, final View itemView, final OnItemClickListener onItemClickListener) {
        super(itemView);
        this.context = context;
        inflater = LayoutInflater.from(context);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClickListener(itemView, view, getItemViewType(), getLayoutPosition(), entity);
            }
        });
        initHolder(itemView);
    }

    public void initHolder(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    public void setData(T entity) {
        this.entity = entity;
    }
}