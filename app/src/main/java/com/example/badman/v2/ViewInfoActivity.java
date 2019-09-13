package com.example.badman.v2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewInfoActivity extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar mToolbar;

    private TextView displayName,displayMainUnit;
    private TextView displayMobile,displayOtherUnit,displayEmail,Bill_payment;
    private Button Bill_id_button;


    private FirebaseAuth mAuth;
    private DatabaseReference getUserDataReference;
    private DatabaseReference getUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);


        mToolbar=findViewById(R.id.view_info_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("View Information");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        String online_user_id = mAuth.getCurrentUser().getUid();
        getUserDataReference = FirebaseDatabase.getInstance().getReference().child("applicants").child(online_user_id);
        getUserData = FirebaseDatabase.getInstance().getReference().child("personal_info").child(online_user_id);


        displayName = findViewById(R.id.view_name);
        displayMobile = findViewById(R.id.aaa);
        displayMainUnit = findViewById(R.id.aaaa);
        displayOtherUnit = findViewById(R.id.aaaaa);
        displayEmail = findViewById(R.id.view_email_show);

        Bill_payment = findViewById(R.id.bill_id);
        Bill_id_button = findViewById(R.id.bill_payment_button);

        getUserDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String name = dataSnapshot.child("applicant_name").getValue().toString();
                String mobile = dataSnapshot.child("applicant_number").getValue().toString();
                String email = dataSnapshot.child("applicant_email").getValue().toString();
              //  String otherunit = dataSnapshot.child("other_unit").getValue().toString();


                displayName.setText(name);
                displayMobile.setText(mobile);
                displayEmail.setText(email);
                //displayMainUnit.setText(mainunit);
               // displayOtherUnit.setText(otherunit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Bill_id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String current_user_Id = mAuth.getCurrentUser().getUid();
                Bill_payment.setText(current_user_Id);


            }
        });


   // /*
       getUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //String name = dataSnapshot.child("applicant_name").getValue().toString();
                // String mobile = dataSnapshot.child("applicant_number").getValue().toString();
                String mainunit = dataSnapshot.child("main_unit").getValue().toString();
                String otherunit = dataSnapshot.child("other_unit").getValue().toString();


               // displayName.setText(name);
                //  displayMobile.setText(mobile);
                  displayMainUnit.setText(mainunit);
                  displayOtherUnit.setText(otherunit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
     //   */

    }
}
