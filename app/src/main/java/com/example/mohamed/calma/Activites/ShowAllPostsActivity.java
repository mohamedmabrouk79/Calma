package com.example.mohamed.calma.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.calma.Adapters.PostsAdapter;
import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.User;

/**
 * Created by mohamed on 14/06/2017.
 */

public class ShowAllPostsActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private static User mUser;

    public static Intent newIntent(Context context,User user){
        Intent intent=new Intent(context, ShowAllPostsActivity.class);
        mUser=user;
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_posts);
        mViewPager= (ViewPager) findViewById(R.id.posts_view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.post_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        FragmentManager fragmentManager=getSupportFragmentManager();
        mViewPager.setAdapter(new PostsAdapter(fragmentManager,mUser));
    }
}
