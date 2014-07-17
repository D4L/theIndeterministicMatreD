package com.maxclique.theindeterministicmaitred.app;

import android.os.Bundle;

/**
 * Created by Austin on 7/17/2014.
 */
public class SmartBundle {

    private Bundle mBundle;

    public SmartBundle(FragmentHelper.FragmentId fragmentId, Bundle bundle) {
        mBundle = bundle;
        if (bundle != null && !checkBundle(fragmentId)) {
            throw new BadBundleException("Bad Bundle Exception for " + fragmentId);
        }
    }

    public Bundle getBundle() {
        return mBundle;
    }

    private boolean checkBundle(FragmentHelper.FragmentId fragmentId) {
        switch (fragmentId) {
            case SHOW_RESTAURANTS:
            case ADD_RESTAURANT:
                return true;
            case RESTAURANT_NAV:
            case CHOOSE_DISH:
            case SHOW_MENU:
            case ADD_DISH:
                return mBundle.containsKey("restaurant");
            case RATE_MEAL:
                return mBundle.containsKey("restaurant") && mBundle.containsKey("prevDishes");
            default:
                return false;
        }
    }

    public class BadBundleException extends RuntimeException {
        public BadBundleException(String message) {
            super(message);
        }
    }
}
