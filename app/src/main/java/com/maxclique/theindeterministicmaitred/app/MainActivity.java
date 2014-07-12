package com.maxclique.theindeterministicmaitred.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
        displayRestaurantList();
    }

    public void displayRestaurantList() {
        mFHelper.goTo(FragmentHelper.SHOW_RESTAURANTS, null);
    }

    public void displayRestaurantNav(Restaurant restaurant) {
        mRestaurants.loadRestaurant(restaurant);
        Bundle args = new Bundle();
        args.putParcelable("restaurant", restaurant);
        mFHelper.goTo(FragmentHelper.RESTAURANT_NAV, args);
    }

    public void displayShowMenu(Restaurant restaurant) {
        mRestaurants.loadRestaurant(restaurant);
        Bundle args = new Bundle();
        args.putParcelable("restaurant", restaurant);
        mFHelper.goTo(FragmentHelper.SHOW_MENU, args);
    }

    public void displayAddDish(Restaurant restaurant) {
        Bundle args = new Bundle();
        args.putParcelable("restaurant", restaurant);
        mFHelper.goTo(FragmentHelper.ADD_DISH, args);
    }

    public void displayAddRestaurant() {
        mFHelper.goTo(FragmentHelper.ADD_RESTAURANT, null);
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
