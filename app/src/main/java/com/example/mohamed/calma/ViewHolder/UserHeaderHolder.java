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
import com.squareup.picasso.Picasso;

/**
 * Created by mohamed on 14/06/2017.
 */

public class UserHeaderHolder extends RecyclerView.ViewHolder {
    private KenBurnsView userImageView;
    private TextView userName;
    private TextView upgrade;
    private ImageView select;
    private Context mContext;
    private User mUser;
    private DatabaseReference mDatabaseReference;
    public UserHeaderHolder(View itemView,Context context,User user) {
        super(itemView);
        mContext=context;
        mUser=user;
        userImageView= (KenBurnsView) itemView.findViewById(R.id.user_image_profile_header);
        userName= (TextView) itemView.findViewById(R.id.user_name_header_user);
        upgrade= (TextView) itemView.findViewById(R.id.user_upgrade_to_doctor);
        select= (ImageView) itemView.findViewById(R.id.user_options);
        Selcted();
    }

    public void BindData(Header header){
        try {
           // Picasso.with(mContext).load(Uri.parse(header.getImageUrl())).into(userImageView);
        }catch (Exception e){}
        userName.setText(header.getUserName());

    }

    private void Selcted(){
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
                                ((Activity)mContext).finish();
                                return true;
                            case R.id.add_post:
                            //// TODO: 14/06/2017  add post here
                                mContext.startActivity(AddPostActivity.newIntent(mContext,mUser));
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
