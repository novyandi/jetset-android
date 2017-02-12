package xyz.girudo.jetset.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmObject;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.activities.MainActivity;
import xyz.girudo.jetset.activities.SplashScreenActivity;
import xyz.girudo.jetset.adapters.LeftMenuAdapter;
import xyz.girudo.jetset.controllers.JetsetApp;
import xyz.girudo.jetset.controllers.Preferences;
import xyz.girudo.jetset.controllers.RealmDataControl;
import xyz.girudo.jetset.entities.LeftMenu;
import xyz.girudo.jetset.helpers.AlertHelper;
import xyz.girudo.jetset.holders.TypeHolder;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

/**
 * Created by Novyandi Nurahmad on 11/19/16
 */

public class LeftSliderFragment extends BaseFragment implements OnItemClickListener {
    @BindView(R.id.ls_lv_left_menu)
    RecyclerView lvLeftMenu;
    private LeftMenuAdapter leftMenuAdapter;
    private List<LeftMenu> leftMenus;
    private boolean closedDrawer = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leftMenus = new ArrayList<>();
        leftMenuAdapter = new LeftMenuAdapter(activity, true, false, this);
        leftMenus.addAll(RealmDataControl.getInstance(activity).getLeftMenuItemData());
        leftMenuAdapter.setData(leftMenus);
        leftMenuAdapter.setSingleSelection(true);
        leftMenuAdapter.toggleSelection(1);
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        lvLeftMenu.setItemAnimator(new DefaultItemAnimator());
        lvLeftMenu.setLayoutManager(new LinearLayoutManager(activity));
        lvLeftMenu.setAdapter(leftMenuAdapter);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void setUICallbacks() {
    }

    @Override
    public void updateUI() {
    }

    @Override
    public String getPageTitle() {
        return "";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_left_slider;
    }


    @Override
    public void onItemClickListener(View parent, View view, int viewTypeHolder, int position, RealmObject item) {
        BaseFragment fragment = null;
        if (position != leftMenuAdapter.getLastSelectionPosition()) {
            leftMenuAdapter.toggleSelection(position);
            if (viewTypeHolder == TypeHolder.TYPE_ITEM) {
                AlertHelper.getInstance().showAlert(activity, "show " + ((LeftMenu) item).getTitle());
                switch (position) {
//            case 0:
//                fragment = new HomeFragment();
//                break;
                    case 1:
                        fragment = new HomeFragment();
                        break;
                    case 2:
//                fragment = new PromotionFragment();
                        break;
                    case 3:
//                fragment = new EventListFragment();
                        break;
                    case 4:
//                fragment = new SurveysFragment();
                        break;
                    case 5:
//                fragment = new SurveysFragment();
                        break;
                    case 6:
//                fragment = new AccountFragment();
                        break;
                    case 7:
                        fragment = new AccountFragment();
                        break;
                    case 8:
//                fragment = new SurveysFragment();
                        break;
                    case 9:
//                fragment = new SurveysFragment();
                        break;
                    case 10:
                        new AlertDialog.Builder(activity)
                                .setMessage(getString(R.string.info_logout))
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                JetsetApp.getSession(activity).setToken("");
                                                JetsetApp.setConfig(activity, JetsetApp.TOKEN_KEY, "");
                                                JetsetApp.setConfig(activity, Preferences.USER_LOGIN, "N");
                                                JetsetApp.removeConfig(activity, Preferences.FIRST_START);
                                                RealmDataControl.getInstance(activity).clearRealm();
                                                getBaseActivity().changeActivity(SplashScreenActivity.class, true, null, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            }
                                        }).create().show();
                        break;
                    default:
                        break;
                }
            }
        }
        changeFragment(fragment);
    }

    private void changeFragment(final BaseFragment fragment) {
        if (closedDrawer) ((MainActivity) activity).toggleDrawer(Gravity.START);
        closedDrawer = true;
        if (fragment != null) {
            getBaseActivity().setDefaultActionbarIcon();
            new Handler(activity.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    replaceFragment(R.id.am_fragment_container, fragment, false);
                }
            }, 300);
        }
    }
}