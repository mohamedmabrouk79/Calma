package com.example.mohamed.calma.Fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.calma.Adapters.UsrProfileAdapter;
import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.Header;
import com.example.mohamed.calma.model.User;
import com.example.mohamed.calma.model.UserPost;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ali on 4/19/17.
 */

public class UserProfileFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private static User mUser;
    public static UserProfileFragment newInstance (User user){
        mUser=user;
        return new UserProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_profile_view,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.user_profile_posts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       // UsrProfileAdapter adapter=new UsrProfileAdapter(getHeader(),getUserProfiles());
        mRecyclerView.setAdapter(new UsrProfileAdapter(getHeader(),getUserProfiles(),mUser));
        return view;
    }

    private Header getHeader(){
        Header header=new Header();
        header.setArticles("15");
        header.setFollowers("100");
        header.setImageUrl(mUser.getUserImageUrl());
        header.setUserName(mUser.getUserName());
        header.setVideos("20");
        return header;
    }

    private List<UserPost> getUserProfiles(){
        List<UserPost> userPosts =new ArrayList<>();
        for (int i=0 ; i<100 ; i++){
            UserPost userPost =new UserPost("name #"+i,null);
            userPosts.add(userPost);
        }
        return userPosts;
    }

}
