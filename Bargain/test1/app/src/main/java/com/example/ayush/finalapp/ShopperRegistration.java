package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopperRegistration extends AppCompatActivity implements Serializable {
    SessionManagment session;

    private FirebaseAuth mAuth;

    private FirebaseUser mUser;

    String userp, passwordv;
    TextInputEditText firstname_q ;
    TextInputEditText lastname_q ;
    TextInputEditText email_q ;
    TextInputEditText username_q;
    TextInputEditText confirmpassword_q ;
    TextInputEditText password_q;

    TextInputLayout firstname_w ;
    TextInputLayout lastname_w ;
    TextInputLayout email_w ;
    TextInputLayout username_w;
    TextInputLayout confirmpassword_w;
    TextInputLayout password_w;
    String emailv;

    ShopperProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_shopper_registration);

        mAuth = FirebaseAuth.getInstance ();

        mUser = mAuth.getCurrentUser ();

        Button nextButton = (Button) findViewById (R.id.next1);
        firstname_q = findViewById(R.id.firstname);
        lastname_q = findViewById(R.id.lastname);
        email_q =  findViewById(R.id.email);
        username_q =  findViewById(R.id.username);
        confirmpassword_q =  findViewById(R.id.confirm_password);
        password_q =  findViewById(R.id.password);

        firstname_w = findViewById(R.id.firstname_up);
        lastname_w = findViewById(R.id.lastname_up);
        email_w =  findViewById(R.id.email_up);
        username_w =  findViewById(R.id.username_up);
        confirmpassword_w =  findViewById(R.id.confirm_password_up);
        password_w =  findViewById(R.id.password_up);
        // Capture button clicks
        nextButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                userp = username_q.getText ().toString ().trim ();
                final String userv = "^[a-zA-Z0-9]+([_.a-zA-Z0-9])*$";

                Pattern userx = Pattern.compile (userv);
                Matcher matcher2 = userx.matcher (userp);

                passwordv = password_q.getText ().toString ().trim ();

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                Pattern passwordp = Pattern.compile (PASSWORD_PATTERN);
                Matcher matcher = passwordp.matcher (passwordv);

                emailv = email_q.getText ().toString ().trim ();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                int k = 6;
                if (TextUtils.isEmpty (firstname_q.getText ())) {
                    k--;

                    firstname_w.setError ("First name is required!");
                } else
                    firstname_w.setErrorEnabled(false);


                if (TextUtils.isEmpty (lastname_q.getText ())) {

                    k--;
                    lastname_w.setError ("Last name is required!");

                } else
                    lastname_w.setErrorEnabled(false);


                if (!matcher2.matches ()) {
                    k--;
                    username_w.setError ("Invalid Username");

                } else
                    username_w.setErrorEnabled(false);


                if (!matcher.matches ()) {
                    k--;
                    password_w.setError ("Invalid Password");
                } else
                    password_w.setErrorEnabled(false);

                if (!confirmpassword_q.getText ().toString ().equals (password_q.getText ().toString ()) || confirmpassword_q.getText ().toString ().isEmpty ()) {
                    k--;
                    confirmpassword_w.setError ("Passwords are not same");
                } else
                    confirmpassword_w.setErrorEnabled(false);



                if (!(emailv.matches (emailPattern))) {
                    k--;
                    email_w.setError ("Invalid Email-Id");
                    // Toast.makeText(getApplicationContext(),"invalid email address",Toast.LENGTH_SHORT).show();
                } else
                    email_w.setErrorEnabled(false);


                if (k == 6) {
//
//
                    shopperdetails ();


                }

            }

        });

    }

    private void shopperdetails()

    {

        profile = new ShopperProfile ();
        profile.setEmail (email_q.getText ().toString ().trim ());
        profile.setFname (firstname_q.getText ().toString ().trim ());
        profile.setLname (lastname_q.getText ().toString ().trim ());
        profile.setUsername (username_q.getText ().toString ().trim ());
        session = new SessionManagment (getApplicationContext ());


        mAuth.createUserWithEmailAndPassword (emailv,passwordv).addOnCompleteListener (new OnCompleteListener <AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful ())
                {
                    Toast.makeText (ShopperRegistration.this,"Registration completed",Toast.LENGTH_SHORT).show ();
                    session.createLoginSession ("shopper",emailv);
                   Intent myIntent = new Intent (ShopperRegistration.this,
                            ShopperForm.class);
                    myIntent.putExtra ("profile", profile);
                    startActivity (myIntent);
                }
                if(!task.isSuccessful ())
                {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException ();
                    Toast.makeText (ShopperRegistration.this," " + e.getMessage(),Toast.LENGTH_SHORT).show ();
                }
            }
        });




    }
}
