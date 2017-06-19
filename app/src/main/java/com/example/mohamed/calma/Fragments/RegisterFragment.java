package com.example.mohamed.calma.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.calma.Activites.LoginActivity;
import com.example.mohamed.calma.Activites.ProfileActivity;
import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.CheckConnection;
import com.example.mohamed.calma.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by ali on 2/21/17.
 */

public class RegisterFragment extends Fragment {
    public static final String DATE_TAG="datetag";
    public static final String VERF_TAG="verficationdialog";
    private static final int REQUEST_DATE = 0;
    private EditText mNameText;
    private EditText mPasswordText;
    private EditText mRepeatText;
    private EditText mMobileNumnText;
    private Button mSignUpButton;
    private RadioGroup mGenderGroup;
    private RadioButton mMaleButton;
    private RadioButton mFemale;
    private Spinner mSpinner;
    private LinearLayout mBirthDate;
    private TextView mLoginView;
    private List<String> mList;
    private String mDate;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog mProgressDialog;
    /**************** Instance from RegisterFragment ***************/
    public static RegisterFragment newInstance(){
        return new RegisterFragment();
    }
    /**************** create  view *******************/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.register_fragment,container,false);
        mNameText= (EditText) view.findViewById(R.id.name_edit_text);
        mPasswordText= (EditText) view.findViewById(R.id.password_register_text);
        mRepeatText= (EditText) view.findViewById(R.id.repeat_password_register_text);
        mMobileNumnText= (EditText) view.findViewById(R.id.phone_number_register);
        mSignUpButton= (Button) view.findViewById(R.id.sign_up_register);
        mMaleButton= (RadioButton) view.findViewById(R.id.male);
        mFemale= (RadioButton) view.findViewById(R.id.female);
        mSpinner= (Spinner) view.findViewById(R.id.country_spinner);
        mBirthDate= (LinearLayout) view.findViewById(R.id.birth_day);
        mLoginView= (TextView) view.findViewById(R.id.login_text_view_register);
        mList=new ArrayList<>();
        mProgressDialog=new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Registering .....");
        String[] counries=getResources().getStringArray(R.array.counteries);
        for (int i=0 ; i<counries.length;i++){
            mList.add(counries[i]);
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mList);

        mSpinner.setAdapter(arrayAdapter);
        /****************** actions ********************/
        logInAction();
        if (CheckConnection.isNetworkAvailableAndConnected(getActivity())&& CheckConnection.isNetworkAvailableAndConnected(getActivity())){
            singUPaction();
        }else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        BirthDateAction();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        return view;
    }

    /************ Login text view action **************/
    private void logInAction(){
        mLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.newIntent(getActivity(),false,null));
                getActivity().finish();
            }
        });
    }

    /**************** sign up action ***********************/
    String gender="male";
    private void singUPaction(){
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String name=mNameText.getText().toString().trim();
                final String phone=mMobileNumnText.getText().toString().trim();
                final String country=mSpinner.getSelectedItem().toString();
                final String password=mPasswordText.getText().toString().trim();
                String confirmpassword=mRepeatText.getText().toString();
                mFemale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender="female";
                    }
                });
                mMaleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender="male";
                    }
                });
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(phone)){
                    Toast.makeText(getActivity(), "Enter Phone Number", Toast.LENGTH_SHORT).show();

                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();

                }else if (!password.equals(confirmpassword)){
                    Toast.makeText(getActivity(), "Password not Matches", Toast.LENGTH_SHORT).show();

                }else {
                    mProgressDialog.show();
                  mFirebaseAuth.createUserWithEmailAndPassword(phone+"@yahoo.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()){
                              DatabaseReference mReference=mDatabaseReference.child(phone);
                              User user=new User(name,phone,null,"user",password,country,mDate);
                              mReference.child("name").setValue(name);
                              mReference.child("phone").setValue(phone);
                              mReference.child("password").setValue(password);
                              mReference.child("country").setValue(country);
                              mReference.child("gender").setValue(gender);
                              mReference.child("image").setValue("null");
                              mReference.child("date").setValue(mDate);
                              mReference.child("type").setValue("user");
                              mProgressDialog.dismiss();
                              startActivity(ProfileActivity.newIntent(getActivity(),user));
                              getActivity().finish();
                          }else{
                              mProgressDialog.dismiss();
                              Toast.makeText(getActivity(), "Register Fail", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
                }

            }
        });
    }
    /***************** for get verfication number ****************************/
    private int getRandom(){
        int max=999999;
        int min=100000;
        Random random=new Random();
        int num=random.nextInt((max-min)+1)+min;
        return num;
    }
    /********************** get birth date ******************************/
    private void BirthDateAction(){
        mBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                BirthDateFragment fragment= BirthDateFragment.newInstance(new Date());
                fragment.setTargetFragment(RegisterFragment.this, REQUEST_DATE);
                fragment.show(fragmentManager,DATE_TAG);
            }
        });
    }
    /*************************** get date from dialog paker ***************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(BirthDateFragment.EXTRA_DATE);
            android.text.format.DateFormat df = new android.text.format.DateFormat();
             mDate= (String) df.format("yyyy-MM-dd",date);
        }
    }

}
