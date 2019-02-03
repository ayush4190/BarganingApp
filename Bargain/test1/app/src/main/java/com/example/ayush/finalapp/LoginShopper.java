package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginShopper extends AppCompatActivity {

    EditText email, password;
    FirebaseAuth firebaseAuth;
    Button button;
    FirebaseUser user;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login_shopper);



        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            if(s.compareToIgnoreCase ("shop")==0)
            {
                startActivity (new Intent (LoginShopper.this,ShopperHomepage.class));
            }
            else
            {
                startActivity (new Intent (LoginShopper.this,Negotiator_dash.class));
            }


        }
        email =(EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        button = (Button)findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();


            }
        });


        user =firebaseAuth.getCurrentUser();




    }
    private void userlogin()
    {
        String memail = email.getText().toString().trim();
        String mpassword = password.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(memail,mpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())

                {
                    Toast.makeText (LoginShopper.this,"you are logged in in testing mode",Toast.LENGTH_SHORT).show ();
                    s= "shop";
                    startActivity (new Intent (LoginShopper.this,Negotiator_dash.class));
                }
                else
                {
                    Toast.makeText(LoginShopper.this,"not a valid client",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    }
