package com.example.ayush.finalapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopperHomepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    private DatabaseReference fdb;
    FirebaseAuth fba;
    Float rate_val;
    static String shop_name;
    AlertDialog.Builder builder2;
    FirebaseUser user;
    ImageView mwallet;
    private static final String channelId ="com.example.ayush.finalapp";
    Fragment fragment = null;
    ImageView mcommunity;
    ImageView mfav, msetting;
    public static Context contextOfApplication;
    int zip;
    public static boolean isAppRunning;
    // for user name
    FirebaseDatabase firebaseDatabase;
    StorageReference photo_storage;
    private LocationManager locationManager;
    private LocationListener locationListener;
    SessionManagment sessionManagment;
//    FirebaseUser user;
//    FirebaseAuth fba;
    CircleImageView shopper_pic;

    String check = "false";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        //function for getting pincode of the current location

        pincode ();
        //
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        OneSignal.sendTag("USER_ID",FirebaseAuth.getInstance().getCurrentUser().getUid());

        toolbar = (Toolbar) findViewById (R.id.toolbarbottom);

        setContentView (R.layout.activity_shopper_homepage);

        contextOfApplication=getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        toolbar.setLogo(R.drawable.applogo1);
        setSupportActionBar (toolbar);

        fba = FirebaseAuth.getInstance ();

        user = fba.getCurrentUser ();

        fdb = FirebaseDatabase.getInstance ().getReference ();


        photo_storage = FirebaseStorage.getInstance ().getReference ().child ("Shopper_profile_image");





/*        DatabaseReference mref = fdb.child ("Shopper").child (user.getUid ());
        mref.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ShopperProfile obj ;
                obj = dataSnapshot.getValue (ShopperProfile.class);

                assert obj != null;
                TextView textView = (TextView)findViewById (R.id.nav_drawer_username);
                textView.setText ((obj.getFname () + obj.getLname ()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        mwallet = (ImageView) findViewById (R.id.wallet);//creating the buttons
        mcommunity = (ImageView) findViewById (R.id.communityimage);
        mfav = (ImageView) findViewById (R.id.favimage);
        msetting=(ImageView)findViewById(R.id.settingimage);
        //mfaq = (ImageView) findViewById (R.id.faq);
        // notification =(MenuItem) findViewById(R.id.action_notification);


        //setting the first fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
        fragmentTransaction.replace (R.id.content_frame, new HomeShopperfrag (), "Homefrag");
        fragmentTransaction.commit ();
        

        // calling wallet page using fragments
        mwallet.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
                fragmentTransaction.replace (R.id.content_frame, new PayementActivity ());
                fragmentTransaction.addToBackStack ("wallet");
                fragmentTransaction.commit ();

            }
        });
        // calling community page using fragments
        mcommunity.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
                fragmentTransaction.replace (R.id.content_frame, new CommunityFragment ());
                fragmentTransaction.addToBackStack ("community");
                fragmentTransaction.commit ();

            }
        });
        //calling favourite page
        mfav.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
                fragmentTransaction.replace (R.id.content_frame, new Favourite ());
                fragmentTransaction.addToBackStack ("fav");
                fragmentTransaction.commit ();

            }
        });

//        final NotificationCompat.Builder builder = new NotificationCompat.Builder(ShopperHomepage.this, channelId)
//                .setContentTitle("Bargainer App")
//                .setSmallIcon(R.drawable.appicon1)
//                .setContentText("Hi this is test notification")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setLargeIcon(BitmapFactory.decodeResource(getResources(),
//                        R.drawable.appicon1));

        msetting.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
//                sendNotification();
              startActivity(new Intent(ShopperHomepage.this, SettingsActivity.class));
//                Log.v("here","i am here");

//notification is white as icon color is not trasnparent and white
            }
        });


//        //calling faq page
//        mfaq.setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });not

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener (toggle);
        toggle.syncState ();

        NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (this);

        //// added new content here
        final TextView textView = (TextView) findViewById (R.id.shopper_name);
        DatabaseReference shopper = fdb.child ("Shopper").child (user.getUid ());

        shopper.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    ShopperProfile profile = dataSnapshot.getValue (ShopperProfile.class);
                    assert profile != null;
                    String key = profile.getFname () + profile.getLname ();
                    shop_name = key;

                    NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);
                    View headerView = navigationView.getHeaderView (0);
                    TextView navUsername = (TextView) headerView.findViewById (R.id.shopper_name);
                    navUsername.setText (key);
                    TextView user_email = (TextView) headerView.findViewById (R.id.shopper_drawer_mail);
                    user_email.setText (profile.getEmail ());
                    shopper_pic = (CircleImageView) headerView.findViewById (R.id.image_shopper);


                    /// adding function to get photo from firebase storage
//                String location = user.getUid ()+"."+"jpg";
//                photo_storage.child (location).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri> () {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        String imageURL = uri.toString ();
//                        Glide.with(getApplicationContext()).load(imageURL).into(shopper_pic);
//                    }
//                }).addOnFailureListener(new OnFailureListener () {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle any errors
//                        Toast.makeText (ShopperHomepage.this,exception.getMessage (),Toast.LENGTH_LONG).show ();
//                    }
//                });
                    fetch ();
                }catch (NullPointerException e)
                {}
                    try {
                        check = (String) getIntent ().getSerializableExtra ("bool");
                        if(check.compareToIgnoreCase ("true") == 0) {
                            fetch ();
                            check = "false";
                        }
                    }catch (NullPointerException e)
                    {
                        //Toast.makeText (ShopperHomepage.this,e.getMessage (),Toast.LENGTH_LONG).show ();
                    }
                //////////////////////////////////////////////////////


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
/////////////////////

    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        if (drawer.isDrawerOpen (GravityCompat.START)) {
            drawer.closeDrawer (GravityCompat.START);
        } else {
            super.onBackPressed ();
            getSupportFragmentManager ().popBackStackImmediate (null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.shopper_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chatbox) {//R.id.action_setting

            //open chat fragment
//            Intent intent = new Intent (ShopperHomepage.this,ChatMain.class);
//            startActivity (intent);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
            fragmentTransaction.replace (R.id.content_frame, new ChatFragment ());
            fragmentTransaction.addToBackStack ("chatfrag");
            fragmentTransaction.commit ();
        }
        if (id == R.id.action_notification) {
//to open notification as a fragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
            fragmentTransaction.replace (R.id.content_frame, new NotificationFrag ());
            fragmentTransaction.addToBackStack ("notification");
            fragmentTransaction.commit ();
        }

        return super.onOptionsItemSelected (item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //  TextView fullname=(TextView)findViewById(R.id.nav_drawer_username);
        //fullname.setText(user.getDisplayName());
        // user.getEmail();
        //  user.getDisplayName();
        // Fragment fragment = null;

        int id = item.getItemId ();

        if (id == R.id.nav_logout) {

            // Handle the camera action
            sessionManagment = new SessionManagment (getApplicationContext ());

            fba.signOut ();
            sessionManagment.logoutUser ();

            finish ();
            startActivity (new Intent (ShopperHomepage.this, WelcomePage.class));

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent (ShopperHomepage.this, ShopperProfileActivity.class);
            startActivity (intent);

        } else if (id == R.id.nav_faq) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
            fragmentTransaction.replace (R.id.content_frame, new FAQ ());
            fragmentTransaction.addToBackStack ("faq");
            fragmentTransaction.commit ();

        } else if (id == R.id.nav_cs) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
            fragmentTransaction.replace (R.id.content_frame, new CustomerServiceFrag ());
            fragmentTransaction.addToBackStack ("faq");
            fragmentTransaction.commit ();


        } else if (id == R.id.nav_rate) {


            builder2 = new AlertDialog.Builder (ShopperHomepage.this);
            builder2.setTitle("Rate App");
            builder2.setMessage("Show us some love!!");

            LinearLayout linearLayout = new LinearLayout(this);
            final RatingBar rating = new RatingBar(this);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            rating.setLayoutParams(lp);
            rating.setNumStars(5);
            rating.setStepSize(1);
            linearLayout.addView(rating);
            linearLayout.setGravity(Gravity.CENTER);

//            rating.setNumStars(5);
            builder2.setIcon(android.R.drawable.star_on);
            builder2.setView(linearLayout);

            builder2.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    int rate_val =rating.getProgress();
                    Log.v("RAAAAA",String.valueOf(rating.getProgress()));
                    if (rate_val>=4)
                    Toast.makeText(ShopperHomepage.this,"Thank you for your Support!",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(ShopperHomepage.this,"Thank you! Please provide feedback to help us improve the service",Toast.LENGTH_SHORT).show();
                    // here you can add functions
                }
            });
            builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                    // here you can add functions
                }
            });

            builder2.create();
            builder2.show();  //<-- See This!


        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";//insert app link here
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Bargaining App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));



        }


        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        drawer.closeDrawer (GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        if((grantResults.length > 0) && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission (this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER, 0, 0,locationListener);

            }
            else
            {
                locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER, 0, 0,locationListener);
            }
        }
    }

    public void fetch()
    {
        try {


            String location = user.getUid () + "." + "jpg";
            Log.v("manas",photo_storage.getPath().toString());

            photo_storage.child (location).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener <Uri> () {
                @Override
                public void onSuccess(Uri uri) {
                    String imageURL = uri.toString ();
                    Log.v("manas2",imageURL);
                    Glide.with (getApplicationContext ()).load (imageURL).into (shopper_pic);
                }
            }).addOnFailureListener (new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText (ShopperHomepage.this, exception.getMessage (), Toast.LENGTH_LONG).show ();
                }
            });
        }catch (NullPointerException e)
        {
            Toast.makeText (ShopperHomepage.this,e.getMessage (),Toast.LENGTH_LONG).show ();
        }


    }

    public void pincode() {
        locationManager = (LocationManager) this.getSystemService (LOCATION_SERVICE);
        locationListener = new LocationListener () {
            @Override
            public void onLocationChanged(Location location) {
//           Toast.makeText ( ShopperHomepage.this,location.toString (),Toast.LENGTH_LONG).show ();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }


            @Override
            public void onProviderEnabled(String provider) {


            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 11);

        }
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER, 0, 0,locationListener);

    }

    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel1";
            String description = "Channel discription";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAppRunning = false;
    }

}




