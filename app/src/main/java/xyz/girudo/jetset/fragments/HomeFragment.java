package xyz.girudo.jetset.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ViewAnimator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmObject;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.adapters.HomeMenuPagerAdapter;
import xyz.girudo.jetset.controllers.CleverTapControl;
import xyz.girudo.jetset.controllers.GoogleTagControl;
import xyz.girudo.jetset.controllers.RealmDataControl;
import xyz.girudo.jetset.customviews.BaseSwipeRefreshLayout;
import xyz.girudo.jetset.entities.Events.OnSelectLeftMenu;
import xyz.girudo.jetset.entities.HomeMenu;
import xyz.girudo.jetset.interfaces.OnItemClickListener;
import xyz.girudo.jetset.tags.EventTag;
import xyz.girudo.jetset.tags.ParamTag;

/**
 * Created by Novyandi Nurahmad on 11/20/16
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {
    @BindView(R.id.fh_swipe_layout)
    BaseSwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fh_animator)
    ViewAnimator animator;
    @BindView(R.id.fh_homelist)
    RecyclerView homeList;

    private List<HomeMenu> homeMenus;
    private HomeMenuPagerAdapter homeMenuPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeMenus = new ArrayList<>();
        homeMenuPagerAdapter = new HomeMenuPagerAdapter(activity, true, false, this);
        homeMenuPagerAdapter.setData(homeMenus);
        getBaseActivity().setColorActionBar(0);
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        homeList.setItemAnimator(new DefaultItemAnimator());
        homeList.setLayoutManager(new LinearLayoutManager(activity));
        homeList.setAdapter(homeMenuPagerAdapter);
        GoogleTagControl.logEvent(EventTag.LOGINEVENT.eventName(), null);
    }

    @Override
    public void setUICallbacks() {
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void updateUI() {
        homeMenuPagerAdapter.notifyDataSetChanged();
        animator.setDisplayedChild(1);
    }

    @Override
    public String getPageTitle() {
        return getString(R.string.home_title);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onItemClickListener(View parent, View view, int viewTypeHolder, int position, RealmObject item) {
        switch (position) {
            case 1:
                EventBus.getDefault().post(new OnSelectLeftMenu(2));
                break;
            case 2:
                EventBus.getDefault().post(new OnSelectLeftMenu(3));
                break;
            case 3:
                EventBus.getDefault().post(new OnSelectLeftMenu(4));
                break;
            case 4:
                EventBus.getDefault().post(new OnSelectLeftMenu(5));
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void refreshData() {
        new Handler(activity.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                updateUI();
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void getData() {
        animator.setDisplayedChild(0);
        homeMenus.clear();
        homeMenus.addAll(RealmDataControl.getInstance(getActivity()).getHomeMenuData());
        refreshData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}
