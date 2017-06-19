package com.example.mohamed.calma.Activites;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.calma.Adapters.ProfileAdapter;
import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;


public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager mViewPager;
    private FragmentManager fragmentManager=getSupportFragmentManager();
    private Fragment mFragment=fragmentManager.findFragmentById(R.id.Fragment_Container_Profile);
    private TextView mUsername;
    private TextView mUserPhone;
    private ImageView mUserImage;
    private static User mUser;
    private FirebaseAuth mFirebaseAuth;
    public static Intent newIntent(Context context, User user){
        Intent intent=new Intent(context,ProfileActivity.class);
        mUser=user;
        return intent;
    }
    private void updateData(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUserPhone());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> map= (Map<String, String>) dataSnapshot.getValue();
                try {
                    Picasso.with(ProfileActivity.this).load(Uri.parse(map.get("image"))).into(mUserImage);
                }catch (Exception e){}

                mUsername.setText(map.get("name"));
                mUserPhone.setText(map.get("phone"));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirebaseAuth=FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        mUserImage= (ImageView) headerView.findViewById(R.id.user_image_view);
        mUsername= (TextView) headerView.findViewById(R.id.user_name_profile);
        mUserPhone= (TextView) headerView.findViewById(R.id.user_phone_profile);
        mViewPager= (ViewPager) findViewById(R.id.Fragment_Container_Profile);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        updateData();

    }

    @Override
    protected void onResume() {
        super.onResume();
         UpdateUi();
    }

    private void UpdateUi(){

     mViewPager.setAdapter(new ProfileAdapter(fragmentManager,mUser));
 }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case  R.id.Appointment:

                break;
            case  R.id.Dashbord:
                break;
            case  R.id.Messages:

                break;
            case  R.id.posts_item:
              startActivity(ShowAllPostsActivity.newIntent(this,mUser));
                break;
            case  R.id.Article_item:

                break;
            case  R.id.Settings:

                break;
            case  R.id.Support:

                break;
            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(MainActivity.newIntent(this));
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
