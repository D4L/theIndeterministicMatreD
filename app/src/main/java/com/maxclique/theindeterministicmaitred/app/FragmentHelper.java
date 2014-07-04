package com.maxclique.theindeterministicmaitred.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

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
        ft.replace(mContainerResource, fragment).commit();
    }

    public void goToShowMenu(Restaurant restaurant) {
        FragmentTransaction ft = mFManager.beginTransaction();
        Fragment fragment = new ShowMenuFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurant", restaurant);
        fragment.setArguments(args);
        ft.replace(mContainerResource, fragment).addToBackStack(null).commit();
    }

    public void refreshCurrentFragment() {
        RefreshableFragment fragment = (RefreshableFragment)
                mFManager.findFragmentById(mContainerResource);
        fragment.refresh();
    }

    public interface RefreshableFragment {
        public void refresh();
    }
}
