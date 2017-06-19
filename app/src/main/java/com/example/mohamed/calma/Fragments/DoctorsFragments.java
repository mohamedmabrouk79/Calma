package com.example.mohamed.calma.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.calma.R;
import com.example.mohamed.calma.ViewHolder.DoctorHolder;
import com.example.mohamed.calma.model.Doctor;
import com.example.mohamed.calma.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mohamed on 09/05/2017.
 */

public class DoctorsFragments extends Fragment {
    private RecyclerView mRecyclerView;
    public static DoctorsFragments newFragment(){
        return  new DoctorsFragments();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.doctors_frgmaent,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.Doctors_Recycler_View);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void UpdateUi(){
        mRecyclerView.setAdapter(new DoctorAdapter(getDoctors()));
    }

    private List<Doctor>  getDoctors(){
        List<Doctor> doctors=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Doctor doctor=new Doctor();
            doctor.setName("mohamed #"+(i+1));
            doctor.setPriceBerHour(((i+1)*100)/(i+1));
            doctor.setRate(4);
            List<String> specialty= Arrays.asList("depression","lazy");
            doctor.setSpecialty(specialty);
            doctors.add(doctor);
        }
        return  doctors;
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateUi();
    }

    class DoctorAdapter extends  RecyclerView.Adapter<DoctorHolder>{
        List<Doctor> mDoctors=new ArrayList<>();
        public DoctorAdapter(List<Doctor> doctors){
            mDoctors =doctors;
        }
        @Override
        public DoctorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.find_doctor_view,parent,false);

            return new DoctorHolder(view,getActivity());
        }

        @Override
        public void onBindViewHolder(DoctorHolder holder, int position) {
         holder.bind(mDoctors.get(position));
        }

        @Override
        public int getItemCount() {
            return mDoctors.size();
        }
    }
}
