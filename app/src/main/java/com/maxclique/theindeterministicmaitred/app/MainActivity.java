package com.maxclique.theindeterministicmaitred.app;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity
        implements ShowRestaurantsFragment.OnAddRestaurantListener {

    private FragmentHelper mFHelper;
    private List<Restaurant> mRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRestaurants = new ArrayList<Restaurant>();

        mFHelper = new FragmentHelper(this, R.id.main_container);
        setContentView(R.layout.activity_main);
        mFHelper.goToHome();
    }

    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    @Override
    public void onAddRestaurant(Restaurant restaurant) {
        mRestaurants.add(restaurant);
        mFHelper.refreshCurrentFragment();
    }
}
