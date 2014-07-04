package com.maxclique.theindeterministicmaitred.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin on 7/4/2014.
 */
public class RestaurantMenu implements Parcelable {
    private List<Dish> mDishList;

    public RestaurantMenu() {
        mDishList = new ArrayList<Dish>();
    }

    public RestaurantMenu(Parcel source) {
        source.readTypedList(mDishList, Dish.CREATOR);
    }

    public void addDish(Dish dish) {
        mDishList.add(dish);
    }

    public List<Dish> getDishes() {
        return mDishList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mDishList);
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public RestaurantMenu createFromParcel(Parcel source) {
            return new RestaurantMenu(source);
        }

        @Override
        public RestaurantMenu[] newArray(int size) {
            return new RestaurantMenu[size];
        }
    };
}
