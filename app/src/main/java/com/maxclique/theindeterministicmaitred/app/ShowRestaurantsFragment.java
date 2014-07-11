package com.maxclique.theindeterministicmaitred.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Austin on 6/30/2014.
 */
public class ShowRestaurantsFragment extends Fragment implements FragmentHelper.AddableFragment {

    private RestaurantAdapter mRestaurantAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.show_restaurants_fragments, viewGroup, false);

        List<Restaurant> restaurants = ((MainActivity) getActivity()).getRestaurants();
        mRestaurantAdapter = new RestaurantAdapter(getActivity(), R.layout.restaurant_list_fragment,
                restaurants);
        ListView restaurantList = ((ListView) view.findViewById(R.id.restaurant_list));
        restaurantList.setAdapter(mRestaurantAdapter);
        restaurantList.setOnItemClickListener(new RestaurantOnItemClickListener(getActivity()));

        return view;
    }

    private class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        LayoutInflater mVi;
        int mViewResourceId;

        public RestaurantAdapter(Context context, int viewResourceId, List<Restaurant> items) {
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
            Restaurant p = getItem(position);

            if (p != null) {
                TextView nameView = (TextView) v.findViewById(R.id.name);
                if (nameView != null) {
                    nameView.setText(p.getName().toString());
                }
            }
            return v;
        }
    }

    private class RestaurantOnItemClickListener implements AdapterView.OnItemClickListener {
        Activity mActivity;

        public RestaurantOnItemClickListener(Activity activity) {
            mActivity = activity;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            RestaurantAdapter restaurantAdapter = (RestaurantAdapter) adapterView.getAdapter();
            Restaurant restaurant = restaurantAdapter.getItem(position);
            ((MainActivity) mActivity).displayShowMenu(restaurant);
        }
    }

    @Override
    public void onActionAdd() {
        ((MainActivity) getActivity()).displayAddRestaurant();
    }
}
