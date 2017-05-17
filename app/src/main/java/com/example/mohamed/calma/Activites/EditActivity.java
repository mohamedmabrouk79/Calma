package com.example.mohamed.calma.Activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private ImageView userImage;
    private EditText mNameText;
    private EditText mPasswordText;
    private EditText mRepeatText;
    private EditText mMobileNumnText;
    private RadioButton mMaleButton;
    private RadioButton mFemale;
    private Spinner mSpinner;
    private Button save;
    private Button cancel;
    private List<String> mList;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog mProgressDialog;
    private static User mUser;
    private String gender="male";
    private Uri imageUri;
    private StorageReference mStorageReference;

    public static Intent newIntent(Context context,User user){
        Intent intent=new Intent(context,EditActivity.class);
        mUser=user;
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_fragment);
        userImage= (ImageView) findViewById(R.id.edit_image_view);
        mNameText= (EditText) findViewById(R.id.name_edit_view);
        mPasswordText= (EditText) findViewById(R.id.password_edit_text);
        mRepeatText= (EditText) findViewById(R.id.repeat_password_edit_text);
        mMobileNumnText= (EditText) findViewById(R.id.phone_number_edit);
        mMaleButton= (RadioButton) findViewById(R.id.male_edit);
        mFemale= (RadioButton) findViewById(R.id.female_edit);
        mSpinner= (Spinner) findViewById(R.id.country_spinner_edit);
        save= (Button) findViewById(R.id.save);
        cancel= (Button) findViewById(R.id.cancel);
        mList=new ArrayList<>();
        mProgressDialog=new ProgressDialog(this);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        mProgressDialog.setMessage("Updating ....");
        String[] counries=getResources().getStringArray(R.array.counteries);
        for (int i=0 ; i<counries.length;i++){
            mList.add(counries[i]);
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mList);
        mSpinner.setAdapter(arrayAdapter);
        mStorageReference= FirebaseStorage.getInstance().getReference().child("UsersImage");
        userImage.setOnClickListener(v ->{
            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,0);
        });
        PutData();
        Save();
        Cancel();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK){
            imageUri=data.getData();
            Picasso.with(this).load(imageUri).into(userImage);
        }
    }

    private void PutData(){
        mNameText.setText(mUser.getUserName());
        mMobileNumnText.setText(mUser.getUserPhone());
        Toast.makeText(this, mUser.getDate()+"", Toast.LENGTH_SHORT).show();
    }

    private void Save(){
        save.setOnClickListener(v ->{
            FirebaseUser user=mFirebaseAuth.getCurrentUser();
            String name=mNameText.getText().toString().trim();
            String phone=mMobileNumnText.getText().toString().trim();
            String country=mSpinner.getSelectedItem().toString();
            String oldpassword=mPasswordText.getText().toString().trim();
            String confirmpassword=mRepeatText.getText().toString();

            mFemale.setOnClickListener(w ->{gender="female";});
            mMaleButton.setOnClickListener(w ->{gender="male";});
            if (TextUtils.isEmpty(name)){
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(phone)){
                Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();

            }else if(!oldpassword.equals(mUser.getPass())){
                Toast.makeText(this, "Enter correct  Password", Toast.LENGTH_SHORT).show();

            }else if (confirmpassword.length()<6){
                Toast.makeText(this, "Password must more 6 ", Toast.LENGTH_SHORT).show();

            }else if (TextUtils.isEmpty(imageUri.toString())) {

            }else{
                mProgressDialog.show();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(mUser.getUserPhone()+"@yahoo.com", mUser.getPass());
                mStorageReference.child(phone);
                mStorageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                        user.updateEmail(phone+"@yahoo.com").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    user.updatePassword(confirmpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                DatabaseReference mReference=mDatabaseReference.child(mUser.getUserPhone());
                                                mReference.removeValue();
                                                     DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
                                                        reference.child("name").setValue(name);
                                                        reference.child("phone").setValue(phone);
                                                        reference.child("password").setValue(confirmpassword);
                                                        reference.child("country").setValue(country);
                                                        reference.child("gender").setValue(gender);
                                                        reference.child("date").setValue(mUser.getDate());
                                                        reference.child("image").setValue(""+taskSnapshot.getDownloadUrl());
                                                        mProgressDialog.dismiss();
                                            }   else{
                                                mProgressDialog.dismiss();
                                                Toast.makeText(EditActivity.this, "Password is not updated", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else{
                                    mProgressDialog.dismiss();
                                    Toast.makeText(EditActivity.this, "Email is not updated"+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                            }
                        });
                        /**** end **/
                    }
                });

            }

        });
    }

    private void Cancel(){
        cancel.setOnClickListener(v ->{

        });
    }
}
