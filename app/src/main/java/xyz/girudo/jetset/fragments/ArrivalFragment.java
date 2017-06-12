package xyz.girudo.jetset.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import io.realm.RealmObject;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.adapters.ArrivalPagerAdapter;
import xyz.girudo.jetset.controllers.RealmDataControl;
import xyz.girudo.jetset.customviews.BaseSwipeRefreshLayout;
import xyz.girudo.jetset.entities.ItemSell;
import xyz.girudo.jetset.helpers.AlertHelper;
import xyz.girudo.jetset.holders.TypeHolder;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 11/20/16
 */
public class ArrivalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {
    public static final String SUBCATEGORY = "subcategory";
    @BindView(R.id.fh_swipe_layout)
    BaseSwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fh_animator)
    ViewAnimator animator;
    @BindView(R.id.fh_arrivallist)
    RecyclerView arrivalList;
    @BindView(R.id.sp_subtitle)
    AppCompatSpinner spSubtitle;

    private ArrayAdapter subtitleAdapter;
    private List<ItemSell> itemList;
    private ArrivalPagerAdapter arrivalPagerAdapter;
    private int subcategorySelection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseActivity().setColorActionBar(0);
        Bundle bundle = getArguments();
        subcategorySelection = bundle.getInt(SUBCATEGORY, 0);
        itemList = new ArrayList<>();
        arrivalPagerAdapter = new ArrivalPagerAdapter(activity, false, false, this);
        arrivalPagerAdapter.setData(itemList);
        subtitleAdapter = ArrayAdapter.createFromResource(activity, R.array.subtitle_list, R.layout.custom_spinner_dropdown_item);
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        arrivalList.setItemAnimator(new DefaultItemAnimator());
        arrivalList.setLayoutManager(new GridLayoutManager(activity, 2));
        arrivalList.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.five_dp));
        arrivalList.setAdapter(arrivalPagerAdapter);
        spSubtitle.setAdapter(subtitleAdapter);
        spSubtitle.setSelection(subcategorySelection);
    }

    @Override
    public void setUICallbacks() {
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void updateUI() {
        subtitleAdapter.notifyDataSetChanged();
        arrivalPagerAdapter.notifyDataSetChanged();
        animator.setDisplayedChild(1);
    }

    @Override
    public String getPageTitle() {
        return getString(R.string.home_txt_newArrivals);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_new_arrivals;
    }

    @Override
    public void onItemClickListener(View parent, View view, int viewTypeHolder, int position, RealmObject item) {
        if (viewTypeHolder == TypeHolder.TYPE_ITEM)
            AlertHelper.getInstance().showAlert(activity, "Show " + ((ItemSell) item).getTitle());
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
        itemList.clear();
        switch (subcategorySelection) {
            case 0:
                itemList.addAll(RealmDataControl.getInstance(getActivity()).getArrivalMensData());
                break;
            case 1:
                itemList.addAll(RealmDataControl.getInstance(getActivity()).getArrivalWomensData());
                break;
            case 2:
                itemList.addAll(RealmDataControl.getInstance(getActivity()).getArrivalKidsData());
                break;
            default:
                break;
        }
        refreshData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mItemOffset;

        ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }

    @OnItemSelected(R.id.sp_subtitle)
    void onItemSelected(int position) {
        subcategorySelection = position;
        getData();
    }
}