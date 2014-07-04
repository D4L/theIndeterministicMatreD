package com.maxclique.theindeterministicmaitred.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Austin on 6/30/2014.
 */
public class ShowRestaurantsFragment extends Fragment
        implements FragmentHelper.RefreshableFragment {

    private OnAddRestaurantListener mOnAddRestaurantListener;

    private RestaurantAdapter mRestaurantAdapter;
    private EditText mWidgetTextBox;
    private ImageButton mActionButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnAddRestaurantListener = (OnAddRestaurantListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement OnAddRestaurantListener");
        }
    }

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

        mWidgetTextBox = (EditText) view.findViewById(R.id.restaurant_widget_text_box);
        mActionButton = (ImageButton) view.findViewById(R.id.restaurant_widget_action_button);
        mActionButton.setOnClickListener(new ActionButtonOnClickListener());

        return view;
    }

    @Override
    public void refresh() {
        refreshListView();
    }

    private void refreshListView() {
        mRestaurantAdapter.notifyDataSetChanged();
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
            ((MainActivity)mActivity).displayShowMenu();
        }
    }

    private class ActionButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String restaurantName = mWidgetTextBox.getText().toString();
            if (restaurantName.equals("")) {
                Toast toast = Toast.makeText(getActivity(),
                        R.string.enter_restaurant_name, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            Restaurant restaurant = new Restaurant(restaurantName);
            mOnAddRestaurantListener.onAddRestaurant(restaurant);
            mWidgetTextBox.setText("");
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mWidgetTextBox.getWindowToken(), 0);
        }
    }

    public interface OnAddRestaurantListener {
        public void onAddRestaurant(Restaurant restaurant);
    }
}
