package com.maxclique.theindeterministicmaitred.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Austin on 6/30/2014.
 */
public class FragmentHelper {

    public enum FragmentId {
        SHOW_RESTAURANTS, SHOW_MENU, ADD_DISH, ADD_RESTAURANT, RESTAURANT_NAV,
        CHOOSE_DISH, RATE_MEAL
    }

    public static List<FragmentId> NON_BACK_FRAGMENTS = Arrays.asList(
            FragmentId.ADD_DISH, FragmentId.ADD_RESTAURANT,
            FragmentId.CHOOSE_DISH, FragmentId.RATE_MEAL
    );

    private Activity mActivity;
    private FragmentManager mFManager;
    private int mContainerResource;

    public FragmentHelper(Activity activity, int containerResource) {
        mActivity = activity;
        mFManager = mActivity.getFragmentManager();
        mContainerResource = containerResource;
    }

    public void goTo(FragmentId destination, SmartBundle args) {
        if (mFManager.popBackStackImmediate(destination.toString(), 0)) {
            return;
        }
        FragmentTransaction ft = mFManager.beginTransaction();
        Fragment fragment = resolveFragment(destination);
        if (args != null) {
            fragment.setArguments(args.getBundle());
        }
        fragment.setHasOptionsMenu(true);

        ft.replace(mContainerResource, fragment, destination.toString())
                .addToBackStack(destination.toString()).commit();
    }

    private Fragment resolveFragment(FragmentId destination) {
        switch (destination) {
            case SHOW_RESTAURANTS:
                return new ShowRestaurantsFragment();
            case SHOW_MENU:
                return new ShowMenuFragment();
            case ADD_DISH:
                return new AddDishFragment();
            case ADD_RESTAURANT:
                return new AddRestaurantFragment();
            case RESTAURANT_NAV:
                return new RestaurantNavFragment();
            case CHOOSE_DISH:
                return new ChooseDishFragment();
            case RATE_MEAL:
                return new RateMealFragment();
            default:
                return new ShowRestaurantsFragment();
        }
    }

    public boolean createOptionsMenu(Menu menu, MenuInflater menuInflater) {
        View customActionBar;
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
            case ADD_RESTAURANT:
                customActionBar = setCustomActionBar(R.layout.done_cancel_actionbar);
                setActionBarDone(customActionBar);
                setActionBarCancel(customActionBar);
                break;
            case RESTAURANT_NAV:
            case CHOOSE_DISH:
            case RATE_MEAL:
                setRegularActionBar();
                setHomeButtonBack(true);
                break;
        }
        return true;
    }

    private FragmentId getCurrentFragmentId() {
        return FragmentId.valueOf(getCurrentFragment().getTag());
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
                goBack();
                return true;
        }
        return false;
    }

    public boolean goBack() {
        if (mFManager.getBackStackEntryCount() > 1) {
            FragmentId currentFragmentId;
            do {
                mFManager.popBackStackImmediate();
                currentFragmentId = getCurrentFragmentId();
            } while (NON_BACK_FRAGMENTS.contains(currentFragmentId));
            return true;
        }
        return false;
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
