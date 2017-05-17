package com.example.mohamed.calma.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.mohamed.calma.Fragments.TestFragmentSelected;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 01/05/2017.
 */

public class TestAdapter extends FragmentPagerAdapter {
    public static int mpostion=0;

    private List<String> mQuations=new ArrayList<>();
    private List<String[]> mAnswers=new ArrayList<>();
    public TestAdapter(FragmentManager fm ,List<String> quaton,List<String[]> answer) {
        super(fm);mQuations=quaton;
        mAnswers=answer;
    }

    @Override
    public Fragment getItem(int position) {
        Log.v("mohahhh",mQuations.size()+"");
        mpostion=position;
        return TestFragmentSelected.newTestFragmentSelected(mQuations,mAnswers,position);
    }

    @Override
    public int getCount() {
        return mQuations.size();
    }
}
