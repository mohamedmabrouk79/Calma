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

import com.example.mohamed.calma.Adapters.UsrProfileAdapter;
import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.CommentModel;
import com.example.mohamed.calma.model.Header;
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
 * Created by ali on 4/19/17.
 */

public class UserProfileFragment extends Fragment {
    List<CommentModel> comments=new ArrayList<CommentModel>();
    List<String> likes=new ArrayList<>();
    List<String> shers=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private static User mUser;
    private List<UserPost> mUserPosts=new ArrayList<>();
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mReference;
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
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("allPosts").child(mUser.getType()).child(mUser.getUserPhone());
        mReference=FirebaseDatabase.getInstance().getReference().child("posts");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateUi();
    }

    private void UpdateUi(){
         mDatabaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {

                 for (final DataSnapshot snapshot:dataSnapshot.getChildren()){

                     final DatabaseReference reference=mReference.child(snapshot.getKey());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final Map<String,String> map= (Map<String, String>) dataSnapshot.getValue();
                            UserPost mUserPost=new UserPost();
                            String contant=map.get("contant");
                            String owner=map.get("owner");
                            String imageUrl=map.get("posturl");
                           // Toast.makeText(getActivity(),contant+ "", Toast.LENGTH_SHORT).show();
                            mUserPost.setContant(contant);
                            mUserPost.setName(owner);
                            mUserPost.setImageUrl(imageUrl);
                            DatabaseReference comment=reference.child("comment");
                            DatabaseReference like=reference.child("likes");
                            DatabaseReference sher=reference.child("sher");
                            /********* comments ******/
                            if(comment!=null){
                                comment.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ArrayList<String> comm= (ArrayList<String>) dataSnapshot.getValue();
                                        CommentModel model=new CommentModel();
                                         try {
                                             model.setImage(mUser.getUserImageUrl());
                                             model.setComment(comm.get(0));
                                             model.setOwner(mUser.getUserName());
                                             comments.add(model);
                                         }catch (Exception e){
                                         }
                                       // Toast.makeText(getActivity(),model.getComment()+ "", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                            /***********likes************/
                            if(like!=null){
                                like.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        likes.add(dataSnapshot.getValue(String.class));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                            /********* shers *********/
                            if(sher!=null){
                                sher.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        shers.add(dataSnapshot.getValue(String.class));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                            mUserPost.setComments(comments);
                            mUserPost.setLikes(likes);
                            mUserPost.setShares(shers);
                             mUserPosts.add(mUserPost);
                            if (mUser.getType().equals("user")){
                                UpadetUserUi(mUserPosts,snapshot.getKey(),getUserHeader());
                            }else {
                                UpadetUserUi(mUserPosts,snapshot.getKey(),getDoctorHeader());
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });


    }

    private void UpadetUserUi(List<UserPost> userPostList,String key,Header mHeader){
        mRecyclerView.setAdapter(new UsrProfileAdapter(mHeader,userPostList,mUser,key));
    }
    private Header getUserHeader(){
        Header header=new Header();
        header.setImageUrl(mUser.getUserImageUrl());
        header.setUserName(mUser.getUserName());
        return header;
    }
    private Header getDoctorHeader(){
        Header header=new Header();
        header.setArticles("15");
        header.setFollowers("100");
        header.setImageUrl(mUser.getUserImageUrl());
        header.setUserName(mUser.getUserName());
        header.setVideos("20");
        return header;
    }
}
