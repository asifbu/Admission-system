package com.example.badman.v2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.security.Identity;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdmitCardActivity extends AppCompatActivity

  {
        private CircleImageView settingDisplayProfileImage;
      private Button settingChangeProfileImageButton;
      private TextView displayName;
      private final int Gallery_Pick = 1;
      private FirebaseAuth mAuth;
      private DatabaseReference getUserDataReference;
      private StorageReference storeProfileImage;
      private String imageUrl;


        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admit_card);


            settingDisplayProfileImage =  findViewById(R.id.setting_profile_image);
            settingChangeProfileImageButton = findViewById(R.id.setting_profile_image_button);
            displayName = findViewById(R.id.display_name);




            mAuth = FirebaseAuth.getInstance();
            String online_user_id = mAuth.getCurrentUser().getUid();
            getUserDataReference = FirebaseDatabase.getInstance().getReference().child("applicants").child(online_user_id);
            storeProfileImage = FirebaseStorage.getInstance().getReference().child("profile_images");

            getUserDataReference.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.hasChild("applicants_image"))
                    {
                        // String name = dataSnapshot.child("applicant_name").getValue().toString();
                        String image = dataSnapshot.child("applicants_image").getValue().toString();

                      //   displayName.setText(name);
                        Picasso.get().load(image).into(settingDisplayProfileImage);
                        //   Picasso.get().load(imageUri).into(settingDisplayProfileImage);

                        // Do stuff
                    }
                    else
                        {
                        Toast.makeText(AdmitCardActivity.this, "Please Insert Your Image", Toast.LENGTH_SHORT).show();
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                }
            });


            settingChangeProfileImageButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "select picture"), Gallery_Pick);

                }
            });

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode==Gallery_Pick  &&  resultCode==RESULT_OK  &&  data!=null)
            {
                Uri ImageUri=data.getData();
                CropImage.activity(ImageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }


            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK)
                {
                    Uri resultUri = result.getUri();
                    String user_id = mAuth.getCurrentUser().getUid();
                    final StorageReference  filepath = storeProfileImage.child(user_id +".jpg");


                    filepath.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                    {

                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                        {
                            if (!task.isSuccessful()){
                                throw task.getException();
                            }
                            return filepath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                final Uri downUri = task.getResult();


                                String current_user_Id = mAuth.getCurrentUser().getUid();
                                getUserDataReference = FirebaseDatabase.getInstance().getReference().child("applicants").child(current_user_Id);

                               // Map<String,String> map = new HashMap<>();
                              //  map.put("applicants_image",downUri.toString());


                                getUserDataReference.child("applicants_image").setValue(downUri.toString()).addOnCompleteListener(new OnCompleteListener<Void>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                         //   Picasso.get().load(downUri.toString()).into(settingDisplayProfileImage);
                                            Toast.makeText(AdmitCardActivity.this, "success", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            }
                            else {
                                Toast.makeText(AdmitCardActivity.this, "here Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
                {
                    Exception error = result.getError();
                }
            }

        }

  }
