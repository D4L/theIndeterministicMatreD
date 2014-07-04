package com.maxclique.theindeterministicmaitred.app;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;


public class MainActivity extends Activity
        implements ShowRestaurantsFragment.OnAddRestaurantListener {

    private FragmentHelper mFHelper;
    private LazyRestaurantsHelper mRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRestaurants = new LazyRestaurantsHelper(this);

        mFHelper = new FragmentHelper(this, R.id.main_container);
        setContentView(R.layout.activity_main);
        mFHelper.goToHome();
    }

    public void displayShowMenu() {
        mFHelper.goToShowMenu();
    }

    public List<Restaurant> getRestaurants() {
        return mRestaurants.getRestaurantList();
    }

    @Override
    public void onAddRestaurant(Restaurant restaurant) {
        mRestaurants.addRestaurant(restaurant);
        mFHelper.refreshCurrentFragment();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRestaurants.save();
    }
}
