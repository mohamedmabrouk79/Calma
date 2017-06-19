package com.example.mohamed.calma.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mohamed.calma.Fragments.FindTherapistFragment;
import com.example.mohamed.calma.Fragments.QuickTestFragment;
import com.example.mohamed.calma.Fragments.UserProfileFragment;
import com.example.mohamed.calma.model.User;


/**
 * Created by ali on 4/20/17.
 */

public class ProfileAdapter extends FragmentPagerAdapter {
    private User mUser;
    public ProfileAdapter(FragmentManager fm,User user) {
        super(fm);
        mUser=user;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                // profile Fragment
                    return UserProfileFragment.newInstance(mUser);

            case 1:
                //therapist fragment
                return FindTherapistFragment.newFragment();
            case 2:
                //quick test
                return QuickTestFragment.newFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Profile";
            case 1:
                return "Find Therapist";
            case 2:
                return "Quick Test";
        }
        return null;
    }
}
