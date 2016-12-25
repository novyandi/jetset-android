package xyz.girudo.jetset.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import xyz.girudo.jetset.R;
import xyz.girudo.jetset.activities.BaseActivity;
import xyz.girudo.jetset.helpers.PictureHelper;
import xyz.girudo.jetset.interfaces.FragmentInterface;
import xyz.girudo.jetset.interfaces.OnItemClickListener;

public abstract class BaseFragment extends Fragment implements FragmentInterface, OnItemClickListener {
    public static final String FRAGMENT = "fragment";
    protected Activity activity;
    protected LayoutInflater inflater;
    protected PictureHelper pictureHelper;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pictureHelper = PictureHelper.getInstance(activity, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        view = inflater.inflate(getFragmentLayout(), container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateUI();
        setUICallbacks();
        if (getBaseActivity() != null)
            getBaseActivity().setActionBarTitle(getPageTitle(), true);
    }

    @Override
    public void onAttach(Context context) {
        this.activity = activity;
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        this.activity = activity;
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
    }

    public BaseActivity getBaseActivity() {
        if (activity instanceof BaseActivity) {
            return ((BaseActivity) activity);
        } else {
            return null;
        }
    }

    public void replaceFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(container, fragment, fragment.getClass().getSimpleName());
        ft.commit();
    }

    public void addFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.add(container, fragment);
        ft.commit();
    }

    public BaseFragment findFragment(String tag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            return (BaseFragment) fragment;
        }
        return null;
    }

    public void addChildFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.add(container, fragment);
        ft.commitAllowingStateLoss();
    }

    public void loadImage(String url, ImageView imageView, int stubImage) {
        pictureHelper.loadImage(url, imageView, stubImage);
    }

    public String checkNullString(String string) {
        return (string == null) ? "" : string;
    }

    protected void closeKeyboard() {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
