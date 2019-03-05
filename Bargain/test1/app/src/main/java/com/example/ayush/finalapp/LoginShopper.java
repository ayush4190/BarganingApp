package com.example.ayush.finalapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow ();
            window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor (getResources ().getColor (R.color.colorPrimary));
        }



        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            Toast.makeText (LoginShopper.this,"testing mode",Toast.LENGTH_SHORT).show ();
       //     ver ();


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
        DatabaseReference data = FirebaseDatabase.getInstance ().getReference ();
        DatabaseReference reference= data.child (user.getUid ());
        reference.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                test temp = new test ();
                temp.setDecide ((String) dataSnapshot.getValue ());
                if(temp.getDecide ().compareTo ("true") == 0)
                {
                    startActivity (new Intent (LoginShopper.this,Negotiator_dash.class));
                }
                else if(temp.getDecide ().compareTo ("false")==0)
                {
                    startActivity (new Intent (LoginShopper.this,ShopperHomepage.class));
                }
                else
                {
                    Toast.makeText (LoginShopper.this,"not a valid client",Toast.LENGTH_SHORT).show ();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Toast.makeText (LoginPage.this,""+s,Toast.LENGTH_SHORT).show ();

    }

    }
