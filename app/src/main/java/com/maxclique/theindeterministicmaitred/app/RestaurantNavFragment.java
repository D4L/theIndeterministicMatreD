package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

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
            Bundle args = new Bundle();
            args.putParcelable("restaurant", mRestaurant);
            ((MainActivity) getActivity()).goTo(FragmentHelper.FragmentId.SHOW_MENU, args);
        }
    }

    private class GetDishOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (mRestaurant.getMenu().getDishes().size() == 0) {
                Toast.makeText(getActivity(), R.string.add_dish_first, Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle args = new Bundle();
            args.putParcelable("restaurant", mRestaurant);
            ((MainActivity) getActivity()).goTo(FragmentHelper.FragmentId.CHOOSE_DISH, args);
        }
    }
}
