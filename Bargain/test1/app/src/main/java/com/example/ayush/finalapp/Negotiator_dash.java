package com.example.ayush.finalapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Negotiator_dash extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    private DatabaseReference fdb;
    FirebaseAuth fba;
    FirebaseUser muser;
    FirebaseUser user;
    ImageView mwallet, nfaq;
    Fragment fragment = null;
    FirebaseStorage firebaseStorage;
    StorageReference photo_storage;

  CircleImageView i1;

    // for user name
    FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negotiator_dash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fba = FirebaseAuth.getInstance ();

        // ferencing storage refernce

        photo_storage = FirebaseStorage.getInstance ().getReference ().child ("Upload");


        //

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        muser=fba.getCurrentUser ();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mwallet = (ImageView) findViewById (R.id.wallet);//creating the buttons
        nfaq = (ImageView) findViewById(R.id.faq); //faqfragbutton


        // calling wallet page using fragments
        mwallet.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
                fragmentTransaction.replace(R.id.content_frame,new NegotiatorWallet ());
                fragmentTransaction.addToBackStack("wallet");
                fragmentTransaction.commit ();

            }
        });

        // calling wallet page using fragments
        nfaq.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
                fragmentTransaction.replace(R.id.content_frame,new FAQ ());
                fragmentTransaction.addToBackStack("faq");
                fragmentTransaction.commit ();

            }
        });

        //// added new content here
        final TextView textView = (TextView) findViewById (R.id.shopper_name);
        fdb = FirebaseDatabase.getInstance ().getReference ();
        DatabaseReference shopper = fdb.child ("Negotiator").child (muser.getUid ());

        shopper.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NegotiatorDetails profile = dataSnapshot.getValue (NegotiatorDetails.class);
                assert profile != null;
                String key = profile.getFirstname () + profile.getLastname ();

//                if(key == null)
//                {
//                    Toast.makeText (Negotiator_dash.this,"name is not present",Toast.LENGTH_SHORT).show ();
//                }

                NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);
                View headerView = navigationView.getHeaderView (0);
                TextView navUsername = (TextView) headerView.findViewById (R.id.nego_name);
                navUsername.setText (key);
                TextView user_email = (TextView) headerView.findViewById (R.id.nego_email);
                user_email.setText (profile.getEmail ());
                i1 = (CircleImageView) headerView.findViewById (R.id.image_nego);

                /// adding function to get photo from firebase storage
//                String location = user.getUid () + "." + "jpg";
                try {

                    String location = user.getUid () + "." + "jpg";
                    photo_storage.child (location).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener <Uri> () {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString ();
                            Glide.with (getApplicationContext ()).load (imageURL).into (i1);
                        }
                    }).addOnFailureListener (new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            Toast.makeText (Negotiator_dash.this, exception.getMessage (), Toast.LENGTH_LONG).show ();
                        }
                    });


                    //////////////////////////////////////////////////////

                }catch (NullPointerException e)
                {
                    Toast.makeText (Negotiator_dash.this,e.getMessage (),Toast.LENGTH_LONG).show ();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
/////////////////////
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.negotiator_dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            muser=fba.getCurrentUser ();
            finish ();
            startActivity (new Intent (Negotiator_dash.this,WelcomePage.class));

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
