package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Austin on 7/3/2014.
 */
public class ShowMenuFragment extends Fragment implements FragmentHelper.AddableFragment {

    MenuAdapter mMenuAdapter;
    Restaurant mRestaurant;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.show_menu_fragment, viewGroup, false);

        mRestaurant = grabRestaurantFromArgs();
        if (mRestaurant != null) {
            mMenuAdapter = new MenuAdapter(getActivity(), R.layout.menu_list_fragment,
                    mRestaurant.getMenu().getDishes());
            ((ListView) view.findViewById(R.id.menu_list)).setAdapter(mMenuAdapter);
        }

        return view;
    }

    private Restaurant grabRestaurantFromArgs() {
        return getArguments().getParcelable("restaurant");
    }

    private class MenuAdapter extends ArrayAdapter<Dish> {
        LayoutInflater mVi;
        int mViewResourceId;

        public MenuAdapter(Context context, int viewResourceId, List<Dish> items) {
            super(context, viewResourceId, items);
            mVi = LayoutInflater.from(context);
            mViewResourceId = viewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = mVi.inflate(mViewResourceId, null);
            }
            Dish dish = getItem(position);

            if (dish != null) {
                TextView nameView = (TextView) v.findViewById(R.id.name);
                if (nameView != null) {
                    nameView.setText(dish.getName());
                }
            }
            return v;
        }
    }

    @Override
    public void onActionAdd() {
        ((MainActivity) getActivity()).displayAddDish(mRestaurant);
    }

}
