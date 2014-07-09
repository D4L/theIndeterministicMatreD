package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Austin on 7/6/2014.
 */
public class AddDishFragment extends Fragment implements FragmentHelper.DoneableFragment,
        FragmentHelper.CancelableFragment {

    Restaurant mRestaurant;
    EditText mDishNameTextBox;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.add_dish_fragment, viewGroup, false);
        mDishNameTextBox = (EditText) view.findViewById(R.id.name);

        mRestaurant = grabRestaurantFromArgs();

        return view;
    }

    private Restaurant grabRestaurantFromArgs() {
        return getArguments().getParcelable("restaurant");
    }

    @Override
    public void onActionDone() {
        String dishName = mDishNameTextBox.getText().toString();
        if (dishName.equals("")) {
            Toast toast = Toast.makeText(getActivity(),
                    R.string.enter_dish_name, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        mRestaurant.addDish(new Dish(dishName));
        ((MainActivity) getActivity()).displayShowMenu(mRestaurant);
    }

    @Override
    public void onActionCancel() {
        ((MainActivity) getActivity()).displayShowMenu(mRestaurant);
    }

}
