package com.example.mohamed.calma.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    private ImageView doctorImageView;
    private TextView doctorName;
    private Button  viewProfile;
    private TextView priceberHour;
    private TextView specialty;
    private TextView priceBer30Min;
    private RatingBar mRatingBar;
    private Context mContext;
    public DoctorHolder(View itemView,Context context) {
        super(itemView);
        mContext=context;
        doctorImageView= (ImageView) itemView.findViewById(R.id.doctor_result_image_view);
        doctorName= (TextView) itemView.findViewById(R.id.result_doctor_name);
        viewProfile= (Button) itemView.findViewById(R.id.view_profile);
        priceBer30Min= (TextView) itemView.findViewById(R.id.price_per_30_min);
        priceberHour= (TextView) itemView.findViewById(R.id.price_per_60_min);
        mRatingBar= (RatingBar) itemView.findViewById(R.id.ratingBar);
        specialty= (TextView) itemView.findViewById(R.id.specialty_doctor);
        ViewProfile();
    }

    private void ViewProfile(){
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void bind(Doctor doctor){
        doctorName.setText(doctor.getName());
        priceBer30Min.setText("EGP "+String.valueOf(doctor.getPriceBerHour()/2)+".00");
        priceberHour.setText("EGP "+String.valueOf(doctor.getPriceBerHour())+".00");
        String s=null;
        for (String s1: doctor.getSpecialty()){
            s+="["+s1+"] ";
        }
        specialty.setText(s);
        mRatingBar.setProgress(doctor.getRate());
    }
}
