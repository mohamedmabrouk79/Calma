package com.example.mohamed.calma.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.calma.R;
import com.example.mohamed.calma.ViewHolder.HeaderHolder;
import com.example.mohamed.calma.ViewHolder.PostHolder;
import com.example.mohamed.calma.ViewHolder.UserHeaderHolder;
import com.example.mohamed.calma.model.Header;
import com.example.mohamed.calma.model.User;
import com.example.mohamed.calma.model.UserPost;

import java.util.List;

/**
 * Created by mohamed on 4/24/17.
 */

public class UsrProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private String mPostId;
    Header header;
    List<UserPost> listItems;
    User mUser;

    public UsrProfileAdapter(Header mHeader, List<UserPost> mUserPosts,User user,String postId){
        this.header=mHeader;
        this.listItems= mUserPosts;
        mPostId=postId;
        mUser=user;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEADER){
            Log.v("Type",TYPE_HEADER+"");
            View view = null;
            if (mUser.getType().equals("user")){
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_header,parent,false);
                return  new UserHeaderHolder(view,parent.getContext(),mUser);

            }else {
                 view= LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_header,parent,false);
                return  new HeaderHolder(view,parent.getContext(),mUser);
            }
        }else if (viewType==TYPE_ITEM){
            Log.v("Type",TYPE_ITEM+"");
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
             return new PostHolder(view,parent.getContext(),mPostId,mUser);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
   if (holder instanceof HeaderHolder){
        HeaderHolder headerHolder= (HeaderHolder) holder;
       Log.v("Type",header.getUserName()+"");
       headerHolder.BindData(header);
   }else if(holder instanceof UserHeaderHolder){
       UserHeaderHolder headerHolder= (UserHeaderHolder) holder;
       Log.v("Type",header.getUserName()+"");
       headerHolder.BindData(header);
   }else if (holder instanceof PostHolder){
       UserPost userPost =listItems.get(position-1);
       PostHolder postHolder= (PostHolder) holder;
       postHolder.BindData(userPost);

   }

    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return listItems.size()+1;
    }
}
