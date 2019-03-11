package com.example.ayush.finalapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class HomeShopperfrag extends Fragment {
    SearchView msearchview;
    EditText msearchtext;
    Button location_selector;
    int PLACE_PICKER_REQUEST = 1;
    String loc_cat;
    Place place;
    Geocoder geocoder;
    List <Address> addresses;
    ViewPager viewPager;

    MyCustomPagerAdapter myCustomPagerAdapter;
    Context context;
    String pincode;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.shopper_home_frag,null);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        msearchview=(SearchView) view.findViewById(R.id.search);//intialisig searchView
        location_selector = (Button) view.findViewById(R.id.shopper_home_loc_button);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        myCustomPagerAdapter = new MyCustomPagerAdapter(this.getActivity());
        viewPager.setAdapter(myCustomPagerAdapter);

        location_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                try {
                    if(getActivity () != null) {
                        startActivityForResult (intentBuilder.build (getActivity ()), PLACE_PICKER_REQUEST);

                    }

                    else
                    {
                        Toast.makeText (getActivity (),"android",Toast.LENGTH_SHORT).show ();
                    }
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });



        int idtxt = msearchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        msearchtext = (EditText)view.findViewById(idtxt);//initialising the searched text in an edit text

        msearchtext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction ();
                fragmentTransaction.replace(R.id.content_frame,new Searchfrag ());
                fragmentTransaction.addToBackStack ("SearchTagEdit");
                fragmentTransaction.commit ();
                return false;
            }
        });

        msearchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction ();
                fragmentTransaction.replace(R.id.content_frame,new Searchfrag ());
                fragmentTransaction.addToBackStack ("SearchTag");
                fragmentTransaction.commit ();

            }
        });



     /*   location_selector = (Button) view.findViewById(R.id.shopper_home_loc_button);
        if (location_selector == null)
            Toast.makeText(getActivity(), "not all can be empty", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "maa chuda", Toast.LENGTH_SHORT).show();
        location_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(intentBuilder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (RESULT_OK == resultCode) {
              place = PlacePicker.getPlace(getActivity(), data);
                Log.d ("checking", String.valueOf (place.getLatLng ()));
                // here i am getting the latitue and longitute
                pincode =test ();
    /// here is the value transfer this to the shopperHomepage activity and display the list of negotiators there.


                location_selector.setText(place.getName());
                List<Integer> a = place.getPlaceTypes();
                int ab = a.get(0);
                Bundle s_l = new Bundle();


                if (ab == Place.TYPE_ELECTRONICS_STORE) {
                    loc_cat = "Electronics";
                } else if (ab == Place.TYPE_CLOTHING_STORE) {
                    loc_cat = "Clothes";
                } else if (ab == Place.TYPE_BAKERY) {
                    loc_cat = "Groceries";
                } else if (ab == Place.TYPE_DEPARTMENT_STORE) {
                    loc_cat = "Groceries";
                } else if(ab == Place.TYPE_FURNITURE_STORE){
                    loc_cat = "Furniture";
                } else if(ab == Place.TYPE_JEWELRY_STORE){
                    loc_cat = "Jewellery";
                }
                else if (ab == Place.TYPE_SHOPPING_MALL) {
                    loc_cat = "Groceries";
                } else {
                    loc_cat = "Groceries";
                }
                msearchtext.setText(loc_cat);

                s_l.putString("loctype", String.valueOf(loc_cat));


            }
            }
        }

        public String test()
        {
            geocoder = new Geocoder(getContext (), Locale.getDefault());
                double latitude = place.getLatLng ().latitude;
                double longitiute = place.getLatLng ().longitude;
            try {
                addresses = geocoder.getFromLocation (latitude,longitiute, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace ();
            }


            String postalCode = addresses.get(0).getPostalCode();
            Log.d ("pincode",postalCode);

    return postalCode;
        }


    }


