package com.example.mohamed.calma.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.calma.Activites.TestActivity;
import com.example.mohamed.calma.R;

import java.util.List;
import java.util.Map;

/**
 * Created by mohamed on 01/05/2017.
 */

public class TestFragmentSelected extends Fragment {
    private TextView quation;
    private RadioButton answer1,answer2,answer3,answer4;
    private static List<String> mQuations;
    private static List<String[]> mAnswers;
    private int currentDegree=0;
    private static int mCount=0;
    public static TestFragmentSelected newTestFragmentSelected(List<String> quations,List<String[]> answers ,int count){
        mQuations=quations;
        mCount=count;
        mAnswers=answers;
        return new TestFragmentSelected();
    }

    @Override
    public void onPause() {
        super.onPause();
        TestActivity.degree+=currentDegree;
       // Toast.makeText(getActivity(), TestActivity.degree+"", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.quick_test_view,container,false);
        quation = (TextView) view.findViewById(R.id.quation);
        answer1= (RadioButton) view.findViewById(R.id.a1);
        answer2= (RadioButton) view.findViewById(R.id.a2);
        answer3= (RadioButton) view.findViewById(R.id.a3);
        answer4= (RadioButton) view.findViewById(R.id.a4);
        addQuation_answers();
        test();
        return view;
    }

    private void test(){
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDegree=1;
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDegree=3;

            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDegree=6;

            }
        });
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDegree=9;

            }
        });

    }



    private void addQuation_answers(){
        quation.setText(mQuations.get(mCount));
        answer1.setText(mAnswers.get(mCount)[0]);
        answer2.setText(mAnswers.get(mCount)[1]);
        answer3.setText(mAnswers.get(mCount)[2]);
        answer4.setText(mAnswers.get(mCount)[3]);
    }

}
