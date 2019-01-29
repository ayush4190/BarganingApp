package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ShopperRegistration extends AppCompatActivity implements Serializable {
    AlertDialog.Builder builder2;
    private static final String TAG = "ShopperRegistration";
    private ShopperProfile obj;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    EditText firstname,lastname, phno ,email;
    TextView dob ;

    String emailv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_registration);

        final CheckBox rb2= (CheckBox) findViewById(R.id.rb);

        rb2.setChecked(!rb2.isChecked());
        TextView term1 = (TextView)findViewById(R.id.terms);

        term1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {


                builder2 = new AlertDialog.Builder(ShopperRegistration.this);

                LayoutInflater inflater = ShopperRegistration.this.getLayoutInflater();
                builder2.setView(inflater.inflate(R.layout.terms, null))
                        // Add action buttons

                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...
                                //radio
                                CheckBox rb= (CheckBox) findViewById(R.id.rb);
                                rb.setChecked(true);


                            }
                        })
                        .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CheckBox rb= (CheckBox) findViewById(R.id.rb);
                                if(rb.isChecked())
                                    rb.setChecked(!rb.isChecked());
                                dialog.cancel();
                            }
                        });


                //Setting message manually and performing action on button click
                builder2.setCancelable(false);

                //Creating dialog box
                AlertDialog alert = builder2.create();

                alert.show();

            }




            //

        });
        mDisplayDate = (TextView) findViewById(R.id.dob);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ShopperRegistration.this,
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



        Button nextButton = (Button) findViewById(R.id.next1);

        // Capture button clicks
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 firstname = (EditText) findViewById(R.id.firstname);
                 lastname = (EditText) findViewById(R.id.lastname);
                 phno = (EditText) findViewById(R.id.phone);
                dob =(TextView) findViewById(R.id.dob);
                 email = (EditText) findViewById(R.id.email);
                 emailv = email.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                int k=5;
                if( TextUtils.isEmpty(firstname.getText())){
                    k--;

                    firstname.setError( "First name is required!" );

                } if( TextUtils.isEmpty(lastname.getText())){

                    k--;
                    lastname.setError( "Last name is required!" );

                }
                if( TextUtils.isEmpty(dob.getText())) {

                    k--;
                    Toast.makeText(ShopperRegistration.this, "Date of Birth is Required!", Toast.LENGTH_SHORT).show();

                }
                if( TextUtils.isEmpty(phno.getText()) || phno.length()!=10) {

                    k--;
                    phno.setError("Invalid Phone number");

                }
                if (!(emailv.matches(emailPattern)))
                {
                    k--;
                    email.setError("Invalid Email-Id");
                    // Toast.makeText(getApplicationContext(),"invalid email address",Toast.LENGTH_SHORT).show();
                }

                if(k==5 && rb2.isChecked()){
                    assign ();

                    Intent myIntent = new Intent(ShopperRegistration.this,
                            ShopperForm.class);
                    myIntent.putExtra("profile", obj);
                    startActivity(myIntent);
                }

            }

        });


    }
    private void assign()
    {
        String fname , lmane, meamil,mphone,mdob;
        fname =firstname.getText().toString().trim();
        lmane = lastname.getText().toString().trim();
        meamil = emailv;
        mphone = phno.getText().toString().trim();
        mdob = dob.getText().toString().trim();
        obj = new ShopperProfile(fname,lmane,meamil,mphone,mdob);
    }
}
