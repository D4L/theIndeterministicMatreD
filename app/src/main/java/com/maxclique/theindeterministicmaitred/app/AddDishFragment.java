package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Austin on 7/6/2014.
 */
public class AddDishFragment extends Fragment implements FragmentHelper.DoneableFragment,
        FragmentHelper.CancelableFragment {

    Restaurant mRestaurant;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.add_dish_fragment, viewGroup, false);

        mRestaurant = grabRestaurantFromArgs();

        return view;
    }

    private Restaurant grabRestaurantFromArgs() {
        return getArguments().getParcelable("restaurant");
    }

    @Override
    public void onActionDone() {

    }

    @Override
    public void onActionCancel() {
        ((MainActivity) getActivity()).displayShowMenu(mRestaurant);
    }

}
