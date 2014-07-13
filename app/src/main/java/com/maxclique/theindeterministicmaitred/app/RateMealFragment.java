package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Austin on 7/12/2014.
 */
public class RateMealFragment extends Fragment {

    private Restaurant mRestaurant;
    private ArrayList<Dish> mPrevRandom;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.rate_meal_fragment, viewGroup, false);

        mRestaurant = grabRestaurantFromArgs();
        mPrevRandom = grabPrevDishesFromArgs();
        ((TextView)view.findViewById(R.id.chosen_dish)).setText(
                mPrevRandom.get(mPrevRandom.size() - 1).getName());

        return view;
    }

    private Restaurant grabRestaurantFromArgs() {
        return getArguments().getParcelable("restaurant");
    }

    private ArrayList<Dish> grabPrevDishesFromArgs() {
        return getArguments().getParcelableArrayList("prevDishes");
    }
}
