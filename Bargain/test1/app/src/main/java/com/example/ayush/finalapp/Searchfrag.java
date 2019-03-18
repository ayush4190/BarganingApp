package com.example.ayush.finalapp;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;


public class Searchfrag extends Fragment {
    //int filterpos=-1;
    SearchView searchbut;
    EditText searchfragedit;
    SearchView.SearchAutoComplete searchAutoComplete;
    String searchtext;
    ImageView filter;
    String age;
    int PLACE_PICKER_REQUEST = 1;
    Button location_selector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.search_frag, null);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments ();
        if(bundle != null)
            age = getArguments().getString ("Agevalue");
        else
            age = "0";
        Log.v ("age",age);


///// check for int =0;

        searchbut = (SearchView) view.findViewById(R.id.search);
        searchbut.setIconifiedByDefault(false);
        searchbut.setFocusable(true);
//        searchbut.requestFocus();
        searchfragedit=(EditText)searchbut.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete=(android.support.v7.widget.SearchView.SearchAutoComplete)searchbut.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownAnchor(R.id.search);
        final String[] categories={"Electronics","Furniture","Groceries","Jewellery","Clothes"};
        final long ids[]=new long[5];
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,categories);
        searchAutoComplete.setThreshold(0);
        searchAutoComplete.setAdapter(adapter);
        searchAutoComplete.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        searchfragedit.setText((parent.getItemAtPosition(position)).toString());
                        searchfragedit.setSelection(searchfragedit.getText().length());
                    }
                });
        /*location_selector = (Button) view.findViewById(R.id.shopper_home_loc_button);
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

        //to make the keyboard pop up when fragment opens
        searchfragedit.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        filter = (ImageView) view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new Filterfrag());
                fragmentTransaction.addToBackStack("filter");
                fragmentTransaction.commit();

            }
        });


        searchtext = searchbut.getQuery().toString();
        Log.v("search value ", searchtext);


        searchbut.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchtext = searchbut.getQuery().toString();
                Log.v("search value ", searchtext);


//                        Intent intent = new Intent(getActivity(), SearchDisplay.class);
//                         //intent.putExtra("filter_result_pos", String.valueOf(filterpos));
//                        intent.putExtra("Searchtext", searchtext);
////
//                  startActivity(intent);
//                        FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
//                        fragmentTransaction.replace(R.id.content_frame,new NotificationFrag());
//                        fragmentTransaction.addToBackStack("notification");
//                        fragmentTransaction.commit ();
                //Put the value


                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                SearchDisplayFrag ldf = new SearchDisplayFrag();
                Bundle args = new Bundle();
                args.putString("Searchtext", searchtext);
                args.putString("Agevalue", (age));
                ldf.setArguments(args);

                fragmentTransaction.addToBackStack("searchbar");
                fragmentTransaction.replace(R.id.content_frame, ldf).commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                location_selector.setText(place.getAddress());
            }
        }
    }*/
}







//spinner part not needed


//        final Spinner mySpinner = (Spinner) view.findViewById(R.id.filter);
//        ArrayAdapter<String> myAdapter = new ArrayAdapter <String> (getActivity(), android.R.layout.simple_list_item_1, getResources ().getStringArray (R.array.filter_drop)) {
//            @Override
//            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView (position, convertView, parent);
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor (Color.GRAY);
//                } else {
//                    tv.setTextColor (Color.BLACK);
//                }
//                return view;
//            }
//
//        };
//
//        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        myAdapter.notifyDataSetChanged();
//        mySpinner.setAdapter(myAdapter);
//                  int m=mySpinner.getSelectedItemPosition();
////                if(m==1)
////                    Log.v("search value ", m);
//                    mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            filterpos=mySpinner.getSelectedItemPosition();
//                        }
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//
//                        }
//                    });
//