package com.example.mohamed.calma.Activites;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.calma.Fragments.LoginFragment;
import com.example.mohamed.calma.SingleFragamentActivity;


/**
 * Created by ali on 2/21/17.
 */

public class LoginActivity extends SingleFragamentActivity {

    public static Intent newIntent(Context context,boolean isUpadted,String email){
        Intent intent=new Intent(context,LoginActivity.class);
        intent.putExtra("updated",isUpadted);
        intent.putExtra("email",email);
        return intent;
    }
    @Override
    public Fragment CreateFragment() {
        boolean isUpdted=getIntent().getBooleanExtra("updated",false);
        return LoginFragment.newInstance(isUpdted,getIntent().getStringExtra("email"));
    }


}
