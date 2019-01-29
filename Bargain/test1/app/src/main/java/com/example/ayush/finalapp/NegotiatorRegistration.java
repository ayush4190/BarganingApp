package com.example.ayush.finalapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class NegotiatorRegistration extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText firstname,lastname,phno,ad1 , ad2, city, state, pincode,email;
    TextView dob ;
    private DatabaseReference databaseReference , mroot;
    NegotiatorProfile profile;


    private static final String TAG = "NegotiatorRegistration";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    //EditText email = (EditText) findViewById(R.id.email);
    //CharSequence target= (CharSequence) findViewById(R.id.email);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negotiator_registration);
        mroot = FirebaseDatabase.getInstance().getReference();
       // firebaseAuth = FirebaseAuth.getInstance();

        mDisplayDate = (TextView) findViewById(R.id.dob);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NegotiatorRegistration.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);

                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        final Spinner mySpinner = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NegotiatorRegistration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names))
        {
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };


        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        Button nextButton = (Button) findViewById(R.id.next1);

        // Capture button clicks
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 firstname = (EditText) findViewById(R.id.firstname);
               lastname = (EditText) findViewById(R.id.lastname);
                 phno = (EditText) findViewById(R.id.phone);
                ad1 = (EditText) findViewById(R.id.adlin1);
                 ad2 = (EditText) findViewById(R.id.adline2);
                city = (EditText) findViewById(R.id.adcity);
               state = (EditText) findViewById(R.id.adstate);
                 pincode = (EditText) findViewById(R.id.adpincode);
                 dob =(TextView) findViewById(R.id.dob);
                 email = (EditText) findViewById(R.id.email);
                String emailv = email.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                int k=11;
                if( TextUtils.isEmpty(firstname.getText())){
                    k--;

                    firstname.setError( "First name is required!" );

                } if( TextUtils.isEmpty(lastname.getText())){

                    k--;
                    lastname.setError( "Last name is required!" );

                }
                if( TextUtils.isEmpty(dob.getText())) {

                    k--;
                    Toast.makeText(NegotiatorRegistration.this, "Date of Birth is Required!", Toast.LENGTH_SHORT).show();

                }
                if( TextUtils.isEmpty(phno.getText()) || phno.length()!=10) {

                    k--;
                    phno.setError("Invalid Phone number");

                } if( TextUtils.isEmpty(ad1.getText())) {

                    k--;
                    ad1.setError("Full address is required!");
                }

                if( TextUtils.isEmpty(ad2.getText())) {

                    k--;
                    ad2.setError("Full address is required!");
                }if( TextUtils.isEmpty(city.getText())) {

                    k--;
                    city.setError("City is required!");
                }
                if( TextUtils.isEmpty(state.getText())) {

                    k--;
                    state.setError("State is required!");
                }
                if( TextUtils.isEmpty(pincode.getText()) || pincode.length()!=6) {
                    k--;

                    pincode.setError("Invalid pincode!");
                }
                if (mySpinner.getSelectedItem().toString().trim().equals("Select Gender")) {
                    k--;
                    Toast.makeText(NegotiatorRegistration.this, "Gender is Required!", Toast.LENGTH_SHORT).show();
                }

                if (!(emailv.matches(emailPattern)))
                {
                    k--;
                    email.setError("Invalid Email-Id");
                    // Toast.makeText(getApplicationContext(),"invalid email address",Toast.LENGTH_SHORT).show();
                }

                if(k==11){
                   basicinfo();

                    Intent myIntent = new Intent(NegotiatorRegistration.this,
                            NegotiatorForm.class);
                    myIntent.putExtra("phon",profile.getPhno());
                    myIntent.putExtra("email",profile.getEmail());
                   // myIntent.putExtra("profile", (Parcelable) profile);
                    startActivity(myIntent);
                }

            }

        });


    }
    private void basicinfo()
    {
        String mfirstname,mlastname,mphno,mad1 , mad2, mcity, mstate, mpincode,memail,mdob ;

        mfirstname =firstname.getText().toString().trim();
        mlastname =lastname.getText().toString().trim();
        mad1 =ad1.getText().toString().trim();
        mad2 =ad2.getText().toString().trim();
        mphno =phno.getText().toString().trim();
        mcity=city.getText().toString().trim();
        mstate =state.getText().toString().trim();
        mpincode= pincode.getText().toString().trim();
        memail =email.getText().toString().trim();
        mdob =dob.getText().toString().trim();
        profile = new NegotiatorProfile(mfirstname,mlastname,mphno,mad1 , mad2, mcity, mstate, mpincode,memail,mdob);
        databaseReference= mroot.child("Negotiator");//setValue(profile);
       databaseReference.child(profile.getPhno()).child("Basic").setValue(profile);
        Toast.makeText(NegotiatorRegistration.this,"Profile Saved",Toast.LENGTH_LONG).show();


    }




}
