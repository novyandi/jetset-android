package xyz.girudo.jetset.holders;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.adapters.NewestOfferPagerAdapter;
import xyz.girudo.jetset.controllers.RealmDataControl;
import xyz.girudo.jetset.entities.HomeMenu;
import xyz.girudo.jetset.entities.Offer;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 11/21/16
 */

public class HomeMenuHeaderHolder extends BaseHolder<HomeMenu> {
    public static final int OFFSCREEN_PAGE_LIMIT = 3;
    @BindView(R.id.am_pager_data)
    ViewPager dataPager;
    @BindView(R.id.am_indicator)
    CirclePageIndicator indicator;
    private NewestOfferPagerAdapter newestOfferPagerAdapter;
    private List<Offer> offers;
    private Handler handler;
    private Runnable update;
    private int currentPagerPage = 0;

    public HomeMenuHeaderHolder(Context context, View itemView, OnItemClickListener onItemClickListener) {
        super(context, itemView, onItemClickListener);
    }

    @Override
    public void initHolder(View itemView) {
        super.initHolder(itemView);
        offers = new ArrayList<>();
        newestOfferPagerAdapter = new NewestOfferPagerAdapter(context, offers);
        //set pager autoslide
        handler = new Handler();
        update = new Runnable() {
            @Override
            public void run() {
                if (currentPagerPage == OFFSCREEN_PAGE_LIMIT) {
                    currentPagerPage = 0;
                }
                if (dataPager != null)
                    dataPager.setCurrentItem(currentPagerPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 10000, 10000);
        /////////////////////
        dataPager.setOffscreenPageLimit(OFFSCREEN_PAGE_LIMIT);
        dataPager.setAdapter(newestOfferPagerAdapter);
        dataPager.setCurrentItem(1, true);
        indicator.setViewPager(dataPager);
        setOfferData();
    }

    private void setOfferData() {
        offers.clear();
        offers.addAll(RealmDataControl.getInstance(context).getOfferData());
        newestOfferPagerAdapter.notifyDataSetChanged();
    }
}
