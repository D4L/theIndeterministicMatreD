package com.maxclique.theindeterministicmaitred.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Austin on 7/4/2014.
 */
public class Dish implements Parcelable {

    public Dish(Parcel source) {

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
        public Dish createFromParcel(Parcel source) {
            return new Dish(source);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };
}
