package xyz.girudo.jetset.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.girudo.jetset.R;
import xyz.girudo.jetset.callbacks.OnActionbarListener;
import xyz.girudo.jetset.fragments.BaseFragment;
import xyz.girudo.jetset.helpers.FragmentHelper;
import xyz.girudo.jetset.helpers.TextHelper;
import xyz.girudo.jetset.interfaces.ActivityInterface;

/**
 * Created by Novyandi Nurahmad on 11/19/16
 */

public abstract class BaseActivity extends AppCompatActivity implements ActivityInterface {
    public final static int TRANSITION_IN_IN = R.anim.slide_in_left;
    public final static int TRANSITION_IN_OUT = R.anim.slide_out_right;
    public final static int TRANSITION_OUT_IN = R.anim.slide_in_right;
    public final static int TRANSITION_OUT_OUT = R.anim.slide_out_left;
    public final static int TRANSITION_REQUEST_CODE = 391;
    protected Context context;
    private View actionBarView;
    private FragmentHelper fragmentHelper;
    private OnActionbarListener actionbarListener;
    private TextView tvActionBarTitle;
    private ImageView leftIcon, rightIcon;
    private boolean badgeRightShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHelper = FragmentHelper.getInstance(getSupportFragmentManager());
        context = this;
        badgeRightShown = true;
        setContentView(getLayout());
        initView();
        setUICallbacks();
        showCustomActionBar();
        setFont();
    }

    private void setFont() {
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        TextHelper.getInstance(this).setFont(rootView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    public void showCustomActionBar() {
        showCustomActionBar(R.layout.view_custom_actionbar);
    }

    public void showCustomActionBar(int resourceView) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actionBarView = inflater.inflate(resourceView, null, false);
            actionbarClickListener();
            tvActionBarTitle = (TextView) actionBarView.findViewById(R.id.tv_title);
            leftIcon = (ImageView) actionBarView.findViewById(R.id.iv_action_left);
            rightIcon = (ImageView) actionBarView.findViewById(R.id.iv_action_right);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(actionBarView);
            actionBar.show();
        }
    }

    public void showBackActionBar() {
        if (rightIcon != null) {
            rightIcon.setVisibility(View.GONE);
        }
    }

    public void setColorActionBar(int color) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.getCustomView().setBackgroundColor(color);
    }

    private void actionbarClickListener() {
        View actionLeft = actionBarView.findViewById(R.id.left_icon_container);
        View actionRight = actionBarView.findViewById(R.id.right_icon_container);
        actionLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (actionbarListener != null) actionbarListener.onLeftIconClick();
            }
        });

        actionRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (actionbarListener != null) actionbarListener.onRightIconClick();
            }
        });
    }

    @Override
    public void onBackPressed() {
        int stackCount = getFragmentManager().getBackStackEntryCount();
        if (stackCount > 1) {
            getFragmentManager().popBackStack();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.info_exit))
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
//                                    RealmDataControl.getInstance(context).clearRealm();
                                    finish();
                                }
                            }).create().show();
        }
    }

    public FragmentHelper getFragmentHelper() {
        return fragmentHelper;
    }

    public void replaceFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        fragmentHelper.replaceFragment(container, fragment, addBackToStack);
    }

    public void replaceFragment(int container, Fragment fragment, boolean addBackToStack) {
        fragmentHelper.replaceFragment(container, fragment, addBackToStack);
    }

    public void addFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        fragmentHelper.addFragment(container, fragment, addBackToStack);
    }

    public void addFragment(int container, Fragment fragment, boolean addBackToStack) {
        fragmentHelper.addFragment(container, fragment, addBackToStack);
    }

    public void changeActivity(Class<?> destination) {
        changeActivity(destination, false, null, 0);
    }

    public void changeActivity(Class<?> destination, int flags) {
        changeActivity(destination, false, null, flags);
    }

    public void changeActivity(Class<?> destination, boolean killActivity) {
        changeActivity(destination, killActivity, null, 0);
    }

    public void changeActivity(Class<?> destination, Bundle extra) {
        changeActivity(destination, false, extra, 0);
    }

    public void changeActivity(Class<?> destination, boolean killActivity, Bundle extra, int flags) {
        Intent intent = new Intent(context, destination);
        if (extra != null) intent.putExtras(extra);
        if (flags != 0) intent.setFlags(flags);
        startActivityForResult(intent, TRANSITION_REQUEST_CODE);
        if (killActivity) finish();
        overridePendingTransition(TRANSITION_IN_IN, TRANSITION_IN_OUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TRANSITION_REQUEST_CODE) {
            overridePendingTransition(TRANSITION_OUT_IN, TRANSITION_OUT_OUT);
        }
    }

    public void setActionBarTitle(String title, boolean visible) {
        if (tvActionBarTitle != null && title != null) {
            if (visible) {
                tvActionBarTitle.setVisibility(View.VISIBLE);
                tvActionBarTitle.setText(title);
            } else {
                tvActionBarTitle.setVisibility(View.GONE);
            }
        } else {
            tvActionBarTitle.setVisibility(View.GONE);
        }
    }

    public void setActionbarListener(OnActionbarListener actionbarListener) {
        this.actionbarListener = actionbarListener;
    }

    public void setLeftIcon(int drawableRes) {
        if (leftIcon != null) leftIcon.setImageResource(drawableRes);
    }

    public void setRightIcon(int drawableRes) {
        if (rightIcon != null) {
            TextView badge = (TextView) findViewById(R.id.actionbar_notification_badge_right);
            if (badge != null && !badge.getText().toString().equalsIgnoreCase("") && isBadgeRightShown()) {
                if (Integer.parseInt((String) badge.getText()) > 0)
                    badge.setVisibility(View.VISIBLE);
            }
            if (drawableRes == 0) {
                rightIcon.setVisibility(View.GONE);
                if (badge != null) badge.setVisibility(View.GONE);
            } else {
                rightIcon.setImageResource(drawableRes);
                rightIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setDefaultActionbarIcon() {
        findViewById(R.id.right_icon_container).setVisibility(View.VISIBLE);
        rightIcon.setVisibility(View.VISIBLE);
        setLeftIcon(R.drawable.ic_menuicon);
        setRightIcon(R.drawable.ic_carticon);
    }

    public boolean isBadgeRightShown() {
        return badgeRightShown;
    }

    public void setBadgeRightShown(boolean badgeRightShown) {
        this.badgeRightShown = badgeRightShown;
    }
}