package xyz.girudo.jetset.customviews;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by Novyandi Nurahmad on 11/09/2016
 * Indonesia
 */
public class BaseSwipeRefreshLayout extends SwipeRefreshLayout {

    private boolean selfCancelled = false;

    public BaseSwipeRefreshLayout(Context context) {
        super(context);
    }

    public BaseSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        if (isRefreshing()) {
            clearAnimation();
            setRefreshing(false);
            selfCancelled = true;
        }
        return super.onSaveInstanceState();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        selfCancelled = false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && selfCancelled) {
            setRefreshing(true);
        }
    }
}