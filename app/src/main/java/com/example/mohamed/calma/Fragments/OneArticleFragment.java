package com.example.mohamed.calma.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.mohamed.calma.R;

/**
 * Created by mohamed on 08/06/2017.
 */

public class OneArticleFragment extends Fragment {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private static final String URL="URL";

    public static OneArticleFragment newFragment(String url){
        Bundle bundle=new Bundle();
        bundle.putSerializable(URL,url);
        OneArticleFragment fragment=new OneArticleFragment();
        fragment.setArguments(bundle);
        return  fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.article_view,container,false);
        mWebView= (WebView) view.findViewById(R.id.Articles_Web_view);
        mProgressBar= (ProgressBar) view.findViewById(R.id.fragment_photo_page_progress_bar);
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
        mWebView.loadUrl((String) getArguments().getSerializable(URL));
        return view;
    }
}
