package com.example.badman.v2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class resultActivity extends AppCompatActivity {


    private TextView showText,showText1,showText2;
    private Button resultShowButton;
    private EditText getResult;

    private FirebaseAuth mAuth;
    private DatabaseReference getUserDataReference;
    private DatabaseReference getUserData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        showText = findViewById(R.id.parse_date);
        showText1 = findViewById(R.id.parse_date1);
        showText2 = findViewById(R.id.parse_date2);
        getResult = findViewById(R.id.get_editText);
        resultShowButton = findViewById(R.id.result_show_button);


        mAuth = FirebaseAuth.getInstance();



        resultShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String roll = getResult.getText().toString();

                String online_user_id = roll;
                getUserDataReference = FirebaseDatabase.getInstance().getReference().child("result").child((online_user_id));
                getUserData = FirebaseDatabase.getInstance().getReference().child("result").child(online_user_id);

                getUserDataReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        String number = dataSnapshot.child("number").getValue().toString();
                        String sgpa = dataSnapshot.child("sgpa").getValue().toString();
                        String year = dataSnapshot.child("year").getValue().toString();
                        //  String otherunit = dataSnapshot.child("other_unit").getValue().toString();


                        showText.setText(number);
                        showText1.setText(sgpa);
                        showText2.setText(year);
                        //displayMainUnit.setText(mainunit);
                        // displayOtherUnit.setText(otherunit);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }

}