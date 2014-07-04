package com.maxclique.theindeterministicmaitred.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;

/**
 * Created by Austin on 7/1/2014.
 */
public class Restaurant implements LazyRestaurantsHelper.LazyResource, Parcelable {

    private String mName;
    private RestaurantMenu mMenu;
    private boolean mLoaded;

    public Restaurant(String name) {
        mName = name;
        mLoaded = false;
    }

    public Restaurant(Parcel source) {

    }

    public String getName() {
        return mName;
    }

    public RestaurantMenu getMenu() {
        return mMenu;
    }

    public void setMenu(RestaurantMenu menu) {
        mMenu = menu;
    }

    @Override
    public boolean isLoaded() {
        return mLoaded;
    }

    @Override
    public void setLoaded(boolean loaded) {
        mLoaded = loaded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
