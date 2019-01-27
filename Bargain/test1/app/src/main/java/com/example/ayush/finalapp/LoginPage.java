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

public class LoginPage extends AppCompatActivity {
    EditText email, password;
    FirebaseAuth firebaseAuth;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            // Start activity here
            finish();
            startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
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



    }
    private void userlogin()
    {
        String memail = email.getText().toString().trim();
        String mpassword = password.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(memail,mpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(LoginPage.this,"Login sucess",Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(LoginPage.this,PaymentActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginPage.this,"error",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}