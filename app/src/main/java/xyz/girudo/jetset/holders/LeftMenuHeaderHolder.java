package xyz.girudo.jetset.holders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.customviews.CircleImageView;
import xyz.girudo.jetset.entities.LeftMenuHeader;
import xyz.girudo.jetset.helpers.PictureHelper;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 11/19/16
 */

public class LeftMenuHeaderHolder extends BaseHolder<LeftMenuHeader> {
    @BindView(R.id.ls_iv_user_image)
    CircleImageView circleImgUser;
    @BindView(R.id.ls_txt_user_name)
    TextView txtUserName;

    public LeftMenuHeaderHolder(Context context, View itemView, OnItemClickListener onItemClickListener) {
        super(context, itemView, onItemClickListener);
    }

    @Override
    public void setData(LeftMenuHeader entity) {
        super.setData(entity);
        PictureHelper.getInstance(context).loadImage(entity.getImage(), circleImgUser, R.drawable.logo);
        txtUserName.setText(entity.getName());
    }
}