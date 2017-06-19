package com.example.mohamed.calma.Activites;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mohamed.calma.R;
import com.example.mohamed.calma.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by mohamed on 14/06/2017.
 */

public class AddPostActivity extends AppCompatActivity {
    private EditText postContent;
    private ImageButton postImageButton;
    private Button publishButton;
    private Button cancelButton;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private static User mUser;
    private static final int IMAGE_REQUEST=0;
    private ProgressDialog mProgressDialog;
    private Uri mUri;

    public static Intent newIntent(Context context, User user){
        Intent intent=new Intent(context,AddPostActivity.class);
        mUser=user;
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_view);
        postContent= (EditText) findViewById(R.id.post_content);
        postImageButton= (ImageButton) findViewById(R.id.add_post_image);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUserPhone()).child("posts");
        mStorageReference= FirebaseStorage.getInstance().getReference().child("PostsImage");
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Adding Post ........");
    }

    public void PublishPost(View view){
        switch (view.getId()){
            case R.id.add_post_image:
               Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,IMAGE_REQUEST);
                break;
            case R.id.publish_post:
                final String contant=postContent.getText().toString().trim();
                if (TextUtils.isEmpty(contant)){
                    postContent.setText("Please enter you post contant ");
                }else {
                    mProgressDialog.show();

                    if (mUri!=null){
                        StorageReference mReference=mStorageReference.child(mUri.getLastPathSegment());
                        mReference.putFile(mUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String url= String.valueOf(taskSnapshot.getDownloadUrl());
                                Toast.makeText(AddPostActivity.this, url, Toast.LENGTH_SHORT).show();
                                 DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("allPosts").child(mUser.getType()).child(mUser.getUserPhone()).push();
                                 reference.setValue(true);
                                 DatabaseReference posts=FirebaseDatabase.getInstance().getReference().child("posts").child(reference.getKey());

                                 posts.child("contant").setValue(contant);
                                 posts.child("owner").setValue(mUser.getUserPhone());
                                 posts.child("posturl").setValue(url);

                                mProgressDialog.dismiss();
                                finish();
                            }
                        });
                    }else {
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("allPosts").child(mUser.getType()).child(mUser.getUserPhone()).push();
                        reference.child("contant").setValue(contant);
                        reference.child("owner").setValue(mUser.getUserPhone());
                        reference.child("posturl").setValue("null");
                        mProgressDialog.dismiss();
                        finish();
                    }
                }

                break;
            case R.id.cancel:
                 finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==IMAGE_REQUEST && resultCode== RESULT_OK){
            mUri=data.getData();
            postImageButton.setImageURI(mUri);
        }
    }
}
