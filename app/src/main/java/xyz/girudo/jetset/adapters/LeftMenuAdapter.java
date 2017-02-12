package xyz.girudo.jetset.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmObject;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.controllers.RealmDataControl;
import xyz.girudo.jetset.entities.LeftMenu;
import xyz.girudo.jetset.entities.LeftMenuHeader;
import xyz.girudo.jetset.helpers.PictureHelper;
import xyz.girudo.jetset.holders.BaseHolder;
import xyz.girudo.jetset.holders.LeftMenuHeaderHolder;
import xyz.girudo.jetset.holders.LeftMenuItemHolder;
import xyz.girudo.jetset.holders.TypeHolder;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 11/19/16
 */
public class LeftMenuAdapter extends SelectableAdapter<LeftMenu, LeftMenuHeader, RealmObject> {
    private OnItemClickListener onItemClickListener;

    public LeftMenuAdapter(Context context, boolean withHeader, boolean withFooter, OnItemClickListener onItemClickListener) {
        super(context, withHeader, withFooter);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TypeHolder.TYPE_HEADER)
            return new LeftMenuHeaderHolder(context, LayoutInflater.from(context).inflate(R.layout.list_header_left_menu, parent, false), onItemClickListener);
        else
            return new LeftMenuItemHolder(context, LayoutInflater.from(context).inflate(R.layout.list_item_left_menu, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (holder != null && holder instanceof LeftMenuItemHolder) {
            LeftMenu item = getItem(position);
            LeftMenuItemHolder leftMenuItemHolder = (LeftMenuItemHolder) holder;
            leftMenuItemHolder.setData(item);
            leftMenuItemHolder.setShowSeparator(position == 4 || position == 6 || position == 8);
            initSelectedItemMenu(leftMenuItemHolder, item, isSelected(position));
        } else if (holder != null && holder instanceof LeftMenuHeaderHolder) {
            LeftMenuHeader leftMenuHeaderData = RealmDataControl.getInstance(context).getLeftMenuHeaderData();
            ((LeftMenuHeaderHolder) holder).setData(leftMenuHeaderData);
        }
    }

    private void initSelectedItemMenu(LeftMenuItemHolder itemHolder, LeftMenu leftMenu, boolean selected) {
        View view = itemHolder.itemView;
        if (selected != view.isSelected()) {
            view.setSelected(selected);
            TextView textTitle = itemHolder.getTxtTitle();
            int paddingSelected = PictureHelper.getInstance(context).sizeInDp(5);
            if (textTitle != null) {
                if (selected)
                    textTitle.setTypeface(textTitle.getTypeface(), Typeface.BOLD);
                else
                    textTitle.setTypeface(Typeface.create(textTitle.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                textTitle.setPadding(
                        textTitle.getPaddingLeft() + (selected ? paddingSelected : -paddingSelected),
                        textTitle.getPaddingTop(),
                        textTitle.getPaddingRight(),
                        textTitle.getPaddingBottom())
                ;
            }
            View selector = itemHolder.getSelector();
            if (selector != null) selector.setVisibility(selected ? View.VISIBLE : View.GONE);
        }
    }
}