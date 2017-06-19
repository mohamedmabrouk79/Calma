package com.example.mohamed.calma.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.calma.Adapters.ArticlePagerAdapter;
import com.example.mohamed.calma.Adapters.TestAdapter;
import com.example.mohamed.calma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 08/06/2017.
 */

public class FragmentArticles  extends Fragment{
    private ViewPager mViewPager;
    public static final String KEY="TYPE";
    private DatabaseReference mDatabaseReference;
    private List<String> list=new ArrayList<>();

    public static FragmentArticles newFragment(String type){
        Bundle bundle=new Bundle();
        bundle.putSerializable(KEY,type);
        FragmentArticles articles=new FragmentArticles();
        articles.setArguments(bundle);
        return  articles;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.article_fragment_view,container,false);
        mViewPager= (ViewPager) view.findViewById(R.id.Articles_view_pager);
        switch ((String)getArguments().getSerializable(KEY)){
            case "الاكتئاب":
                mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("depression").child("articles");
                break;
            case "التوتر":
                mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("tension").child("articles");
                break;

            case "الكسل":
                mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("laziness").child("articles");
                break;
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateUi();
    }

    private void getArticle(List<String> strings){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mViewPager.setAdapter(new ArticlePagerAdapter(fragmentManager,strings));
    }

    private void UpdateUi(){
        ArticlesSet articlesSet=new ArticlesSet();
        articlesSet.execute();
    }

     class ArticlesSet extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String>  list= (List<String>) dataSnapshot.getValue();
                    getArticle(list);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }

     }
}
