package xyz.girudo.jetset.activities;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.callbacks.OnActionbarListener;
import xyz.girudo.jetset.fragments.HomeFragment;
import xyz.girudo.jetset.helpers.AlertHelper;

/**
 * Created by Novyandi Nurahmad on 11/19/16
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    public OnActionbarListener actionbarListener = new OnActionbarListener() {
        @Override
        public void onLeftIconClick() {
            toggleDrawer(Gravity.START);
        }

        @Override
        public void onRightIconClick() {
            AlertHelper.getInstance().showAlert(context, "show right icon action");
        }
    };

    @Override
    public void initView() {
        ButterKnife.bind(MainActivity.this);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        replaceFragment(R.id.am_fragment_container, new HomeFragment(), false);
    }


    @Override
    public void setUICallbacks() {
        setActionbarListener(actionbarListener);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View view = MainActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void updateUI() {

    }

    public void toggleDrawer(int gravity) {
        if (drawerLayout.isDrawerOpen(gravity)) {
            drawerLayout.closeDrawer(gravity);
        } else {
            drawerLayout.closeDrawers();
            drawerLayout.openDrawer(gravity);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT) || drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}