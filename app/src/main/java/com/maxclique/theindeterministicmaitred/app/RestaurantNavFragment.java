package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Austin on 7/11/2014.
 */
public class RestaurantNavFragment extends Fragment {

    private Restaurant mRestaurant;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.restaurant_nav_fragment, viewGroup, false);
        view.findViewById(R.id.view_menu_button).setOnClickListener(
                new ViewMenuOnClickListener());
        view.findViewById(R.id.get_dish_button).setOnClickListener(
                new GetDishOnClickListener());

        mRestaurant = grabRestaurantFromArgs();

        return view;
    }

    private Restaurant grabRestaurantFromArgs() {
        return getArguments().getParcelable("restaurant");
    }

    private class ViewMenuOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ((MainActivity) getActivity()).displayShowMenu(mRestaurant);
        }
    }

    private class GetDishOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }
}
