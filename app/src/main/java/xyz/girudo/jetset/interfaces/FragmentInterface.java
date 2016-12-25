package xyz.girudo.jetset.interfaces;

import android.view.View;

public interface FragmentInterface {
    void initView(View view);

    void setUICallbacks();

    void updateUI();

    String getPageTitle();

    int getFragmentLayout();

    String getTag();
}
