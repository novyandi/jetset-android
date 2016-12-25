package xyz.girudo.jetset.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.entities.HomeMenu;
import xyz.girudo.jetset.helpers.PictureHelper;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 11/21/16
 */

public class HomeMenuItemHolder extends BaseHolder<HomeMenu> {
    @BindView(R.id.home_iv_featured_home)
    ImageView ivImage;
    @BindView(R.id.home_txt_featured_home)
    TextView txtTitle;

    public HomeMenuItemHolder(Context context, View itemView, OnItemClickListener onItemClickListener) {
        super(context, itemView, onItemClickListener);
    }

    @Override
    public void setData(HomeMenu entity) {
        super.setData(entity);
        PictureHelper.getInstance(context).loadImage(entity.getImage(), ivImage, R.drawable.logo);
        txtTitle.setText(entity.getTitle());
    }
}
