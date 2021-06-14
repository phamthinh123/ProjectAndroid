package com.example.phamxuanthinh_b17dcat175_btl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView email,name;
    private Button btnLogout,btnPass,btnDelete;
    private ImageView picProfile;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference reference;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        picProfile=findViewById(R.id.picProfile);

        btnLogout=findViewById(R.id.btnLogout);
        btnPass=findViewById(R.id.btnPass);
        btnDelete=findViewById(R.id.btnDelete);
        storage=FirebaseStorage.getInstance();
        reference=storage.getReference();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        if(mUser!=null){
                email.setText(mUser.getEmail());
                name.setText(mUser.getDisplayName());
                Picasso.get().load(mUser.getPhotoUrl()).into(picProfile);




        }
        picProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent  = new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mAuth.signOut();
                        Intent intent  = new Intent(ProfileActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(ProfileActivity.this,"Xóa tài khoản thành công",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this,"Xóa tài khoản thất bại",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ProfileActivity.this, PasswordActivity.class);

                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){

        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imageUri=data.getData();
            picProfile.setImageURI(imageUri);
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(imageUri)
                    .build();

            mUser.updateProfile(profileUpdates);

            uploadPicture();
        }
    };
    private void uploadPicture() {
        final String randomKey= UUID.randomUUID().toString();
        StorageReference riverRef=reference.child("images/" + randomKey);

        riverRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                Picasso.get().load(imageUri).into(picProfile);
                Toast.makeText(ProfileActivity.this,"Cập nhật ảnh đại diện thành công",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this,"Cập nhật ảnh đại diện thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }


}