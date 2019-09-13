package com.example.badman.v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Apply2Activity extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar mToolbar;
    private EditText userName,userNumber,userQuata,sscGpa,hscGpa;
    private Button submitButton;

    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultDataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply2);

        mToolbar=findViewById(R.id.apply2_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("parsonal Info");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true)


        submitButton = findViewById(R.id.submit_button);
        userName = findViewById(R.id.user_name);
        userNumber = findViewById(R.id.user_number);
        userQuata = findViewById(R.id.user_quata);
        sscGpa =findViewById(R.id.ssc_gpa);
        hscGpa =findViewById(R.id.hsc_gpa);

        mAuth = FirebaseAuth.getInstance();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = userName.getText().toString();
                final String number = userNumber.getText().toString();
                final String quata = userQuata.getText().toString();
                final String hsc_gpa = hscGpa.getText().toString();

                final String ssc_gpa = sscGpa.getText().toString();


                sendValue(name,number,quata,hsc_gpa,ssc_gpa);


                Intent applyThirdIntent = new Intent(Apply2Activity.this,Apply3Activity.class);
                startActivity(applyThirdIntent);

            }
        });



    }

    private void sendValue(String userName, String userNumber, String userQuata, String sscGpa, String hscGpa)
    {
        String current_user_Id = mAuth.getCurrentUser().getUid();
        storeUserDefaultDataReference = FirebaseDatabase.getInstance().getReference().child("applicants").child(current_user_Id);

        storeUserDefaultDataReference.child("applicant_name").setValue(userName);
        storeUserDefaultDataReference.child("applicant_number").setValue(userNumber);
        storeUserDefaultDataReference.child("applicant_quata").setValue(userQuata);
        storeUserDefaultDataReference.child("ssc_gpa").setValue(sscGpa);

        storeUserDefaultDataReference.child("hsc_gpa").setValue(hscGpa);



    }
}
