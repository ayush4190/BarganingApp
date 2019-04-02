package com.example.ayush.finalapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;

public class LoginShopper extends AppCompatActivity {

    TextInputLayout password_w;
    TextInputLayout email_w;
    TextInputEditText email_q,password_q ;
    TextView signup;
    FirebaseAuth firebaseAuth;
    TextView forgotpassword;
    Button button;
    FirebaseUser user;
    String s;
    SessionManagment session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login_shopper);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow ();
            window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor (getResources ().getColor (R.color.colorPrimary));
        }

        session = new SessionManagment (getApplicationContext ());

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            try {
                session.checkLogin ();

                Toast.makeText (getApplicationContext (), "User Login Status: " + session.isLoggedIn (), Toast.LENGTH_LONG).show ();
                HashMap<String , String> list = session.getUserDetails ();
                Collection <String> values = list.values ();
                Object[] temp = values.toArray ();
                Log.v ("if suceess", String.valueOf (session.getUserDetails ()));
                Log.v ("final test", String.valueOf (temp[0]));
                String s = String.valueOf (temp[0]);
                if(session.isLoggedIn () && (s.compareToIgnoreCase ("shopper")==0))
                {
                    finish ();
                    startActivity (new Intent (LoginShopper.this,ShopperHomepage.class));
                    finish ();

                }
                else if(session.isLoggedIn () && (s.compareToIgnoreCase ("nego")==0))
                {
                    finish ();
                    startActivity (new Intent (LoginShopper.this,Negotiator_dash.class));
                    finish ();
                }
                else
                {
                }
            }
            catch (NullPointerException e)
            {}


        }
        email_q =  findViewById(R.id.email);
        password_q=findViewById(R.id.password);
        email_w = findViewById(R.id.email_up);
        password_w = findViewById(R.id.password_up);
        button = (Button)findViewById(R.id.login);
        signup=(TextView)findViewById(R.id.signuplogin);
        forgotpassword=(TextView)findViewById(R.id.forgotpassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext (), WelcomePage.class);
                v.getContext ().startActivity (intent);
                finish();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (v.getContext (), ForgotPassword.class);
                v.getContext ().startActivity (intent);

            }
        });


      //  user =firebaseAuth.getCurrentUser();




    }
    private void userlogin()
    {
        String memail = email_q.getText().toString().trim();
        String mpassword = password_q.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(memail,mpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())

                {
                    ver ();

                }
                else
                {
                    Toast.makeText(LoginShopper.this,"not a valid client",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void ver()
    {
        try {

            user = firebaseAuth.getCurrentUser ();
            DatabaseReference data = FirebaseDatabase.getInstance ().getReference ();
            DatabaseReference reference = data.child (user.getUid ());
            reference.addValueEventListener (new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    test temp = new test ();
                    temp.setDecide ((String) dataSnapshot.getValue ());
                    if (temp.getDecide ().compareTo ("true") == 0) {
                        session.createLoginSession ("nego",user.getEmail ());
                        finish ();
                        startActivity (new Intent (LoginShopper.this, Negotiator_dash.class));
                        finish ();

                    } else if (temp.getDecide ().compareTo ("false") == 0) {
                        finish ();
                        session.createLoginSession ("shopper",user.getEmail ());


                        startActivity (new Intent (LoginShopper.this, ShopperHomepage.class));
                        finish ();
                    } else {
                        Toast.makeText (LoginShopper.this, "not a valid client", Toast.LENGTH_SHORT).show ();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (NullPointerException e)
        {
            Toast.makeText (LoginShopper.this,e.getMessage (),Toast.LENGTH_LONG).show ();
        }


    }

    }
