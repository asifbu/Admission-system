package com.example.badman.v2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NoticeActivity extends AppCompatActivity {

    private Button selectFile,upload,fetch;
    private TextView notification;
    Uri pdfUri;
    ProgressDialog progressDialog;

    FirebaseDatabase database;
    FirebaseStorage storage;
    private DatabaseReference getUserDataReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

//        PDFView pdfView = findViewById(R.id.pdfView) ;
//        pdfView.fromAsset("bu.pdf").load();

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        selectFile = findViewById(R.id.select_file);
        notification = findViewById(R.id.notification);
        upload = findViewById(R.id.upload);
        fetch = findViewById(R.id.fetch);

        fetch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(NoticeActivity.this,MyRecyclarView.class));
            }
        });

        selectFile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ContextCompat.checkSelfPermission(NoticeActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else
                {
                    ActivityCompat.requestPermissions(NoticeActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });



        upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (pdfUri != null)
                {
                    uploadfile(pdfUri);
                }
                else 
                {
                    Toast.makeText(NoticeActivity.this, "select a file", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadfile(Uri pdfUri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis() + ".pdf";
        final String RfileName = System.currentTimeMillis() + "";
        StorageReference storageReference = storage.getReference().child("uploads");


        //storageReference.child("uploads").child(fileName).putFile(pdfUri).addOnSuccessListen
        final StorageReference filepath = storageReference.child(fileName);
        filepath.putFile(pdfUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {

            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    final Uri downUri = task.getResult();


                    // String current_user_Id = mAuth.getCurrentUser().getUid();
                    getUserDataReference = FirebaseDatabase.getInstance().getReference().child("notice");

                    // Map<String,String> map = new HashMap<>();
                    //  map.put("applicants_image",downUri.toString());


                    getUserDataReference.child(RfileName).setValue(downUri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //   Picasso.get().load(downUri.toString()).into(settingDisplayProfileImage);
                                Toast.makeText(NoticeActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } else {
                    Toast.makeText(NoticeActivity.this, "here Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NoticeActivity.this, "File not successfully uploaded", Toast.LENGTH_SHORT).show();

            }
        });
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                progressDialog.setProgress(currentProgress);
//            }
//        });

    }

      ////end........


//        storageReference.child("uploads").child(fileName).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
//        {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//            {
//                String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
//                DatabaseReference reference = database.getReference().child("notice");
//                reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>()
//                {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task)
//                    {
//                        if (task.isSuccessful())
//                        {
//                            Toast.makeText(NoticeActivity.this, "File successfully uploaded", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                            Toast.makeText(NoticeActivity.this, "File not successfully uploaded", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener()
//        {
//            @Override
//            public void onFailure(@NonNull Exception e)
//            {
//                Toast.makeText(NoticeActivity.this, "File not successfully uploaded", Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
//        {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
//            {
//                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                progressDialog.setProgress(currentProgress);
//            }
//        });
//
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else 
        {
            Toast.makeText(NoticeActivity.this, "please provide premission", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode == 86 && resultCode == RESULT_OK && data != null)
        {
            pdfUri = data.getData();
            notification.setText("A file is selected : "+ data.getData().getLastPathSegment());
        }
        else
        {
            Toast.makeText( NoticeActivity.this, "please select a file", Toast.LENGTH_SHORT).show();
        }
    }
}
