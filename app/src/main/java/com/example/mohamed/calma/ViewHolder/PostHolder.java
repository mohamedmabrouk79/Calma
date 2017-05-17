package com.example.mohamed.calma.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.UserPost;

/**
 * Created by mohamed on 4/24/17.
 */

public class PostHolder extends RecyclerView.ViewHolder {
    private TextView postDes;
    private ImageView postImage;
    private LinearLayout like;
    private LinearLayout comment;
    private LinearLayout share;
    private Context mContext;
    public PostHolder(View itemView,Context context) {
        super(itemView);
        mContext=context;
        postDes= (TextView) itemView.findViewById(R.id.post_des);
        postImage= (ImageView) itemView.findViewById(R.id.post_image);
        like= (LinearLayout) itemView.findViewById(R.id.like);
        comment= (LinearLayout) itemView.findViewById(R.id.comment);
        share= (LinearLayout) itemView.findViewById(R.id.share);
        Like();
        Share();
        Comment();
    }

    public void BindData(UserPost profile){
        postDes.setText(profile.getName());
//        if (!profile.getImageUrl().isEmpty()){
//            Picasso.with(mContext).load(Uri.parse(profile.getImageUrl())).into(postImage);
//        }
    }

    private void Like(){
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void Comment(){
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void Share(){
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
