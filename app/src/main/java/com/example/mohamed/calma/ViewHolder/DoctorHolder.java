package com.example.mohamed.calma.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.Doctor;
import com.example.mohamed.calma.model.TestDoctor;
import com.squareup.picasso.Picasso;

/**
 * Created by mohamed on 08/05/2017.
 */

public class DoctorHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTextView;
    private Context mContext;
    private Button mButton;
    public DoctorHolder(View itemView,Context context) {
        super(itemView);
        mContext=context;
        mImageView= (ImageView) itemView.findViewById(R.id.doctor_result_image_view);
        mTextView= (TextView) itemView.findViewById(R.id.result_doctor_name);
        mButton= (Button) itemView.findViewById(R.id.view_profile);
        ViewProfile();
    }

    private void ViewProfile(){
        mButton.setOnClickListener(w ->{
            Toast.makeText(mContext, "Doctor Profile", Toast.LENGTH_SHORT).show();
        });
    }

    public void bind(Doctor testDoctor){
        mTextView.setText(testDoctor.getName());
        Picasso.with(mContext).load(testDoctor.getImage()).into(mImageView);
    }
}
