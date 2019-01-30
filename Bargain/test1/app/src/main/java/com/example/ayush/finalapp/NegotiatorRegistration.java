package com.example.ayush.finalapp;

import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class NegotiatorRegistration extends AppCompatActivity {
    //1c2
    // private String email;
    private EditText password;
    //1
    private EditText firstname;

    private EditText lastname;

    private EditText email;

    private EditText username;

    private static final String TAG = "NegotiatorRegistration";
    //idk
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    ///




    //idk

    //idk
   //firebase user to get the unique user id.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negotiator_registration);
        //idk
       //firebase authentication variable


         firebaseAuth = FirebaseAuth.getInstance();

         // firebase user reference
        user = firebaseAuth.getCurrentUser ();
        if(user != null)
        {
            //// start the new activity this page wont open

        }


        //1
        Button nextButton = (Button) findViewById(R.id.next1);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //1c2
                password = (EditText) findViewById(R.id.password);
                EditText confirmpassword = (EditText) findViewById(R.id.confirm_password);

                 username = (EditText) findViewById(R.id.username);

                //1
                firstname = (EditText) findViewById(R.id.firstname);
                lastname = (EditText) findViewById(R.id.lastname);
                email = (EditText) findViewById(R.id.email);
                String emailv = email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                //1c2
                //username
                String userp=username.getText().toString().trim();
                final String userv = "^[a-zA-Z0-9]+([_.a-zA-Z0-9])*$";

                Pattern userx= Pattern.compile(userv);
                Matcher matcher2 = userx.matcher(userp);

                String passwordv = password.getText().toString().trim();

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                Pattern passwordp= Pattern.compile(PASSWORD_PATTERN);
                Matcher matcher = passwordp.matcher(passwordv);

                //1
                int k=6;
                //1
                //1c2
                if(!matcher2.matches()) {
                    k--;
                    username.setError("Invalid Username");

                }
                //1c2
                if(!matcher.matches()) {
                    k--;
                    password.setError("Invalid Password");

                }
                //1c2
                if(!confirmpassword.getText().toString().equals(password.getText().toString())){
                    k--;
                    confirmpassword.setError("Passwords are not same");
                }

                if( TextUtils.isEmpty(firstname.getText())){
                    k--;
                    firstname.setError( "First name is required!" );
                }
                //1
                if( TextUtils.isEmpty(lastname.getText())){

                    k--;
                    lastname.setError( "Last name is required!" );

                }
                //1
                if (!(emailv.matches(emailPattern)))
                {
                    k--;
                    email.setError("Invalid Email-Id");
                    // Toast.makeText(getApplicationContext(),"invalid email address",Toast.LENGTH_SHORT).show();
                }
                if(k==6){
                   registeruser ();
                        ///// call this intent after the email verification is done
                    finish();
                    startActivity (new Intent (NegotiatorRegistration.this,NegotiatorForm.class));


                }
            }//corr

        });
    }
    //idk

    ///// function to register user and sen a email verification link to his email



    private void registeruser() {

        NegotiatorProfile profile = new NegotiatorProfile ();
        profile.setFname (firstname.getText ().toString ().trim ());
        profile.setLname (lastname.getText ().toString ().trim ());
        profile.setMemail (email.getText ().toString ().trim ());
        profile.setUsername (username.getText ().toString ().trim ());
        profile.setMpassword (password.getText ().toString ().trim ());

        firebaseAuth.createUserWithEmailAndPassword (profile.getMemail (), profile.getMpassword ()).addOnCompleteListener (new OnCompleteListener <AuthResult> () {
            @Override
            public void onComplete(@NonNull Task <AuthResult> task) {
                if (task.isSuccessful ()) {
                    Toast.makeText (NegotiatorRegistration.this, "i am here", Toast.LENGTH_SHORT).show ();



                    ///// some error in email verification
                 ////// check for email verification once
                    //   user.sendEmailVerification ();
                    /* user.sendEmailVerification ().addOnCompleteListener (new OnCompleteListener <Void> () {
                        @Override
                        public void onComplete(@NonNull Task <Void> task) {
                            Toast.makeText (NegotiatorRegistration.this, "Verification link is send to your email id", Toast.LENGTH_SHORT).show ();

                        }
                    });*/

                }
                if(!task.isSuccessful()){
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(NegotiatorRegistration.this, "Failed Registration: "+e.getMessage (), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }







}
