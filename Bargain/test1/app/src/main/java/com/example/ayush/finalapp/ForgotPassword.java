package com.example.ayush.finalapp;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPassword extends AppCompatActivity {

    TextInputLayout email_w;
    TextInputEditText email_q ;
    Button forgotButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        email_w = findViewById(R.id.forgotemail_up);
        forgotButton=findViewById(R.id.forgotbutton);
        email_q =  findViewById(R.id.email);
//        String memail = email_q.getText().toString().trim();

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add forgot email part
            }
        });



    }
}
