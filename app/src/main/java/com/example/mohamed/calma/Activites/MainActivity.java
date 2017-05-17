package com.example.mohamed.calma.Activites;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.calma.Fragments.MainFragment;
import com.example.mohamed.calma.SingleFragamentActivity;


public class MainActivity extends SingleFragamentActivity {
   public static Intent newIntent(Context context){
       Intent intent=new Intent(context,MainActivity.class);
       return intent;
   }
    @Override
    public Fragment CreateFragment() {
        return MainFragment.newInstance();
    }
}
