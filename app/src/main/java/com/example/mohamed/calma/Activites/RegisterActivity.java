package com.example.mohamed.calma.Activites;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.calma.Fragments.RegisterFragment;
import com.example.mohamed.calma.SingleFragamentActivity;

/**
 * Created by ali on 2/21/17.
 */

public class RegisterActivity extends SingleFragamentActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,RegisterActivity.class);
        return intent;
    }
    @Override
    public Fragment CreateFragment() {
        return RegisterFragment.newInstance();
    }
}
