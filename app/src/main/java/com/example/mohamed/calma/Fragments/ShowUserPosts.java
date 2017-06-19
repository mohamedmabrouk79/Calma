package com.example.mohamed.calma.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mohamed.calma.R;
import com.example.mohamed.calma.ViewHolder.PostHolder;
import com.example.mohamed.calma.model.User;
import com.example.mohamed.calma.model.UserPost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mohamed on 15/06/2017.
 */

public class ShowUserPosts extends Fragment {
    private RecyclerView mRecyclerView;
    private List<UserPost> mUserPosts=new ArrayList<>();
    private static User mUser;

    private DatabaseReference mDatabaseReference;

    public static ShowUserPosts newPosts(User type){
        mUser=type;
        return new ShowUserPosts();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.post_list_view,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.show_doctor_user_post);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("allPosts");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPosts();
    }


    private void UpdateUi(List<UserPost> posts , String potsId){
        ShowUserPosts.PostsAdapter adapter=new ShowUserPosts.PostsAdapter(posts,potsId);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getPosts(){
        final DatabaseReference mReference=mDatabaseReference.child("user");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserPosts.clear();
                Iterable<DataSnapshot> strings=dataSnapshot.getChildren();
                try {

                    for (DataSnapshot snapshot : strings) {
                        for (final DataSnapshot snapshot1 : snapshot.getChildren()) {
                           final DatabaseReference posts=FirebaseDatabase.getInstance().getReference().child("posts").child(snapshot1.getKey());
                            posts.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String, String> list = (Map<String, String>) dataSnapshot.getValue();

                                    DatabaseReference comments = posts.child("comments");
                                    DatabaseReference likes = posts.child("likes");
                                    DatabaseReference shares = posts.child("shares");

                                    comments.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot cSnapshot : dataSnapshot.getChildren()) {
                                                Map<String, String> map = (Map<String, String>) cSnapshot.getValue();
                                                //                   Toast.makeText(getActivity(), map.get("coment") + "", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    UserPost userPost = new UserPost(list.get("owner"), list.get("posturl"));
                                    mUserPosts.add(userPost);
                                    UpdateUi(mUserPosts,snapshot1.getKey());

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                }catch (Exception e){

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    class PostsAdapter extends RecyclerView.Adapter<PostHolder>{
        private List<UserPost> mUserPosts;
        private String mPostId;
        public PostsAdapter(List<UserPost> posts,String postId){
            mUserPosts=posts;
            mPostId=postId;
        }

        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.post,parent,false);
            return new PostHolder(view,getActivity(),mPostId,mUser);
        }

        @Override
        public void onBindViewHolder(PostHolder holder, int position) {
            holder.BindData(mUserPosts.get(position));
        }

        @Override
        public int getItemCount() {
            return mUserPosts.size();
        }
    }

}
