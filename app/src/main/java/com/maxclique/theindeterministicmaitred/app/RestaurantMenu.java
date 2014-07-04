package com.maxclique.theindeterministicmaitred.app;

import java.util.List;

/**
 * Created by Austin on 7/4/2014.
 */
public class RestaurantMenu {
    private List<Dish> mDishList;

    public void addDish(Dish dish) {
        mDishList.add(dish);
    }

    public List<Dish> getDishes() {
        return mDishList;
    }
}
