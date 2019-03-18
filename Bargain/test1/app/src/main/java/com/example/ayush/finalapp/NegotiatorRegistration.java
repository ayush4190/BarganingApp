package com.example.ayush.finalapp;

import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NegotiatorRegistration extends AppCompatActivity {
    //1c2
    // private String email;
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
    SessionManagment session;


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
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //1c2


                String messagePattern = "\n*.";
                String emailv = email_q.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                //1c2
                //username
                String userp=username_q.getText().toString().trim();
                final String userv = "^[a-zA-Z0-9]+([_.a-zA-Z0-9])*$";

                Pattern userx= Pattern.compile(userv);
                Matcher matcher2 = userx.matcher(userp);

                String passwordv = password_q.getText().toString().trim();

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                Pattern passwordp= Pattern.compile(PASSWORD_PATTERN);
                Matcher matcher = passwordp.matcher(passwordv);

                //1
                int k=6;
                //1
                //1c2
                if(!matcher2.matches()) {
                    k--;
                    // username_w.requestFocus();
                    username_w.setError("Invalid Username");
                }
                else
                    username_w.setErrorEnabled(false);
                //1c2
                if(!matcher.matches()) {

                    k--;

                    password_w.setError("Invalid Password");

                }
                else
                    password_w.setErrorEnabled(false);

                //1c2
                if(!confirmpassword_q.getText().toString().equals(password_q.getText().toString()) || confirmpassword_q.getText().toString().trim().isEmpty()){
                    k--;
                    confirmpassword_w.setError("Passwords are not same");
                }else
                    confirmpassword_w.setErrorEnabled(false);

                if( firstname_q.getText().toString().trim().isEmpty()){
                    k--;
                    firstname_w.setError( "First name is required!" );
                }else
                    firstname_w.setErrorEnabled(false);
                //1


                if(lastname_q.getText().toString().trim().isEmpty() ){

                    k--;
                    //firstname_q.setError("Last name is required!");
                    lastname_w.setError( "First name is required!" );

                }
                else
                    lastname_w.setErrorEnabled(false);

                //1
                if (!(emailv.matches(emailPattern)))
                {
                    k--;
                    email_w.setError("Invalid Email-Id");
                    // Toast.makeText(getApplicationContext(),"invalid email address",Toast.LENGTH_SHORT).show();
                }  else
                    email_w.setErrorEnabled(false);

                Log.v("fdgfdh",String.valueOf(k));
                if(k==6){
                        registeruser ();





                }
            }//corr

        });
    }
    //idk

    ///// function to register user and sen a email verification link to his email



    private void registeruser() {

        final NegotiatorProfile profile = new NegotiatorProfile ();
        profile.setFname (firstname_q.getText ().toString ().trim ());
        profile.setLname (lastname_q.getText ().toString ().trim ());
        profile.setMemail (email_q.getText ().toString ().trim ());
        profile.setUsername (username_q.getText ().toString ().trim ());
        profile.setMpassword (password_q.getText ().toString ().trim ());
        session = new SessionManagment (getApplicationContext ());

        firebaseAuth.createUserWithEmailAndPassword (profile.getMemail (), profile.getMpassword ()).addOnCompleteListener (new OnCompleteListener <AuthResult> () {
            @Override
            public void onComplete(@NonNull Task <AuthResult> task) {
                if (task.isSuccessful ()) {
                    Toast.makeText (NegotiatorRegistration.this, "Sign up successfull", Toast.LENGTH_SHORT).show ();

                  //  startActivity (new Intent (NegotiatorRegistration.this,NegotiatorForm.class));
                    Intent intent = new Intent (NegotiatorRegistration.this,NegotiatorForm.class);
                    intent.putExtra ("basic",profile);
                   // session.createLoginSession ("nego",profile.getMemail ());
                    user = firebaseAuth.getCurrentUser ();
                    Log.v ("check",user.getUid ());
//                    user.sendEmailVerification ();
                     user.sendEmailVerification ().addOnCompleteListener (new OnCompleteListener <Void> () {
                        @Override
                        public void onComplete(@NonNull Task <Void> task) {
                            Toast.makeText (NegotiatorRegistration.this, "Verification link is send to your email id", Toast.LENGTH_SHORT).show ();

                        }
                    });
//                    session.createLoginSession ("nego",profile.getMemail ());
                    startActivity (intent);
                     finish ();

                }
                if(!task.isSuccessful()){
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(NegotiatorRegistration.this, "Failed Registration: "+e.getMessage (), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }







}
