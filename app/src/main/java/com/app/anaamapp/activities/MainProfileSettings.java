package com.app.anaamapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.anaamapp.R;
import com.app.anaamapp.data.DataSource;
import com.app.anaamapp.model.User;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainProfileSettings extends AppCompatActivity
{
    Uri fileUri;
    EditText profileTitle,etEmail,etAddress;
    EditText userDetails;
    CircleImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile_settings);
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Edit Profile Settings");
        profileTitle=findViewById(R.id.et_profile_title);
        userDetails=findViewById(R.id.et_details);
        etEmail=findViewById(R.id.et_email);
        etAddress=findViewById(R.id.et_address);
        ImageView imgBack=findViewById(R.id.img_back);
        userImage=findViewById(R.id.user_img);
        TextView saveButton=findViewById(R.id.button_save);
        DataSource source=DataSource.getInstance(this);
        User currentUser=source.getCurrentUser();
        profileTitle.setText(currentUser.getTitle());
        userDetails.setText(currentUser.getDetails());
        etEmail.setText(currentUser.getEmail());
        etAddress.setText(currentUser.getAddress());
        Glide.with(this).load(currentUser.getImgsource()).into(userImage);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String title=profileTitle.getText().toString().trim();
                String details=userDetails.getText().toString().trim();
                String address=etAddress.getText().toString().trim();
                String email=etEmail.getText().toString().trim();
                if(title.isEmpty()||details.isEmpty())
                {
                    Toast.makeText(MainProfileSettings.this, "Please enter the required information", Toast.LENGTH_SHORT).show();
                }
                else if(fileUri!=null)
                {
                    uploadData(title,details);
                }
                else
                {
                    User user=source.getCurrentUser();
                    user.setDetails(details);
                    user.setTitle(title);
                    user.setEmail(email);
                    DatabaseReference mUserRef= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUserid());
                    Map<String, Object> updates = new HashMap<String,Object>();
                    updates.put("title",title);
                    updates.put("details",details);
                    updates.put("address",address);
                    updates.put("email",email);
                    mUserRef.updateChildren(updates);
                    finish();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ImagePicker.Companion.with(MainProfileSettings.this).cropSquare().compress(200).start();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            fileUri = data.getData();
            Glide.with(this).load(fileUri).into(userImage);
        }
    }

    public void uploadData(String title,String details)
    {

        String id= FirebaseAuth.getInstance().getUid();
        final StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child("user_images" + "/" +id);
        StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpg").build();

        try {
            byte[] thumb_byte_data;
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fileUri);
            Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);
            thumb_byte_data = baos.toByteArray();
            UploadTask uploadTask = mStorageRef.putBytes(thumb_byte_data, metadata);
            final Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return mStorageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        String number=getIntent().getStringExtra("number");
                        User user=new User();
                        user.setDetails(details);
                        user.setTitle(title);
                        user.setNumber(number);
                        user.setImgsource(task.getResult().toString());
                        DatabaseReference mUserRef= FirebaseDatabase.getInstance().getReference().child("Users");
                        String id=FirebaseAuth.getInstance().getUid();
                        mUserRef.child(id).setValue(user);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Failed to upload details.! Try again" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();//error saving photo
                    }
                }
            });

        } catch (Exception e)
        {

        }
    }

}