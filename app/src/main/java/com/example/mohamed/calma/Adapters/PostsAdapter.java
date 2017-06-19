package com.example.mohamed.calma.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mohamed.calma.Fragments.ShowDoctorPosts;
import com.example.mohamed.calma.Fragments.ShowUserPosts;
import com.example.mohamed.calma.model.User;

/**
 * Created by mohamed on 14/06/2017.
 */

public class PostsAdapter extends FragmentPagerAdapter {
    private User mUser;
    public PostsAdapter(FragmentManager fm,User user) {
        super(fm);
        mUser=user;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                 return ShowDoctorPosts.newPosts(mUser);
            case 1:
                return ShowUserPosts.newPosts(mUser);
            default:
                return null;
        }

    }



    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Doctors posts";
            case 1:
                return "Other Posts";
        }
        return null;
    }
}
