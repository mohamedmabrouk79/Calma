package com.example.mohamed.calma.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mohamed.calma.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mohamed on 09/05/2017.
 */

public class VideoResultFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;
    public static  String API_KEY = "AIzaSyARLsxBPi4FtDqzF5MNq4hU9i15eIf5OjQ";
    private String type;

    public static VideoResultFragment newFragment(String type){
    Bundle bundle=new Bundle();
        bundle.putSerializable(ArticleFragment.TYPE,type);
        VideoResultFragment fragment=new VideoResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater .inflate(R.layout.videos_fragment,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.Videos_Recycler_View);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        type= (String) getArguments().getSerializable(ArticleFragment.TYPE);
        switch (type){
            case "الاكتئاب":
                mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("depression").child("videos");
                break;
            case "التوتر":
                mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("tension").child("videos");
                break;
            case "الكسل":
                mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("laziness").child("videos");
                break;
        }
        return view;
    }

    private void UpdateUi(){
        FirebaseRecyclerAdapter<String,VideoHolder> adapter=new FirebaseRecyclerAdapter<String,VideoHolder>
                (String.class,R.layout.result_video_view,VideoHolder.class,mDatabaseReference) {
            @Override
            protected void populateViewHolder(VideoHolder viewHolder, String model, int position) {
                viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                                API_KEY,model,100,true,false);
                        startActivity(intent);
                    }
                });
                YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener=new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }
                };

                viewHolder.youTubeThumbnailView.initialize(API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                        youTubeThumbnailLoader.setVideo(model);
                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                        Log.v("erororor",youTubeInitializationResult+"");
                    }
                });

            }
        };

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateUi();
    }

    public  static class  VideoHolder extends RecyclerView.ViewHolder {
        public YouTubeThumbnailView youTubeThumbnailView;
        public ImageView playButton;
        public VideoHolder(View itemView) {
            super(itemView);
            youTubeThumbnailView= (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
            playButton= (ImageView) itemView.findViewById(R.id.btnYoutube_player);
        }
    }
}
