package com.maxclique.theindeterministicmaitred.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Austin on 7/11/2014.
 */
public class ChooseDishFragment extends Fragment {

    private final int TOTAL_REROLLS = 2;

    private Restaurant mRestaurant;
    private String mRerollText;
    private Button mRerollButton;
    private int mRerollsLeft;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.choose_dish_fragment, viewGroup, false);

        view.findViewById(R.id.confirm_dish_button).setOnClickListener(
                new ConfirmOnClickListener());

        mRerollButton = (Button) view.findViewById(R.id.reroll_button);
        mRerollButton.setOnClickListener(
                new RerollOnClickListener());
        mRerollText = getResources().getString(R.string.reroll_button_text);
        mRerollsLeft = TOTAL_REROLLS;

        setRerollButton();

        mRestaurant = grabRestaurantFromArgs();

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

    private class ConfirmOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
        }
    }

    private class RerollOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            --mRerollsLeft;
            setRerollButton();
        }
    }
}
