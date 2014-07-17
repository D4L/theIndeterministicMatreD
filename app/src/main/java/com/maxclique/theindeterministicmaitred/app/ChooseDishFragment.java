package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Austin on 7/11/2014.
 */
public class ChooseDishFragment extends Fragment {

    private final int TOTAL_REROLLS = 2;

    private Restaurant mRestaurant;
    private String mRerollText;
    private Button mRerollButton;
    private TextView mChosenDishTextView;
    private int mRerollsLeft;
    private ArrayList<Dish> mPrevRandom;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.choose_dish_fragment, viewGroup, false);

        mChosenDishTextView = (TextView) view.findViewById(R.id.chosen_dish);
        view.findViewById(R.id.confirm_dish_button).setOnClickListener(
                new ConfirmOnClickListener());

        mRestaurant = grabRestaurantFromArgs();

        mRerollButton = (Button) view.findViewById(R.id.reroll_button);
        mRerollButton.setOnClickListener(
                new RerollOnClickListener());
        mRerollText = getResources().getString(R.string.reroll_button_text);
        mRerollsLeft = Math.min(TOTAL_REROLLS, mRestaurant.getMenu().getDishes().size() - 1);

        mPrevRandom = new ArrayList<Dish>();
        setRerollButton();
        setNewRandomDish();

        return view;
    }

    private Restaurant grabRestaurantFromArgs() {
        return getArguments().getParcelable("restaurant");
    }

    private void setRerollButton() {
        mRerollButton.setText(String.format(mRerollText, mRerollsLeft));
        if (mRerollsLeft <= 0) {
            mRerollButton.setEnabled(false);
        }
    }

    private void setNewRandomDish() {
        Dish newRandomDish;
        Random rand = new Random();
        int newRandomDishIndex;
        List<Dish> restaurantDishes = mRestaurant.getMenu().getDishes();
        do {
            newRandomDishIndex = rand.nextInt(restaurantDishes.size());
            newRandomDish = restaurantDishes.get(newRandomDishIndex);
        } while (mPrevRandom.contains(newRandomDish));
        mChosenDishTextView.setText(newRandomDish.getName());
        mPrevRandom.add(newRandomDish);
    }

    private class ConfirmOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Bundle args = new Bundle();
            args.putParcelable("restaurant", mRestaurant);
            args.putParcelableArrayList("prevDishes", mPrevRandom);
            ((MainActivity)getActivity()).goTo(FragmentHelper.FragmentId.RATE_MEAL, args);
        }
    }

    private class RerollOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setNewRandomDish();
            --mRerollsLeft;
            setRerollButton();
        }
    }
}
