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
                    String[] lineArray = line.split(",");
                    Restaurant restaurant = new Restaurant(lineArray[0], lineArray[1]);
                    mRestaurants.add(restaurant);
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
        try {
            // Add dishes if there is a restaurant file
            File restaurantFile = mFileHelper.getRestaurantFile(restaurant);
            if (restaurantFile.exists()) {
                BufferedReader buffReader = new BufferedReader(
                        new FileReader(restaurantFile));
                String line;
                while ((line = buffReader.readLine()) != null) {
                    menu.addDish(new Dish(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        restaurant.setMenu(menu);
        restaurant.setLoaded(true);
    }

    public void save() {
        try {
            File restaurantsFile = mFileHelper.getRestaurantFile();
            FileOutputStream outStream = mContext.openFileOutput(
                    restaurantsFile.getName(), Context.MODE_PRIVATE);
            for (Restaurant restaurant : mRestaurants) {
                writeRestaurantLine(restaurant, outStream);
                if (restaurant.isDirty()) {
                    File restaurantFile = mFileHelper.getRestaurantFile(restaurant);
                    FileOutputStream restaurantOutStream = mContext.openFileOutput(
                            restaurantFile.getName(), Context.MODE_PRIVATE);
                    writeRestaurantFile(restaurant, restaurantOutStream);
                    restaurant.setDirty(false);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRestaurantLine(Restaurant restaurant,
                                     FileOutputStream outStream) throws IOException {
        outStream.write(restaurant.getName().getBytes());
        outStream.write(',');
        outStream.write(restaurant.getId().getBytes());
        outStream.write('\n');
    }

    private void writeRestaurantFile(Restaurant restaurant,
                                     FileOutputStream outStream) throws IOException {
        for (Dish dish : restaurant.getMenu().getDishes()) {
            outStream.write(dish.getName().getBytes());
            outStream.write('\n');
        }
    }

    public interface LazyResource {
        public boolean isLoaded();

        public boolean isDirty();

        public void setLoaded(boolean loaded);

        public void setDirty(boolean dirty);
    }
}
