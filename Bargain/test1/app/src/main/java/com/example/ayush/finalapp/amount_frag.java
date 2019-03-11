package com.example.ayush.finalapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    EditText editText;
    TextView textView1;
    float temp_amount;

    @SuppressLint("ValidFragment")
    public amount_frag(String nego_user) {
        this.nego_user = nego_user;
    }

    public amount_frag() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.amount_frag, null);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        textView1 = (TextView) view.findViewById (R.id.payable_amount);
        editText =(EditText) view.findViewById (R.id.Amount_pay);

//        try {
//            temp_amount = (Float.parseFloat (editText.getText ().toString ().trim ()));
//            temp_amount = (float) (temp_amount*(0.02));
//            textView1.setText (String.valueOf (temp_amount));
//            databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator");
//
//            databaseReference.child (nego_user).addValueEventListener (new ValueEventListener () {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    details = new NegotiatorDetails ();
//                    details = dataSnapshot.getValue (NegotiatorDetails.class);
//                    textView = (TextView) view.findViewById (R.id.nego_payname);
//                    try {
//                        name = details.getFirstname () + " " + details.getLastname ();
//
//                        textView.setText (name);
//                        // here open a new fragment here for asking permission about completing the payment
//
//
//                    } catch (NullPointerException e) {
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        } catch (NullPointerException e) {
//        }
    }
    private void proceed_payment() {
        try {


            Float temp = Float.parseFloat (details.getAmount () )+ temp_amount;
            details.setAmount (String.valueOf (temp));
            databaseReference.child (nego_user).child ("amount").setValue (details.getAmount ());
        }catch (NumberFormatException e)
        {

        }

    }

}


