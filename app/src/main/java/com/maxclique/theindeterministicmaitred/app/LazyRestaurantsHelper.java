package com.maxclique.theindeterministicmaitred.app;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin on 7/2/2014.
 */
public class LazyRestaurantsHelper {

    private Context mContext;
    private InternalFileHelper mFileHelper;
    private List<Restaurant> mRestaurants;

    public LazyRestaurantsHelper(Context context) {
        mFileHelper = new InternalFileHelper(context);
        mContext = context;
        mRestaurants = null;
    }

    public List<Restaurant> getRestaurantList() {
        if (mRestaurants == null) {
            mRestaurants = new ArrayList<Restaurant>();
            try {
                File restaurantsFile = mFileHelper.getRestaurantFile();
                if (!restaurantsFile.exists()) {
                    return mRestaurants;
                }
                BufferedReader buffReader = new BufferedReader(new FileReader(restaurantsFile));
                String line;
                while ((line = buffReader.readLine()) != null) {
                    mRestaurants.add(new Restaurant(line));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mRestaurants;
    }

    public void addRestaurant(Restaurant restaurant) {
        mRestaurants.add(restaurant);
    }

    public void loadRestaurant(Restaurant restaurant) {
        if (restaurant.isLoaded()) {
            return;
        }
        RestaurantMenu menu = new RestaurantMenu();
        restaurant.setMenu(menu);
        restaurant.setLoaded(true);
    }

    public void save() {
        try {
            File restaurantsFile = mFileHelper.getRestaurantFile();
            FileOutputStream outStream = mContext.openFileOutput(
                    restaurantsFile.getName(), 0);
            for (int i = 0; i < mRestaurants.size(); ++i) {
                outStream.write(mRestaurants.get(i).getName().getBytes());
                outStream.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface LazyResource {
        public boolean isLoaded();
        public void setLoaded(boolean loaded);
    }
}
