package com.example.mohamed.calma.Fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.mohamed.calma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Created by mohamed on 10/05/2017.
 */

public class Article_Fragment extends Fragment {
    public static final String TYPE="type";
    private int count=0;
    private DatabaseReference mDatabaseReference;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private ImageView mNextImageView;
    private ImageView mPrevImageView;
    private List<String> mUrls=new ArrayList<>();
    private String type;
    public static Article_Fragment newFragment(String type){
        Bundle bundle=new Bundle();
        bundle.putSerializable(TYPE,type);
        Article_Fragment fragment=new Article_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.article_view,container,false);
      type= (String) getArguments().getSerializable(TYPE);
        switch (type){
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
        mWebView= (WebView) view.findViewById(R.id.Articles_Web_view);
        mProgressBar= (ProgressBar) view.findViewById(R.id.fragment_photo_page_progress_bar);
        mNextImageView= (ImageView) view.findViewById(R.id.article_next);
        mPrevImageView= (ImageView) view.findViewById(R.id.article_prev);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mProgressBar.setMax(100);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress==100){
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setSubtitle(title);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        //mWebView.loadUrl(mUrls.get(count));
        return view;
    }

    private void getUrls(){
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(getActivity(), mUrls.size()+"", Toast.LENGTH_SHORT).show();
    }

    private void UpdateUi(){
        next();
        Prev();
        getUrls();
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateUi();
    }

    private void next(){
        mNextImageView.setOnClickListener(e ->{
           if (count==mUrls.size()-1){
               Toast.makeText(getActivity(), "مفيش مقالات تانيه ", Toast.LENGTH_SHORT).show();
           }else {
               count++;
               mWebView.loadUrl(mUrls.get(count));
           }
        });
    }

    private void Prev(){
        mPrevImageView.setOnClickListener(e ->{
            if (count!=0){
                count--;
                mWebView.loadUrl(mUrls.get(count));
            }
        });
    }

}
