package com.example.mohamed.calma.Activites;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.mohamed.calma.Fragments.TestFragmentSelected;
import com.example.mohamed.calma.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    public static final String TYPE="TYPE";
    public static int degree=0;
    private int count=0;
    private Button nextButton;
    private String type;
    private Map<String,String[]> mapWork=new HashMap<>();

    private List<Integer> TensionAId=Arrays.asList(R.array.Tension_answer1
            , R.array.Tension_answer2,R.array.Tension_answer3,
            R.array.Tension_answer4,R.array.Tension_answer5
            ,R.array.Tension_answer6);
    private List<Integer> answerId= Arrays.asList(R.array.depression_answer1
            , R.array.depression_answer2,R.array.depression_answer3,
            R.array.depression_answer4,R.array.depression_answer5
            ,R.array.depression_answer6,R.array.depression_answer7,R.array.depression_answer8,
            R.array.depression_answer9,R.array.depression_answer10);

    public static Intent newIntent(String type, Context context){
        Intent intent=new Intent(context,TestActivity.class);
        intent.putExtra(TYPE,type);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        nextButton= (Button) findViewById(R.id.next);
        type=getIntent().getStringExtra(TYPE);
        /** for put quation and answers into Map **/


        switch (type){
            case "Depression":
                for (int i = 0; i <10 ; i++) {
                    mapWork.put(getResources().getStringArray(R.array.depression_quation)[i],getResources().getStringArray(answerId.get(i)));
                }
                TestActivity.degree=0;
                 addFragment(TestFragmentSelected.newTestFragmentSelected(getQuaions(mapWork),getAnswers(mapWork),count));
                break;
            case "Tension":
                for (int i = 0; i <6 ; i++) {
                    mapWork.put(getResources().getStringArray(R.array.Tension_quatios)[i],getResources().getStringArray(TensionAId.get(i)));
                }
                TestActivity.degree=0;
                addFragment(TestFragmentSelected.newTestFragmentSelected(getQuaions(mapWork),getAnswers(mapWork),count));

                break;
            case "Laziness":

                break;
        }
        Next();
       // Prev();

    }

    private void Next(){
        nextButton.setOnClickListener(view->{
            if (count==mapWork.size()-1){
                nextButton.setText("Get Result");
               // prevButton.setEnabled(true);
                switch (type){
                    case "Depression":
                        getResult("الاكتئاب");
                        break;
                    case "Tension":
                        getResult("التوتر");
                        break;
                    case "Laziness":
                        getResult("الكسل");
                        break;
                }

            }else{
                count++;
                addFragment(TestFragmentSelected.newTestFragmentSelected(getQuaions(mapWork),getAnswers(mapWork),count));
            }
        });
    }

    private void getResult(String type){
      if (degree>=1 && degree<25){
          int unicode = 0x1F600;
        showDailog("  نسبه"+type+"لديك ضعيفه "+new String(Character.toChars(unicode)),type);
      }else if(degree>=25&& degree<50){
          int unicode = 0x1F612;
          showDailog(" نسبه "+type+"لديك متوسطه "+new String(Character.toChars(unicode)),type);
      }else if (degree>=50 && degree<75){
          int unicode = 0x1F625;
          showDailog(" نسبه "+type+" لديك كبيره "+new String(Character.toChars(unicode)),type);
      }else if (degree>=75 && degree<100){
          int unicode = 0x1F629;
          showDailog(" نسبه "+type+" لديك كبيره جدا "+new String(Character.toChars(unicode)),type);
      }
    }
    AlertDialog.Builder builder;
    private void showDailog(String message,String type){
     builder=new AlertDialog.Builder(this);
        View view=LayoutInflater.from(this).inflate(R.layout.result_view,null);
        TextView textView= (TextView) view.findViewById(R.id.result);
         textView.setText(message);
        Button findoctor= (Button) view.findViewById(R.id.find_doctor);
        Button cancel= (Button) view.findViewById(R.id.cancel_button);
        builder.setView(view).setTitle(type+" Result ").setOnCancelListener(c->{
            finish();
        }).show();

        findoctor.setOnClickListener(v->{
          startActivity(ResultTestActivity.newIntent(this,type));
          finish();
        });
        cancel.setOnClickListener(c ->{
         finish();
        });

    }



    private List<String> getQuaions(Map<String,String[]> map){
        List<String>  keys=new ArrayList<>();
        map.keySet().stream().forEach(e -> keys.add(e));


        return keys;
    }

    private List<String[]> getAnswers(Map<String,String[]> map){
        List<String[]> value=new ArrayList<>();
        map.values().stream().forEach(e ->value.add(e));
        return value;
    }

    private void addFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.test_frame,fragment).commit();

    }
}
