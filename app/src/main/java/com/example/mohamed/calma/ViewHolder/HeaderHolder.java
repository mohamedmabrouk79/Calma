package com.example.mohamed.calma.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.calma.Activites.AddPostActivity;
import com.example.mohamed.calma.Activites.EditActivity;
import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.Header;
import com.example.mohamed.calma.model.User;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by mohamed on 4/24/17.
 */

public class HeaderHolder extends RecyclerView.ViewHolder {
    private KenBurnsView userImageView;
    private TextView userName;
    private TextView followers;
    private TextView articles;
    private TextView videos;
    private TextView about;
    private TextView schedule;
    private ImageView select;
    private Context mContext;
    private User mUser;
    private DatabaseReference mDatabaseReference;

    public HeaderHolder(View itemView, Context context, User user) {
        super(itemView);
        mUser=user;
        mContext=context;
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUserPhone());
        userImageView = (KenBurnsView) itemView.findViewById(R.id.user_image_profile_header);
        userName= (TextView) itemView.findViewById(R.id.user_name_header);
        followers= (TextView) itemView.findViewById(R.id.followers_number);
        articles= (TextView) itemView.findViewById(R.id.articles_number);
        videos= (TextView) itemView.findViewById(R.id.videos_number);
        about= (TextView) itemView.findViewById(R.id.about);
        schedule= (TextView) itemView.findViewById(R.id.schedule);
        select= (ImageView) itemView.findViewById(R.id.edit);
        About();
        Schedule();
        EditAction();
    }

    public void BindData(Header header){
        try {
            Picasso.with(mContext).load(Uri.parse(header.getImageUrl())).into(userImageView);
        }catch (Exception e){}
        userName.setText(header.getUserName());
        followers.setText(header.getFollowers());
        articles.setText(header.getArticles());
        videos.setText(header.getVideos());
    }

  private void About(){
      about.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          }
      });
  }

  private void Schedule(){
      schedule.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          }
      });
  }

    private void EditAction(){
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, select);
                popup.getMenuInflater()
                        .inflate(R.menu.edit_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(mContext,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        switch (item.getItemId()){
                            case R.id.edit_profile:
                                mContext.startActivity(EditActivity.newIntent(mContext,mUser));
                                return true;
                            case R.id.add_post:
                                mContext.startActivity(AddPostActivity.newIntent(mContext,mUser));
                                ((Activity)mContext).finish();
                                return true;
                        }
                        return true;
                    }
                });

                popup.show();


            }
        });
    }
}
