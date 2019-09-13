package com.example.badman.v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Apply3Activity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;

    private CheckBox dUnit;
    private Button UnitSubmitButton;
    private Spinner UnitSpinner;

    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultDataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply3);

        mToolbar=findViewById(R.id.apply3_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Select Unit");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        UnitSpinner = findViewById(R.id.select_unit_spinner);
        dUnit = findViewById(R.id.d_unit);
        UnitSubmitButton = findViewById(R.id.unit_submit_button);

        mAuth = FirebaseAuth.getInstance();


        UnitSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String unitspinner = UnitSpinner.getSelectedItem().toString();
                final String dunit = dUnit.getText().toString();


                sendValue(unitspinner,dunit);

                Intent viewInfoIntent = new Intent(Apply3Activity.this,ViewInfoActivity.class);
                startActivity(viewInfoIntent);


            }
        });



    }

    private void sendValue(String unitspinner, String dunit) {

        String current_user_Id = mAuth.getCurrentUser().getUid();
        storeUserDefaultDataReference = FirebaseDatabase.getInstance().getReference().child("personal_info").child(current_user_Id);

        storeUserDefaultDataReference.child("main_unit").setValue(unitspinner);
        storeUserDefaultDataReference.child("other_unit").setValue(dunit);

    }


}
