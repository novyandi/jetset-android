package xyz.girudo.jetset.helpers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import xyz.girudo.jetset.fragments.BaseFragment;


public class FragmentHelper {
    private static FragmentHelper fragmentHelper;
    private FragmentManager fragmentManager;

    public static FragmentHelper getInstance(FragmentManager fragmentManager) {
        if (fragmentHelper == null) {
            fragmentHelper = new FragmentHelper();
        }
        fragmentHelper.setFragmentManager(fragmentManager);
        return fragmentHelper;
    }

    public void replaceFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getClass().getName());
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(container, fragment, fragment.getClass().getName());
        ft.commit();
    }

    public void addFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getClass().getName());
        }
        ft.add(container, fragment);
        ft.commit();
    }

    public void replaceFragment(int container, Fragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getTag());
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(container, fragment);
        ft.commit();
    }

    public void addFragment(int container, Fragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getTag());
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.add(container, fragment);
        ft.commit();
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}