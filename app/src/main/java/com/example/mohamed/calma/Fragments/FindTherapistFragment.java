package com.example.mohamed.calma.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.calma.Activites.ResultTestActivity;
import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.Doctor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by ali on 4/20/17.
 */

public class FindTherapistFragment extends Fragment {
   private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    public static FindTherapistFragment newFragment(){
        return new FindTherapistFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.found_thrapist,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.find_thrapist_recycler_view);
        mSearchView= (SearchView) view.findViewById(R.id.search_view_doctor);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new findAdapter(getDoctors()));
        /***** doctor search ***************/
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.onActionViewCollapsed();
                mSearchView.setQuery("", false);
                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                if (!Illness(query)){
                    //  get only doctor
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    private boolean Illness(String type){
        Pattern d = Pattern.compile("[Dd][Ee][Pp](.+)?");
        Pattern l = Pattern.compile("[Ll][Aa][Zz](.+)?");
        Pattern t = Pattern.compile("[Tt][Ee][Nn](.+)?");
        Matcher md = d.matcher(type);
        Matcher ml = l.matcher(type);
        Matcher mt = t.matcher(type);

        boolean dep=md.matches();
        boolean lazy=ml.matches();
        boolean tawator=mt.matches();
        if (dep){
            startActivity(ResultTestActivity.newIntent(getActivity(),"الاكتئاب"));
            Toast.makeText(getActivity(), "Depression", Toast.LENGTH_SHORT).show();

            return true;
        }else if (lazy){
            startActivity(ResultTestActivity.newIntent(getActivity(),"الكسل"));
            Toast.makeText(getActivity(), "Laziness", Toast.LENGTH_SHORT).show();

            return true;
        }else if (tawator){
            startActivity(ResultTestActivity.newIntent(getActivity(),"التوتر"));
            Toast.makeText(getActivity(), "Tension", Toast.LENGTH_SHORT).show();

            return true;
        }else {
            Toast.makeText(getActivity(), "no match", Toast.LENGTH_SHORT).show();

            return false;
        }

    }

    private List<Doctor> getDoctors(){
        List<Doctor> doctors=new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            Doctor doctor=new Doctor();
            doctor.setName("mohamed #"+(i+1));
            doctor.setPriceBerHour(((i+1)*100)/(i+1));
            doctor.setRate(4);
            List<String> specialty= Arrays.asList("depression","lazy");
            doctor.setSpecialty(specialty);
            doctors.add(doctor);
        }
        return doctors;
    }

    private class FindHolder extends RecyclerView.ViewHolder{
        private ImageView doctorImageView;
        private TextView doctorName;
        private Button  viewProfile;
        private TextView priceberHour;
        private TextView specialty;
        private TextView priceBer30Min;
        private RatingBar mRatingBar;

        public FindHolder(View itemView) {
            super(itemView);
            doctorImageView= (ImageView) itemView.findViewById(R.id.doctor_result_image_view);
            doctorName= (TextView) itemView.findViewById(R.id.result_doctor_name);
            viewProfile= (Button) itemView.findViewById(R.id.view_profile);
            priceBer30Min= (TextView) itemView.findViewById(R.id.price_per_30_min);
            priceberHour= (TextView) itemView.findViewById(R.id.price_per_60_min);
            mRatingBar= (RatingBar) itemView.findViewById(R.id.ratingBar);
            specialty= (TextView) itemView.findViewById(R.id.specialty_doctor);

            viewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


        public void BindData(Doctor doctor){
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


    class findAdapter extends RecyclerView.Adapter<FindHolder>{
        List<Doctor> mDoctors=new ArrayList<>();
        public findAdapter(List<Doctor> doctors){
            mDoctors=doctors;
        }
        @Override
        public FindHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.find_doctor_view,parent,false);
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
