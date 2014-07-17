package com.maxclique.theindeterministicmaitred.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private FragmentHelper mFHelper;
    private LazyRestaurantsHelper mRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRestaurants = new LazyRestaurantsHelper(this);

        mFHelper = new FragmentHelper(this, R.id.main_container);
        setContentView(R.layout.activity_main);
        goTo(FragmentHelper.FragmentId.SHOW_RESTAURANTS, null);
    }

    public void goTo(FragmentHelper.FragmentId fragmentId, Bundle args) {
        SmartBundle smartBundle = new SmartBundle(fragmentId, args);
        mFHelper.goTo(fragmentId, smartBundle);
    }

    public void loadRestaurant(Restaurant restaurant) {
        mRestaurants.loadRestaurant(restaurant);
    }

    public List<Restaurant> getRestaurants() {
        return mRestaurants.getRestaurantList();
    }

    public void addRestaurant(Restaurant restaurant) {
        mRestaurants.addRestaurant(restaurant);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRestaurants.save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return mFHelper.createOptionsMenu(menu, getMenuInflater());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mFHelper.optionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mFHelper.goBack()) {
            super.onBackPressed();
        }
    }
}
