package com.example.mohamed.calma.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.mohamed.calma.Activites.TestActivity;
import com.example.mohamed.calma.R;

/**
 * Created by ali on 4/20/17.
 */

public class QuickTestFragment extends Fragment {
    private static  final  String TEST="test";
    private Button depressionButton;
    private Button tensionButton;
    private Button lazinessButton;
    public static QuickTestFragment newFragment(){
        return new QuickTestFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.test_select,container,false);
        depressionButton= (Button) view.findViewById(R.id.depression);
        tensionButton= (Button) view.findViewById(R.id.tension);
        lazinessButton= (Button) view.findViewById(R.id.laziness);
        Depression();
        Laziness();
        Tension();
        return view;
    }

    private void Depression(){
        depressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager=getFragmentManager();
//                TestFragment fragment=TestFragment.newFragment("Depression");
//                fragment.show(fragmentManager,TEST);

                startActivity(TestActivity.newIntent("Depression",getActivity()));
            }
        });

    }

    private void Tension(){
        tensionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TestActivity.newIntent("Tension",getActivity()));
            }
        });
    }

    private void Laziness(){
        lazinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
