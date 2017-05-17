package com.example.mohamed.calma.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.mohamed.calma.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mohamed on 09/05/2017.
 */

public class ArticleFragment extends Fragment {
    public static final String TYPE="type";
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;
    private String type;
    public static ArticleFragment newFragment(String type){
        Bundle bundle=new Bundle();
        bundle.putSerializable(TYPE,type);
        ArticleFragment fragment=new ArticleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.articles_fragment,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.Articles_Recycler_View);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
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
        return view;
    }

    private void UpdateUi(){
        FirebaseRecyclerAdapter<String,ArticleHolder> adapter=new FirebaseRecyclerAdapter<String, ArticleHolder>
                (String.class,R.layout.article_view,ArticleHolder.class,mDatabaseReference) {
            @Override
            protected void populateViewHolder(ArticleHolder viewHolder, String model, int position) {
             viewHolder.bind(model);
            }
        };

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateUi();
    }

    public static class ArticleHolder extends RecyclerView.ViewHolder{
        private WebView mWebView;
        private ProgressBar mProgressBar;
        public ArticleHolder(View itemView) {
            super(itemView);
            mWebView= (WebView) itemView.findViewById(R.id.Articles_Web_view);
            mProgressBar= (ProgressBar) itemView.findViewById(R.id.fragment_photo_page_progress_bar);
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
                }
            });

            mWebView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
        }

        public void bind(String uri){
         mWebView.loadUrl(uri);
        }
    }
}
