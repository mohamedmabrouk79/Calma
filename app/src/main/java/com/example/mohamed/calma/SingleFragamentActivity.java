package com.example.mohamed.calma;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ali on 2/21/17.
 */

public abstract class SingleFragamentActivity extends AppCompatActivity {
    public abstract Fragment CreateFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_fragment);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.Fragment_Container);

        if (fragment==null){
            fragment=CreateFragment();
            fragmentManager.beginTransaction().replace(R.id.Fragment_Container,fragment).commit();
        }
    }
}
