package com.maxclique.theindeterministicmaitred.app;

import android.os.Parcel;
import android.os.Parcelable;

import org.bson.types.ObjectId;

/**
 * Created by Austin on 7/1/2014.
 */
public class Restaurant implements LazyRestaurantsHelper.LazyResource, Parcelable {

    private String mId;
    private String mName;
    private RestaurantMenu mMenu;
    private boolean mLoaded;
    private boolean mDirty;

    public Restaurant(String name) {
        mName = name;
        mLoaded = false;
        mDirty = false;
        mId = new ObjectId().toHexString();
    }

    public Restaurant(String name, String id) {
        mName = name;
        mLoaded = false;
        mDirty = false;
        mId = id;
    }

    public Restaurant(Parcel source) {
        mId = source.readString();
        mName = source.readString();
        boolean[] bools = source.createBooleanArray();
        source.readBooleanArray(bools);
        mLoaded = bools[0];
        mDirty = bools[1];
        mMenu = source.readParcelable(RestaurantMenu.class.getClassLoader());
    }

    public String getId() {
        return mId;
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

    public void addDish(Dish dish) {
        mMenu.addDish(dish);
        mDirty = true;
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
    public boolean isDirty() {
        return mDirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        mDirty = dirty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        boolean[] bools = {mLoaded, mDirty};
        dest.writeBooleanArray(bools);
        dest.writeParcelable(mMenu, 0);
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
