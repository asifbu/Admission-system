package com.example.badman.v2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeatPlanActivity extends AppCompatActivity {


    private TextView showText;
    private Button seatShowButton;
    private EditText getRoll;

    private FirebaseAuth mAuth;
    private DatabaseReference getUserDataReference;
    private DatabaseReference getUserData;
    private int n=4;
    private int i;
    String roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_plan);


        showText = findViewById(R.id.show_room);
        getRoll = findViewById(R.id.get_roll_for_seat);
        seatShowButton = findViewById(R.id.seat_show_button);


        mAuth = FirebaseAuth.getInstance();




        seatShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  roll = getRoll.getText().toString();

                    int Roll = Integer.parseInt(roll);


                    for (int i =1 ;i<=4;i++)
                    {
                        String online_user_id = String.valueOf(i);


                        getUserDataReference = FirebaseDatabase.getInstance().getReference().child("seatplan").child(online_user_id);
                        getUserData = FirebaseDatabase.getInstance().getReference().child("seatplan").child(online_user_id);



                        getUserDataReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                String startnumber = dataSnapshot.child("startRoll").getValue().toString();
                                String Room = dataSnapshot.child("room").getValue().toString();
                                String endnumber = dataSnapshot.child("endRoll").getValue().toString();
                                //  String otherunit = dataSnapshot.child("other_unit").getValue().toString();

                                int Roll = Integer.parseInt(roll);
                                int Startnumber = Integer.parseInt(startnumber);
                                int Endnumber = Integer.parseInt(endnumber);
                                if (Roll>=Startnumber && Roll<=Endnumber)
                                {
                                    showText.setText(Room);
                                }

//                            showText2.setText(year);
                                //displayMainUnit.setText(mainunit);
                                // displayOtherUnit.setText(otherunit);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }







            }
        });





    }
}
