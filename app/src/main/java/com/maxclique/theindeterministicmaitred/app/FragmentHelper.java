package com.maxclique.theindeterministicmaitred.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Austin on 6/30/2014.
 */
public class FragmentHelper {

    public static final int SHOW_RESTAURANTS = 0;
    public static final int SHOW_MENU = 1;
    public static final int ADD_DISH = 2;

    private Activity mActivity;
    private FragmentManager mFManager;
    private int mContainerResource;

    public FragmentHelper(Activity activity, int containerResource) {
        mActivity = activity;
        mFManager = mActivity.getFragmentManager();
        mContainerResource = containerResource;
    }

    public void goTo(int destination, Bundle args) {
        FragmentTransaction ft = mFManager.beginTransaction();
        Fragment fragment;
        switch (destination) {
            case SHOW_RESTAURANTS:
                fragment = new ShowRestaurantsFragment();
                break;
            case SHOW_MENU:
                fragment = new ShowMenuFragment();
                break;
            case ADD_DISH:
                fragment = new AddDishFragment();
                break;
            default:
                fragment = new ShowRestaurantsFragment();
        }
        if (args != null) {
            fragment.setArguments(args);
        }
        fragment.setHasOptionsMenu(true);
        ft.replace(mContainerResource, fragment, Integer.toString(destination))
                .addToBackStack(null).commit();
    }

    public void refreshCurrentFragment() {
        RefreshableFragment fragment = (RefreshableFragment)
                mFManager.findFragmentById(mContainerResource);
        fragment.refresh();
    }

    public boolean createOptionsMenu(Menu menu, MenuInflater menuInflater) {
        switch (getCurrentFragmentId()) {
            case SHOW_RESTAURANTS:
                setRegularActionBar();
                setHomeButtonBack(false);
                menuInflater.inflate(R.menu.add_and_search_item_menu, menu);
                break;
            case SHOW_MENU:
                setRegularActionBar();
                setHomeButtonBack(true);
                menuInflater.inflate(R.menu.add_item_menu, menu);
                break;
            case ADD_DISH:
                View customActionBar = setCustomActionBar(R.layout.done_cancel_actionbar);
                setActionBarDone(customActionBar);
                setActionBarCancel(customActionBar);
                break;
        }
        return true;
    }

    private int getCurrentFragmentId() {
        return Integer.parseInt(getCurrentFragment().getTag());
    }

    private Fragment getCurrentFragment() {
        return mFManager.findFragmentById(mContainerResource);
    }

    private void setHomeButtonBack(boolean activated) {
        ActionBar actionBar = mActivity.getActionBar();
        if (activated) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        } else {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
    }

    private void setRegularActionBar() {
        ActionBar actionBar = mActivity.getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
    }

    private View setCustomActionBar(int actionBarResource) {
        ActionBar actionBar = mActivity.getActionBar();
        LayoutInflater inflater = (LayoutInflater) actionBar.getThemedContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = inflater.inflate(actionBarResource, null);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(actionBarView, new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return actionBarView;
    }

    private void setActionBarDone(View actionBar) {
        actionBar.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DoneableFragment fragment = (DoneableFragment) getCurrentFragment();
                        fragment.onActionDone();
                    }
                }
        );
    }

    private void setActionBarCancel(View actionBar) {
        actionBar.findViewById(R.id.actionbar_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CancelableFragment fragment = (CancelableFragment) getCurrentFragment();
                        fragment.onActionCancel();
                    }
                }
        );
    }

    public boolean optionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                AddableFragment fragment = (AddableFragment) getCurrentFragment();
                fragment.onActionAdd();
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

    public interface AddableFragment {
        public void onActionAdd();
    }

    public interface DoneableFragment {
        public void onActionDone();
    }

    public interface CancelableFragment {
        public void onActionCancel();
    }
}
