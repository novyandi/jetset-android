package xyz.girudo.jetset.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.entities.ItemSell;
import xyz.girudo.jetset.helpers.PictureHelper;
import xyz.girudo.jetset.helpers.TextHelper;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 5/11/17
 */

public class ItemSellHolder extends BaseHolder<ItemSell> {
    @BindView(R.id.iv_item_image)
    ImageView itemImage;
    @BindView(R.id.tv_item_title)
    TextView itemTitle;
    @BindView(R.id.tv_item_price)
    TextView itemPrice;

    public ItemSellHolder(Context context, View itemView, OnItemClickListener onItemClickListener) {
        super(context, itemView, onItemClickListener);
    }

    @Override
    public void setData(ItemSell entity) {
        super.setData(entity);
        PictureHelper.getInstance(context).loadImage(entity.getImages().get(0).getString(), itemImage, R.drawable.logo);
        itemTitle.setText(entity.getTitle());
        itemPrice.setText(TextHelper.getInstance(context).setCurrencyText(entity.getPrice()));
    }
}