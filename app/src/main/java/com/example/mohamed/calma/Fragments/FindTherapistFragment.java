package com.example.mohamed.calma.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.Doctor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ali on 4/20/17.
 */

public class FindTherapistFragment extends Fragment {
   private RecyclerView mRecyclerView;
    public static FindTherapistFragment newFragment(){
        return new FindTherapistFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.find_thrapist,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.find_thrapist_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new findAdapter(getDoctors()));
        return view;
    }

    private List<Doctor> getDoctors(){
        List<Doctor> doctors=new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            Doctor doctor=new Doctor();
            doctor.setName("mohamed #"+(i+1));
            doctors.add(doctor);
        }
        return doctors;
    }

    private class FindHolder extends RecyclerView.ViewHolder{
        private ImageView doctorImageView;
        private TextView doctorName;
        private Button  viewProfile;
        public FindHolder(View itemView) {
            super(itemView);
            doctorImageView= (ImageView) itemView.findViewById(R.id.doctor_result_image_view);
            doctorName= (TextView) itemView.findViewById(R.id.result_doctor_name);
            viewProfile= (Button) itemView.findViewById(R.id.view_profile);
            viewProfile.setOnClickListener(v->{
                // go to doctor profile
            });
        }


        public void BindData(Doctor doctor){
            doctorName.setText(doctor.getName());
        }
    }


    class findAdapter extends RecyclerView.Adapter<FindHolder>{
        List<Doctor> mDoctors=new ArrayList<>();
        public findAdapter(List<Doctor> doctors){
            mDoctors=doctors;
        }
        @Override
        public FindHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.doctor_view,parent,false);
            return new FindHolder(view);
        }

        @Override
        public void onBindViewHolder(FindHolder holder, int position) {
        Doctor doctor=mDoctors.get(position);
            holder.BindData(doctor);
        }

        @Override
        public int getItemCount() {
            return mDoctors.size();
        }
    }
}
