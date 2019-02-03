package com.example.ayush.finalapp;

import android.app.DatePickerDialog;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.app.Activity;

import android.os.StrictMode;

import android.text.TextUtils;
import android.util.Log;

import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class NegotiatorForm extends Activity {

    //idk
    FirebaseAuth firebaseAuth;

    ////Firebase user object gor getting user id

    FirebaseUser mUser;


    //2c1
    private EditText phno,ad1 , ad2, city, state, pincode;
    TextView dob ;

    private String s1,s2,s3,hour1="0",hour2="0",hour3="0";

    private String hours;
    //3
    ImageView viewImage;

    //3
    private Button b;



    private String p;

    //// object of negotiatordetails class

    NegotiatorDetails details;


    //2c1
    private TextView mDisplayDate;
    //2c1
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    /// declaring tag
    private static final String TAG = "NegotiatorForm";


    //idk
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //idk
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder ();

        StrictMode.setVmPolicy (builder.build ());
        //2
        super.onCreate (savedInstanceState);
        //2
        setContentView (R.layout.activity_negotiator_form);

        //idk

       final CheckBox box1=(CheckBox) findViewById(R.id.box1);
        final CheckBox box2=(CheckBox) findViewById(R.id.box2);
        final CheckBox box3=(CheckBox) findViewById(R.id.box3);

        //idk
        firebaseAuth = FirebaseAuth.getInstance ();

        mUser = firebaseAuth.getCurrentUser ();

        //2c1
        //2c1
        //  mDisplayDate = (TextView) findViewById (R.id.dob);

        //2c1
        //2



        //2c1
        //2c1

        mDisplayDate = (TextView) findViewById (R.id.dob);

        //2c1
        mDisplayDate.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance ();
                int year = cal.get (Calendar.YEAR);
                int month = cal.get (Calendar.MONTH);
                int day = cal.get (Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog (
                        NegotiatorForm.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);

                dialog.getDatePicker ().setMaxDate (new Date ().getTime ());
                dialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
                dialog.show ();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d (TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText (date);
            }
            };



            //2
            final Spinner mySpinner = (Spinner) findViewById (R.id.gender);
            ArrayAdapter <String> myAdapter = new ArrayAdapter <String> (NegotiatorForm.this,
                    android.R.layout.simple_list_item_1, getResources ().getStringArray (R.array.names)) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView (position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        // Set the hint text color gray
                        tv.setTextColor (Color.GRAY);
                    } else {
                        tv.setTextColor (Color.BLACK);
                    }
                    return view;
                }

            };
                        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mySpinner.setAdapter(myAdapter);

            Button nextButton = (Button) findViewById (R.id.next1);
            //2c1

            final Spinner mySpinner1 = (Spinner) findViewById (R.id.area1);
            final Spinner mySpinner2 = (Spinner) findViewById (R.id.area2);
            final Spinner mySpinner3 = (Spinner) findViewById (R.id.area3);

            ArrayAdapter <String> myAdapter2 = new ArrayAdapter <String> (NegotiatorForm.this,
                    android.R.layout.simple_list_item_1, getResources ().getStringArray (R.array.cat)) {

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView (position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0 || (mySpinner1.getSelectedItemPosition () == position) || (mySpinner2.getSelectedItemPosition () == position) || (mySpinner3.getSelectedItemPosition () == position)) {
                        // Set the hint text color gray

                        tv.setTextColor (Color.GRAY);
                    } else {
                        tv.setTextColor (Color.BLACK);
                    }
                    return view;
                }

            };



        ///////////////////////////

                myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mySpinner1.setAdapter(myAdapter2);
                mySpinner2.setAdapter(myAdapter2);
                mySpinner3.setAdapter(myAdapter2);
            ////////////
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mySpinner.setAdapter(myAdapter);

            // Capture button clicks
                nextButton.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                //idk

                //3
                ImageView propic = (ImageView) findViewById (R.id.profilepic);
                //2
                phno = (EditText) findViewById (R.id.phone);
                ad1 = (EditText) findViewById (R.id.adlin1);
                ad2 = (EditText) findViewById (R.id.adline2);
                city = (EditText) findViewById (R.id.adcity);
                state = (EditText) findViewById (R.id.adstate);
                pincode = (EditText) findViewById (R.id.adpincode);
                dob = (TextView) findViewById (R.id.dob);

                if(box1.isChecked())
                    hour1="1";
                    if(box2.isChecked())
                        hour2="1";
                    if(box3.isChecked())
                        hour3="1";

                s1 = mySpinner1.getSelectedItem ().toString ().trim ();// Stored value is here
                s2 = mySpinner2.getSelectedItem ().toString ().trim ();
                s3 = mySpinner3.getSelectedItem ().toString ().trim ();

                int k = 10;

                //2
                if (s1.equals ("Select Category") && s2.equals ("Select Category") && s3.equals ("Select Category")) {
                    k--;
                    Toast.makeText (NegotiatorForm.this, "not all can be empty", Toast.LENGTH_SHORT).show ();
                }

                if (TextUtils.isEmpty (dob.getText ())) {

                    k--;
                    Toast.makeText (NegotiatorForm.this, "Date of Birth is Required!", Toast.LENGTH_SHORT).show ();

                }
                //2
                if (TextUtils.isEmpty (phno.getText ()) || phno.length () != 10) {

                    k--;
                    phno.setError ("Invalid Phone number");

                }
                //2
                if (TextUtils.isEmpty (ad1.getText ())) {

                    k--;
                    ad1.setError ("Full address is required!");
                }

                //2
                if (TextUtils.isEmpty (ad2.getText ())) {

                    k--;
                    ad2.setError ("Full address is required!");
                }
                //2
                if (TextUtils.isEmpty (city.getText ())) {

                    k--;
                    city.setError ("City is required!");
                }
                //2
                if (TextUtils.isEmpty (state.getText ())) {

                    k--;
                    state.setError ("State is required!");
                }
                //2
                if (TextUtils.isEmpty (pincode.getText ()) || pincode.length () != 6) {
                    k--;

                    pincode.setError ("Invalid pincode!");
                }
                //2
                if (mySpinner.getSelectedItem ().toString ().trim ().equals ("Select Gender")) {
                    k--;
                    Toast.makeText (NegotiatorForm.this, "Gender is Required!", Toast.LENGTH_SHORT).show ();
                }
                //3
                       /* if (bmap.sameAs (myLogo)) {
                            k--;
                            Toast.makeText (NegotiatorForm.this, "Add Profile Picture", Toast.LENGTH_SHORT).show ();

                        }*/
                       //hours
                    if ( (box1.isChecked() || box2.isChecked() || box3.isChecked())) {
                        k--;
                        Toast.makeText (NegotiatorForm.this, "Enter at least one working hour", Toast.LENGTH_SHORT).show ();
                    }

                    if (k == 10) {
                    negotiatordetails ();

                    Intent intent = new Intent (NegotiatorForm.this, NegotiatorId.class);
                   startActivity (intent);



                }


            }

            });

        }





    private void negotiatordetails()
    {
        details = new NegotiatorDetails ();
        details.setAddress (ad1.getText ().toString ().trim () + ad2.getText ().toString ().trim ());
        details.setCity (city.getText ().toString ().trim ());
        details.setState (state.getText ().toString ().trim ());
        details.setPincode (pincode.getText ().toString ().trim ());
        details.setDob (dob.getText ().toString ().trim ());
        details.setPhone (phno.getText ().toString ().trim ());
        details.setCategory1 (s1);
        details.setWorkhour1(hour1);
        details.setWorkhour2(hour2);
        details.setWorkhour3(hour3);
        details.setCategory2(s2);
        details.setCategory3 (s3);
        details.setBl ("true");
        DatabaseReference mroot = FirebaseDatabase.getInstance ().getReference ();
        Task <Void> databaseReference = mroot.child ("Negotiator").child (mUser.getUid ()).setValue (details);
        Toast.makeText (NegotiatorForm.this,"details entered",Toast.LENGTH_SHORT).show ();


    }
    }


