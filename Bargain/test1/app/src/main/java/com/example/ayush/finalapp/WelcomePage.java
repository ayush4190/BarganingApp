package com.example.ayush.finalapp;

import android.app.Dialog;
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
import android.content.Context;
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
import com.google.firebase.storage.StorageReference;

import android.Manifest;

public class WelcomePage extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String s;
    FirebaseDatabase data;
    StorageReference mroot, mref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_welcome_page);

        int Permission_All = 1;

        String[] Permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,};
        if (!hasPermissions (this, Permissions)) {
            ActivityCompat.requestPermissions (this, Permissions, Permission_All);
        }


        firebaseAuth = FirebaseAuth.getInstance ();
        user = firebaseAuth.getCurrentUser ();
        if (user != null) {

            showdata ();
        }


        TextView textView = (TextView) findViewById (R.id.textView3);
        textView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (v.getContext (), LoginPage.class);
                v.getContext ().startActivity (intent);

            }
        });

        Button button = (Button) findViewById (R.id.shopper);
        button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (v.getContext (), ShopperRegistration.class);
                v.getContext ().startActivity (intent);

            }
        });

        Button button1 = (Button) findViewById (R.id.Negotiator);
        button1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext (), NegotiatorRegistration.class);
                v.getContext ().startActivity (intent);
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission (context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void showdata() {


        DatabaseReference mref = FirebaseDatabase.getInstance ().getReference ();
        DatabaseReference mroot = mref.child ("Negotiator").child (user.getUid ());
        mroot.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NegotiatorDetails details;
                details = dataSnapshot.getValue (NegotiatorDetails.class);
                assert details != null;
                s = details.getBl ();
                if (s.compareToIgnoreCase ("true") == 0) {
                    startActivity (new Intent (WelcomePage.this, Negotiator_dash.class));
                } else {
                    startActivity (new Intent (WelcomePage.this, ShopperHomepage.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }
}

