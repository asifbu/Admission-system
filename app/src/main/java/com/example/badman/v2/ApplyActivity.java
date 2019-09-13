package com.example.badman.v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ApplyActivity extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar mToolbar;

    private Spinner hscGroup ,sscGroup;
    private EditText hscRoll ,sscRoll;
    private EditText hscYear ,sscYear;
    private EditText hscBoard ,sscBoard;
    private Button clickButton;

    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultDataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        mToolbar=findViewById(R.id.apply_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Apply Here");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hscGroup = findViewById(R.id.apply_group_spinner);
        sscGroup = findViewById(R.id.ssc_spinner);
/*
        List<String> list = new ArrayList<String>();
        list.add("Science");
        list.add("Arts");
        list.add("Commarce");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        applyGroupSppiner.setAdapter(dataAdapter);
        applyGroupSppiner.setOnItemSelectedListener(this);

*/
        mAuth = FirebaseAuth.getInstance();
        hscRoll = findViewById(R.id.apply_hsc_roll);
        hscYear= findViewById(R.id.apply_year);
        hscBoard = findViewById(R.id.hsc_board);

        sscRoll = findViewById(R.id.ssc_roll);
        sscYear= findViewById(R.id.ssc_year);
        sscBoard = findViewById(R.id.ssc_board);

        clickButton = findViewById(R.id.click_button);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String HscRoll = hscRoll.getText().toString();
                final String HscYear = hscYear.getText().toString();
                final String HscGroup = hscGroup.getSelectedItem().toString();
                final String HscBoard = hscBoard.getText().toString();

                final String SscRoll = sscRoll.getText().toString();
                final String SscYear = sscYear.getText().toString();
                final String SscGroup = sscGroup.getSelectedItem().toString();
                final String SscBoard = sscBoard.getText().toString();


                sendValue(HscRoll,HscYear,HscGroup,HscBoard,SscRoll,SscYear,SscGroup,SscBoard);

                Intent applySecondIntent = new Intent(ApplyActivity.this,Apply2Activity.class);
                startActivity(applySecondIntent);

            }
        });



    }

    private void sendValue(String hscRoll, String hscYear, String hscGroup, String hscBoard, String sscRoll, String sscYear, String sscGroup, String sscBoard)
    {
        String current_user_Id = mAuth.getCurrentUser().getUid();
        storeUserDefaultDataReference = FirebaseDatabase.getInstance().getReference().child("applicants").child(current_user_Id);

        storeUserDefaultDataReference.child("hsc_roll").setValue(hscRoll);
        storeUserDefaultDataReference.child("hsc_year").setValue(hscYear);
        storeUserDefaultDataReference.child("hsc_group").setValue(hscGroup);
        storeUserDefaultDataReference.child("hsc_board").setValue(hscBoard);

        storeUserDefaultDataReference.child("ssc_roll").setValue(sscRoll);
        storeUserDefaultDataReference.child("ssc_year").setValue(sscYear);
        storeUserDefaultDataReference.child("ssc_group").setValue(sscGroup);
        storeUserDefaultDataReference.child("ssc_board").setValue(sscBoard);
    }


/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String text = parent.getItemAtPosition(position).toString();

        a= text;
        TextView priceTextView = (TextView) findViewById(R.id.show_text);
        priceTextView.setText(text);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
*/

}
