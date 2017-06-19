package com.example.mohamed.calma.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mohamed.calma.Fragments.OneArticleFragment;

import java.util.List;

/**
 * Created by mohamed on 08/06/2017.
 */

public class ArticlePagerAdapter extends FragmentPagerAdapter {
    private List<String> articles;
    public ArticlePagerAdapter(FragmentManager fm, List<String> data) {
        super(fm);
        articles=data;
    }

    @Override
    public Fragment getItem(int position) {
        return OneArticleFragment.newFragment(articles.get(position));
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Article :"+(position+1);
    }
}
