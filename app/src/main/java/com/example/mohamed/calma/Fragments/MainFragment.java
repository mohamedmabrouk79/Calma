package com.example.mohamed.calma.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohamed.calma.Activites.LoginActivity;
import com.example.mohamed.calma.Activites.ProfileActivity;
import com.example.mohamed.calma.Activites.RegisterActivity;
import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * Created by ali on 2/21/17.
 */

public class MainFragment extends Fragment {
    private Button mLoginButton;
    private Button mCreateAccountButton;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private User mUser;

    public static MainFragment newInstance(){
        return new MainFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.calma_main,container,false);
         mLoginButton= (Button) view.findViewById(R.id.login_home_page);
        mCreateAccountButton= (Button) view.findViewById(R.id.create_account_page);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        Login();
        CraeteAcount();
        return view;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public boolean isNetworkAvailableAndConnected(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

        boolean isAvailable=cm.getActiveNetworkInfo()!=null;
        boolean isConnected=isAvailable && cm.getActiveNetworkInfo().isConnected();
        return isConnected;
    }
    @Override
    public void onStart() {
        super.onStart();
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading ....");
        if (isNetworkAvailableAndConnected()&& isNetworkConnected()) {
            FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
            if (currentUser != null) {
                progressDialog.show();
                Log.v("phone mohamed",currentUser.getEmail().replace("@yahoo.com", ""));
                DatabaseReference mReference = mDatabaseReference.child(currentUser.getEmail().replace("@yahoo.com", "").trim());
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {


                            Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();

                            mUser = new User(
                                    map.get("name"), map.get("phone"), map.get("image"), map.get("type"), map.get("password"), map.get("country")
                                    , map.get("date")
                            );
                            startActivity(ProfileActivity.newIntent(getActivity(), mUser));
                            getActivity().finish();
                            progressDialog.dismiss();
                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }else{
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
    private void Login(){
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(LoginActivity.newIntent(getActivity(),false,null));
                getActivity().finish();
            }
        });
    }

    private void CraeteAcount(){
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        startActivity(RegisterActivity.newIntent(getActivity()));
                getActivity().finish();
            }
        });
    }
}
