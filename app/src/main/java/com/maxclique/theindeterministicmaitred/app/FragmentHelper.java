package com.maxclique.theindeterministicmaitred.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Austin on 6/30/2014.
 */
public class FragmentHelper {

    private final int SHOW_RESTAURANTS = 0;
    private final int SHOW_MENU = 1;

    private Activity mActivity;
    private FragmentManager mFManager;
    private int mContainerResource;

    public FragmentHelper(Activity activity, int containerResource) {
        mActivity = activity;
        mFManager = mActivity.getFragmentManager();
        mContainerResource = containerResource;
    }

    public void goToHome() {
        goTo(SHOW_RESTAURANTS, null);
    }

    public void goToShowMenu(Restaurant restaurant) {
        Bundle args = new Bundle();
        args.putParcelable("restaurant", restaurant);
        goTo(SHOW_MENU, args);
    }

    private void goTo(int destination, Bundle args) {
        FragmentTransaction ft = mFManager.beginTransaction();
        Fragment fragment;
        switch (destination) {
            case SHOW_RESTAURANTS:
                fragment = new ShowRestaurantsFragment();
                break;
            case SHOW_MENU:
                fragment = new ShowMenuFragment();
                break;
            default:
                fragment = new ShowRestaurantsFragment();
        }
        if (args != null) {
            fragment.setArguments(args);
        }
        ft.replace(mContainerResource, fragment, Integer.toString(destination))
                .addToBackStack(null).commit();
    }

    private void setActionBar(boolean setUpEnabled) {
        ActionBar actionBar = mActivity.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(setUpEnabled);
    }

    public void refreshCurrentFragment() {
        RefreshableFragment fragment = (RefreshableFragment)
                mFManager.findFragmentById(mContainerResource);
        fragment.refresh();
    }

    public boolean prepareOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.add_item_menu, menu);
        int currentFragment = Integer.parseInt(
                mFManager.findFragmentById(mContainerResource).getTag());
        switch (currentFragment) {
            case SHOW_RESTAURANTS:
                setActionBar(false);
                break;
            case SHOW_MENU:
                setActionBar(true);
                break;
        }
        return true;
    }

    public boolean optionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                return true;
            case android.R.id.home:
                mFManager.popBackStack();
                return true;
        }
        return false;
    }

    public interface RefreshableFragment {
        public void refresh();
    }
}
