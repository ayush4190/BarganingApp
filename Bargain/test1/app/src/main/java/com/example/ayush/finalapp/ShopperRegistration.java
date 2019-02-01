package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    private FirebaseAuth mAuth;

    private FirebaseUser mUser;

    String userp, passwordv;

    EditText firstname,lastname,email;

    String emailv;

    ShopperProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_shopper_registration);

        mAuth = FirebaseAuth.getInstance ();

        mUser = mAuth.getCurrentUser ();

        Button nextButton = (Button) findViewById (R.id.next1);

        // Capture button clicks
        nextButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                EditText username=(EditText) findViewById (R.id.username);
                EditText confirmpassword=(EditText) findViewById (R.id.confirm_password);
                EditText password=(EditText) findViewById (R.id.password);


                userp = username.getText ().toString ().trim ();
                final String userv = "^[a-zA-Z0-9]+([_.a-zA-Z0-9])*$";

                Pattern userx = Pattern.compile (userv);
                Matcher matcher2 = userx.matcher (userp);

                passwordv = password.getText ().toString ().trim ();

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                Pattern passwordp = Pattern.compile (PASSWORD_PATTERN);
                Matcher matcher = passwordp.matcher (passwordv);


                firstname = (EditText) findViewById (R.id.firstname);
                lastname = (EditText) findViewById (R.id.lastname);
                email = (EditText) findViewById (R.id.email);
                emailv = email.getText ().toString ().trim ();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                int k = 6;
                if (TextUtils.isEmpty (firstname.getText ())) {
                    k--;

                    firstname.setError ("First name is required!");

                }
                if (TextUtils.isEmpty (lastname.getText ())) {

                    k--;
                    lastname.setError ("Last name is required!");

                }
                if (!matcher2.matches ()) {
                    k--;
                    username.setError ("Invalid Username");

                }
                if (!matcher.matches ()) {
                    k--;
                    password.setError ("Invalid Password");

                }
                if (!confirmpassword.getText ().toString ().equals (password.getText ().toString ())) {
                    k--;
                    confirmpassword.setError ("Passwords are not same");
                }

                if (!(emailv.matches (emailPattern))) {
                    k--;
                    email.setError ("Invalid Email-Id");
                    // Toast.makeText(getApplicationContext(),"invalid email address",Toast.LENGTH_SHORT).show();
                }

                if (k == 6) {
//
                    shopperdetails ();

                   /* Intent myIntent = new Intent (ShopperRegistration.this,
                           LoginPage.class);
                    myIntent.putExtra ("profile", profile);
                    startActivity (myIntent);*/
                }

            }

        });

    }

    private void shopperdetails()

    {

        profile = new ShopperProfile ();
        profile.setEmail (email.getText ().toString ().trim ());
        profile.setFname (firstname.getText ().toString ().trim ());
        profile.setLname (lastname.getText ().toString ().trim ());


        mAuth.createUserWithEmailAndPassword (emailv,passwordv).addOnCompleteListener (new OnCompleteListener <AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful ())
                {
                    Toast.makeText (ShopperRegistration.this,"Registration completed",Toast.LENGTH_SHORT).show ();
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
