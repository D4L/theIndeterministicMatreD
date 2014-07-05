package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Austin on 7/3/2014.
 */
public class ShowMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.show_menu_fragment, viewGroup, false);

        /*
        List<Restaurant> restaurants = ((MainActivity) getActivity()).getRestaurants();
        mRestaurantAdapter = new RestaurantAdapter(getActivity(), R.layout.restaurant_list_fragment,
                restaurants);
        ((ListView) view.findViewById(R.id.restaurant_list)).setAdapter(mRestaurantAdapter);
        */

        return view;
    }

}
