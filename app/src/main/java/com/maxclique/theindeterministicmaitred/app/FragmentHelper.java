package com.maxclique.theindeterministicmaitred.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

/**
 * Created by Austin on 6/30/2014.
 */
public class FragmentHelper {

    Activity mActivity;
    FragmentManager mFManager;
    int mContainerResource;

    public FragmentHelper(Activity activity, int containerResource) {
        mActivity = activity;
        mFManager = mActivity.getFragmentManager();
        mContainerResource = containerResource;
    }

    public void goToHome() {
        FragmentTransaction ft = mFManager.beginTransaction();
        Fragment fragment = new ShowRestaurantsFragment();
        ft.replace(mContainerResource,fragment).commit();
    }
}
