package com.example.ayush.finalapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow ();
            window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor (getResources ().getColor (R.color.colorPrimary));
        }
        int Permission_All = 1;

        String[] Permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.READ_SMS};
        if (!hasPermissions (this, Permissions)) {
            ActivityCompat.requestPermissions (this, Permissions, Permission_All);
        }


        firebaseAuth = FirebaseAuth.getInstance ();
        user = firebaseAuth.getCurrentUser ();
        if (user != null) {

                checkuser();

          //  Toast.makeText (WelcomePage.this,"in testing mode",Toast.LENGTH_SHORT).show ();

        }



        ImageView textView = (ImageView) findViewById (R.id.negotiatorwelcome);
        textView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (v.getContext (), ShopperHomepage.class);
                v.getContext ().startActivity (intent);

            }
        });

       ImageView imageView =(ImageView) findViewById (R.id.shopperwelcome);
       imageView.setOnClickListener (new View.OnClickListener () {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent (v.getContext (),ShopperRegistration.class);
               v.getContext ().startActivity (intent);
           }
       });

       TextView textView1 = (TextView)findViewById (R.id.negotiatorwelcometext);
       textView1.setOnClickListener (new View.OnClickListener () {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent (v.getContext (),LoginPage.class);
               v.getContext ().startActivity (intent);
           }
       });

        TextView textView2 = (TextView)findViewById (R.id.shopperwelcometext);
        textView2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext (),LoginShopper.class);
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
    private void checkuser()
    {

        DatabaseReference mref = FirebaseDatabase.getInstance ().getReference ().child (user.getUid ());
       mref.addChildEventListener (new ChildEventListener () {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               test temp = new test ();
               temp = dataSnapshot.getValue (test.class);
             //  temp.setDecide (dataSnapshot.getValue (test.class));
               assert temp!=null;
               temp.setDecide (dataSnapshot.getValue (test.class));
               if(temp.getDecide ().compareTo ("true") == 0)
               {
                   Toast.makeText (WelcomePage.this,"its done",Toast.LENGTH_SHORT).show ();
               }
               else
               {
                   Toast.makeText (WelcomePage.this,"more effort is required",Toast.LENGTH_SHORT).show ();
               }
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }



}

