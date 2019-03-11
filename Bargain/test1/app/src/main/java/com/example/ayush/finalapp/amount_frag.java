package com.example.ayush.finalapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class amount_frag extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String name;
    NegotiatorDetails details;
    String nego_user;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.amount_frag, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
    }
}
//
////        try {
////
////            ValueEventListener valueEventListener = new ValueEventListener () {
////                @Override
////                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////
////                }
////
////                @Override
////                public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                }
////            }
////            databaseReference.child (nego_user).addValueEventListener (new ValueEventListener () {
////                @Override
////                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                    details = new NegotiatorDetails ();
////                    details = dataSnapshot.getValue (NegotiatorDetails.class);
////                    textView = (TextView) view.findViewById (R.id.nego_payname);
////                    try {
////                        name = details.getFirstname () + " " + details.getLastname ();
////
////                        textView.setText (name);
////                        // here open a new fragment here for asking permission about completing the payment
////
////
////                    } catch (NullPointerException e) {
////                    }
////
////                }
////            });
//        }catch (NullPointerException e)
//        {
//        }


