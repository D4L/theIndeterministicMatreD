package com.maxclique.theindeterministicmaitred.app;

import android.content.Context;

import java.io.File;

/**
 * Created by Austin on 7/2/2014.
 */
public class InternalFileHelper {
    private final String restaurantList = "restaurant_list";
    private final String restaurantPrefix = "restaurant_";
    private Context mContext;

    public InternalFileHelper(Context context) {
        mContext = context;
    }

    public File getRestaurantFile() {
        return new File(mContext.getFilesDir(), restaurantList);
    }

    public File getRestaurantFile(Restaurant restaurant) {
        return new File(mContext.getFilesDir(), restaurantPrefix + restaurant.getId());
    }

}
