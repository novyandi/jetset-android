package xyz.girudo.jetset.holders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.entities.LeftMenu;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 11/19/16
 */

public class LeftMenuItemHolder extends BaseHolder<LeftMenu> {
    @BindView(R.id.title)
    TextView txtTitle;
    @BindView(R.id.separator)
    LinearLayout separator;

    public LeftMenuItemHolder(Context context, View itemView, OnItemClickListener onItemClickListener) {
        super(context, itemView, onItemClickListener);
    }

    @Override
    public void setData(LeftMenu entity) {
        super.setData(entity);
        txtTitle.setText(entity.getTitle());
    }

    public void setShowSeparator(boolean isShow) {
        separator.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}