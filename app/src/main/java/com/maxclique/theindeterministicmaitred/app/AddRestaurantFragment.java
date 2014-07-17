package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Austin on 7/10/2014.
 */
public class AddRestaurantFragment extends Fragment implements FragmentHelper.DoneableFragment,
        FragmentHelper.CancelableFragment {

    private EditText mRestaurantNameTextBox;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.add_restaurant_fragment, viewGroup, false);
        mRestaurantNameTextBox = (EditText) view.findViewById(R.id.name);

        return view;
    }

    @Override
    public void onActionDone() {
        String restaurantName = mRestaurantNameTextBox.getText().toString();
        if (restaurantName.equals("")) {
            Toast toast = Toast.makeText(getActivity(),
                    R.string.enter_restaurant_name, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Restaurant restaurant = new Restaurant(restaurantName);
        Bundle args = new Bundle();
        args.putParcelable("restaurant", restaurant);
        ((MainActivity) getActivity()).addRestaurant(restaurant);
        ((MainActivity) getActivity()).goTo(FragmentHelper.FragmentId.SHOW_MENU, args);
    }

    @Override
    public void onActionCancel() {
        ((MainActivity) getActivity()).goTo(FragmentHelper.FragmentId.SHOW_RESTAURANTS, null);
    }
}
