package com.example.ayush.finalapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
        int Permission_All = 1;

        String[] Permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, };
        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }

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

    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }

}

