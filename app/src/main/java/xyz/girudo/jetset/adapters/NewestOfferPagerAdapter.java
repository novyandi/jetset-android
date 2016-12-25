package xyz.girudo.jetset.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import xyz.girudo.jetset.R;
import xyz.girudo.jetset.entities.Offer;
import xyz.girudo.jetset.helpers.PictureHelper;

/**
 * Created by Novyandi Nurahmad on 11/20/16
 */

public class NewestOfferPagerAdapter extends PagerAdapter {
    private List<Offer> offers;
    private LayoutInflater inflater;
    private PictureHelper pictureHelper;

    public NewestOfferPagerAdapter(Context context, List<Offer> offers) {
        this.inflater = LayoutInflater.from(context);
        this.offers = offers;
        this.pictureHelper = PictureHelper.getInstance(context);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.view_home_header, container, false);
        ImageView ivOffer = (ImageView) view.findViewById(R.id.iv_featured_offer);
        Offer offer = offers.get(position);
        pictureHelper.loadImage(offer.getImageOffer(), ivOffer, R.drawable.logo);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
