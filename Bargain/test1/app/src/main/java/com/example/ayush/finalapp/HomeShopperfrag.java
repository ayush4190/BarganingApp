package com.example.ayush.finalapp;

import android.Manifest;
import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

public class HomeShopperfrag extends Fragment implements Serializable {
    SearchView msearchview;
    EditText msearchtext;
    Button location_selector;
    FusedLocationProviderClient fusedLocationProviderClient;
    int PLACE_PICKER_REQUEST = 1;
    String loc_cat;
    Place place;
    Geocoder geocoder;
    List <Address> addresses;
    ViewPager viewPager;
    private List <NegotiatorDetails> negotiatorList = new ArrayList <> ();
    private RecyclerView recyclerView;
    private NegotiatorProfileAdapter adapter;
    MyCustomPagerAdapter myCustomPagerAdapter;
    Context context;
    int currentPage = 0;
    int NUM_PAGES = 3;
    String pincode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.shopper_home_frag, null);
    }

    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location)
                {
                    if(location!=null)
                    {
                        geocoder = new Geocoder (getContext (), Locale.getDefault ());
                        double latitude = location.getLatitude();
                        double longitiute = location.getLongitude();
                        try {
                            addresses = geocoder.getFromLocation (latitude, longitiute, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace ();
                        }

                        try {
                            String postalCode = addresses.get (0).getPostalCode ();
                            pincode = postalCode;
                            list_negotiators ();
                        }catch (NullPointerException e)
                        {}
                    }
                }
            });
        }catch (SecurityException e)
        {
            Log.e("askd",e.toString());
        }
        msearchview = (SearchView) view.findViewById (R.id.search);//intialisig searchView
        location_selector = (Button) view.findViewById (R.id.shopper_home_loc_button);
        viewPager = (ViewPager) view.findViewById (R.id.viewPager);
        myCustomPagerAdapter = new MyCustomPagerAdapter (this.getActivity ());

        viewPager.setAdapter (myCustomPagerAdapter);

        recyclerView = (RecyclerView) view.findViewById (R.id.recyclerhomepage);
        adapter = new NegotiatorProfileAdapter (negotiatorList, getActivity (), 0);

        final Handler handler = new Handler ();
        final Runnable Update = new Runnable () {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem (currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer ();
        swipeTimer.schedule (new TimerTask () {

            @Override
            public void run() {
                handler.post (Update);
            }
        }, 800, 4500);
        location_selector.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder ();
                try {
                    if (getActivity () != null) {
                        startActivityForResult (intentBuilder.build (getActivity ()), PLACE_PICKER_REQUEST);

                    } else {
                        Toast.makeText (getActivity (), "android", Toast.LENGTH_SHORT).show ();
                    }
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace ();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace ();
                }
            }
        });
        int idtxt = msearchview.getContext ().getResources ().getIdentifier ("android:id/search_src_text", null, null);
        msearchtext = (EditText) view.findViewById (idtxt);//initialising the searched text in an edit text

        msearchtext.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FragmentTransaction fragmentTransaction = getFragmentManager ().beginTransaction ();
                fragmentTransaction.replace (R.id.content_frame, new Searchfrag ());
                fragmentTransaction.addToBackStack ("SearchTagEdit");
                fragmentTransaction.commit ();
                return false;
            }
        });

        msearchview.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager ().beginTransaction ();
                fragmentTransaction.replace (R.id.content_frame, new Searchfrag ());
                fragmentTransaction.addToBackStack ("SearchTag");
                fragmentTransaction.commit ();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (RESULT_OK == resultCode) {
                place = PlacePicker.getPlace (getActivity (), data);

                Log.d ("checking", String.valueOf (place.getLatLng ()));
                // here i am getting the latitue and longitute
                pincode = test ();
                list_negotiators ();
                /// here is the value transfer this to the shopperHomepage activity and display the list of negotiators there.

                location_selector.setText (place.getName ());
                List <Integer> a = place.getPlaceTypes ();
                int ab = a.get (0);
                Bundle s_l = new Bundle ();

                if (ab == Place.TYPE_ELECTRONICS_STORE) {
                    loc_cat = "Electronics";
                } else if (ab == Place.TYPE_CLOTHING_STORE) {
                    loc_cat = "Clothes";
                } else if (ab == Place.TYPE_BAKERY) {
                    loc_cat = "Groceries";
                } else if (ab == Place.TYPE_DEPARTMENT_STORE) {
                    loc_cat = "Groceries";
                } else if (ab == Place.TYPE_FURNITURE_STORE) {
                    loc_cat = "Furniture";
                } else if (ab == Place.TYPE_JEWELRY_STORE) {
                    loc_cat = "Jewellery";
                } else if (ab == Place.TYPE_SHOPPING_MALL) {
                    loc_cat = "Groceries";
                } else {
                    loc_cat = "Groceries";
                }
                msearchtext.setText (loc_cat);
                s_l.putString ("loctype", String.valueOf (loc_cat));
            }
        }
    }


    public String test() {
        geocoder = new Geocoder (getContext (), Locale.getDefault ());
        double latitude = place.getLatLng ().latitude;
        double longitiute = place.getLatLng ().longitude;
        try {
            addresses = geocoder.getFromLocation (latitude, longitiute, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace ();
        }


        String postalCode = addresses.get (0).getPostalCode ();
        return postalCode;
    }


    public void list_negotiators() {
        adapter.clear();
        recyclerView.setAdapter(adapter);
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator");

        Query query2 = databaseReference.orderByChild ("pincode").equalTo (pincode);
        query2.addChildEventListener (new ChildEventListener () {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists ()) {
                    NegotiatorDetails data = dataSnapshot.getValue (NegotiatorDetails.class);
                    adapter.addItem (data, dataSnapshot.getKey ());
                    adapter.notifyDataSetChanged ();
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
        recyclerView.setAdapter (adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager (getActivity ());//
        recyclerView.setLayoutManager (mLayoutManager);//n
        adapter.notifyDataSetChanged ();
    }

}


