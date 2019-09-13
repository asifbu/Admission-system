package com.example.badman.v2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity
{

    private android.support.v7.widget.Toolbar mToolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultDataReference;


    private EditText RegisterUserEmail;
    private EditText RegisterUserPassword;
    private Button CreateAccountButton,loginButton;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

// For app bar and back button...

      //  mToolbar = findViewById(R.id.register_toolbar);
      //  setSupportActionBar(mToolbar);
      //  getSupportActionBar().setTitle("Sign Up");
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        RegisterUserEmail = findViewById(R.id.register_email);
        RegisterUserPassword = findViewById(R.id.register_password);
        CreateAccountButton = findViewById(R.id.create_account_button);
        loginButton = findViewById(R.id.go_login_button);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

//passing data in Firebase ....
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = RegisterUserEmail.getText().toString();
                String password = RegisterUserPassword.getText().toString();

                RegisterAccount();
                //email,password

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();

            }
        });
    }


    private void RegisterAccount()

    {
        //String email, String password
        final String email = RegisterUserEmail.getText().toString().trim();
        String password = RegisterUserPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(RegisterActivity.this,"please Enter your email.",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(RegisterActivity.this,"please Enter your password.",Toast.LENGTH_LONG).show();
        }
        else
        {

            loadingBar.setTitle("creating new account");
            loadingBar.setMessage("Please wait, while we are creating account for you.");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        String current_user_Id = mAuth.getCurrentUser().getUid();
                        storeUserDefaultDataReference = FirebaseDatabase.getInstance().getReference().child("applicants").child(current_user_Id);

                        storeUserDefaultDataReference.child("applicant_email").setValue(email);
                      //  storeUserDefaultDataReference.child("user_status").setValue("hey there ,");
                       // storeUserDefaultDataReference.child("applicants_image").setValue("default_profile");
                      //  storeUserDefaultDataReference.child("user_thumb_image").setValue("user_image").addOnCompleteListener(new OnCompleteListener<Void>() {
                       //     @Override
                         //   public void onComplete(@NonNull Task<Void> task) {

                             //   if (task.isSuccessful())
                             //   {
                                    Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    finish();
                               // }
                          //  }
                      //  });

                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,"Error Occured ,try again",Toast.LENGTH_SHORT).show();
                    }
                    loadingBar.dismiss();
                }
            });
        }


    }

}
