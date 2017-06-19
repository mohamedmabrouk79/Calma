package com.example.mohamed.calma.Activites;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.calma.R;
import com.example.mohamed.calma.ViewHolder.PostHolder;
import com.example.mohamed.calma.model.CommentModel;
import com.example.mohamed.calma.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
     private RecyclerView mRecyclerView;
    private EditText comment;
     private DatabaseReference mDatabaseReference;
    private static final String POSTKEY="postsid";
    private static User mUser;
    List<CommentModel> models=new ArrayList<CommentModel>();

    public static Intent newIntent(Context context, String postId, User user){
        Intent  intent=new Intent(context,PostActivity.class);
        intent.putExtra(POSTKEY,postId);
        mUser=user;
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        comment= (EditText) findViewById(R.id.pComment);
        mRecyclerView= (RecyclerView) findViewById(R.id.post_comments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("posts").child(getIntent().getStringExtra(POSTKEY));
    }

    @Override
    protected void onResume() {
        super.onResume();
       UpdateUi();
    }

    private void UpdateUi(){
        try {
         if (mDatabaseReference==null){
             // not comments at this post
         }else {
           mDatabaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                       DatabaseReference reference=mDatabaseReference.child("comment").child(snapshot.getKey());
                       reference.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String,String> map= (Map<String, String>) dataSnapshot.getValue();
                            CommentModel model=new CommentModel();
                               model.setOwner(mUser.getUserName());
                               model.setComment(map.get("comment"));
                               model.setImage(mUser.getUserImageUrl());
                               models.add(model);
                               Update(models);
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
        }catch (Exception e){

        }
    }

    private void Update(List<CommentModel> models1){
        PostAdapter adapter=new PostAdapter(models1);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void comment(View view) {
        DatabaseReference mReference=mDatabaseReference.child("comment").push();
        mDatabaseReference.child("comment").setValue(comment.getText());
        mReference.child("imageurl").setValue(mUser.getUserImageUrl());
        mDatabaseReference.child("owner").setValue(mUser.getUserPhone());
    }

    class PostAdapter extends RecyclerView.Adapter<PostHolder>
    {
        List<CommentModel> commentModels=new ArrayList<>();
         public PostAdapter(List<CommentModel> models){
             commentModels=models;
         }

        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_view,parent,false);
            return new PostHolder(view);
        }

        @Override
        public void onBindViewHolder(PostHolder holder, int position) {
          holder.bind(commentModels.get(position));
        }

        @Override
        public int getItemCount() {
            return commentModels.size();
        }
    }

    class PostHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mUserName;
        private TextView mUsercontant;
        public PostHolder(View itemView) {
            super(itemView);
         mImageView= (ImageView) itemView.findViewById(R.id.post_owner_image_view);
            mUserName= (TextView) itemView.findViewById(R.id.user_post_owner);
            mUsercontant= (TextView) itemView.findViewById(R.id.user_post_contant);
        }

        public void bind(CommentModel model){
            if (model.getImage()!=null){
                Picasso.with(PostActivity.this).load(Uri.parse(model.getImage())).into(mImageView);
            }
            mUserName.setText(model.getOwner());
            mUsercontant.setText(model.getComment());

        }
    }
}
