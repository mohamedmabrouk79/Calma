package com.example.mohamed.calma.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.mohamed.calma.Fragments.ArticleFragment;
import com.example.mohamed.calma.Fragments.DoctorsFragments;
import com.example.mohamed.calma.Fragments.FragmentArticles;
import com.example.mohamed.calma.Fragments.VideoResultFragment;
import com.example.mohamed.calma.R;

/**
 * Created by mohamed on 09/05/2017.
 */

public class ResultTestActivity extends AppCompatActivity {

    public static Intent newIntent(Context context,String type){
        Intent intent=new Intent(context,ResultTestActivity.class);
        intent.putExtra(ArticleFragment.TYPE,type);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_test_activity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        addFragment(FragmentArticles.newFragment(getIntent().getStringExtra(ArticleFragment.TYPE)));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.article_menu:
                    addFragment(FragmentArticles.newFragment(getIntent().getStringExtra(ArticleFragment.TYPE)));
                    return true;
                case R.id.video_menu:
                    addFragment(VideoResultFragment.newFragment(getIntent().getStringExtra(ArticleFragment.TYPE)));
                    return true;
                case R.id.doctors_name:
                    addFragment(DoctorsFragments.newFragment());
                    return true;
            }
            return false;
        }

    };

    private void addFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.result_container,fragment).commit();
    }
}
