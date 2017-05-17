package com.example.mohamed.calma.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.calma.Activites.ProfileActivity;
import com.example.mohamed.calma.Activites.RegisterActivity;
import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.CheckConnection;
import com.example.mohamed.calma.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by ali on 2/21/17.
 */

public class LoginFragment extends Fragment {
    private EditText mPhone;
    private EditText mPasswoord;
    private Button mLoginButton;
    private Button mForgetPasswordButton;
    private TextView mSignUp;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private User mUser;
    private ProgressDialog mProgressDialog;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view=inflater.inflate(R.layout.login_fragment,container,false);
        mForgetPasswordButton= (Button) view.findViewById(R.id.forget_password);
        mLoginButton= (Button) view.findViewById(R.id.login);
        mPasswoord= (EditText) view.findViewById(R.id.password_edit_text);
        mPhone= (EditText) view.findViewById(R.id.phone_number);
        mSignUp= (TextView) view.findViewById(R.id.sign_up);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        mProgressDialog=new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Log in ....");
        if (CheckConnection.isNetworkAvailableAndConnected(getActivity())&& CheckConnection.isNetworkAvailableAndConnected(getActivity())){
            Login();
        }else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        SignUp();
        return view;
    }

    private void SignUp(){
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(RegisterActivity.newIntent(getActivity()));
            }
        });
    }

    private void Login(){
        mLoginButton.setOnClickListener(v -> {
            if (CheckConnection.isNetworkAvailableAndConnected(getActivity())&& CheckConnection.isNetworkAvailableAndConnected(getActivity())){


            String email=mPhone.getText().toString().trim();
            String password=mPasswoord.getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                Toast.makeText(getActivity(), "Enter your Phone", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(password)){
                Toast.makeText(getActivity(), "Enter your Password", Toast.LENGTH_SHORT).show();
            }else {
                mProgressDialog.show();
                mFirebaseAuth.signInWithEmailAndPassword(email+"@yahoo.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                    DatabaseReference mReference=mDatabaseReference.child(user.getEmail().replace("@yahoo.com",""));
                                    mReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Map<String,String> map= (Map<String, String>) dataSnapshot.getValue();
                                            mUser=new User(
                                                    map.get("name"),map.get("phone"),map.get("image"),"user",map.get("password"),map.get("country")
                                                    ,map.get("date")
                                            );
                                            mProgressDialog.dismiss();
                                            startActivity(ProfileActivity.newIntent(getActivity(),mUser));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        }else{
                      mProgressDialog.dismiss();
                            Toast.makeText(getActivity(), "Log in Is Fail ", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
            }else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser!=null){
            DatabaseReference mReference=mDatabaseReference.child(currentUser.getEmail().replace("@yahoo.com","").trim());
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String,String> map= (Map<String, String>) dataSnapshot.getValue();
                    mUser=new User(
                            map.get("name"),map.get("phone"),map.get("image"),"user",map.get("password"),map.get("country")
                            ,map.get("date")
                    );
                    startActivity(ProfileActivity.newIntent(getActivity(),mUser));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
