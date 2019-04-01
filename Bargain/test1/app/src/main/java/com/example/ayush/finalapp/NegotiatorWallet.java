package com.example.ayush.finalapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class NegotiatorWallet extends Fragment {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ImageView imageView;
    FragmentActivity f;
    String temp;
    TextView textViewamount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.nego_wallet,null);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        textViewamount = (TextView) view.findViewById (R.id.money_text);

        f=getActivity ();


        mUser = FirebaseAuth.getInstance ().getCurrentUser ();
        assert mUser != null;
        temp = mUser.getUid ();
        fetch ();
        TextView button1 = (TextView) view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrdisplay next= new qrdisplay (f);
                f.getSupportFragmentManager ().beginTransaction()
                        .replace(R.id.content_frame, next, "qrcode")
                        .addToBackStack(null)
                        .commit();
            }
        });

        TextView button3 = (TextView) view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previouspaymentsnego next= new previouspaymentsnego();
                f.getSupportFragmentManager ().beginTransaction()
                        .replace(R.id.content_frame, next, "negoprevpayments")
                        .addToBackStack(null)
                        .commit();
            }
        });



    }
    public  void fetch()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator").child (mUser.getUid ()).child ("amount");
        databaseReference.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String am = (String) dataSnapshot.getValue ();
                double trun = Double.parseDouble (am);
                String temptrun = String.format ("%.2f",trun);
                textViewamount.setText (temptrun);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}



