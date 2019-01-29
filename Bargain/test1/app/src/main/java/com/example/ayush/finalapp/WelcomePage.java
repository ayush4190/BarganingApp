package com.example.ayush.finalapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomePage extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        firebaseAuth =FirebaseAuth.getInstance();
       user =firebaseAuth.getCurrentUser();
       if(user != null)
       {
           //start Negotiator/shopper activity
            Toast.makeText(WelcomePage.this,"hello its done",Toast.LENGTH_LONG).show();
           // Intent intent = new Intent (WelcomePage.this,PaymentActivity.class);
           // startActivity (intent);
       }
        // email =(EditText)findViewById(R.id.email);
        //  password = (EditText)findViewById(R.id.password);


        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ShopperHomepage.class);
                v.getContext().startActivity(intent);

            }
        });

        Button button = (Button) findViewById(R.id.shopper);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ShopperRegistration.class);
                v.getContext().startActivity(intent);

            }
        });

        Button button1 = (Button) findViewById(R.id.Negotiator);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NegotiatorRegistration.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}

