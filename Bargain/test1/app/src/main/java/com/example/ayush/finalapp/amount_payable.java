package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;

public class amount_payable extends AppCompatActivity implements Serializable {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String name;
    NegotiatorDetails details;
    String nego_user;
    TextView textView;
    EditText editText;
    TextView textView1;
    double temp_amount;
   Button ok;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_amount_payable);

//        textView1 = (TextView) findViewById (R.id.payable_amount);
        editText = (EditText) findViewById (R.id.Amount_pay);
        nego_user = (String) getIntent ().getSerializableExtra ("uid");
        Log.v ("uid name", nego_user);
      //  floatingActionButton = (Button) findViewById (R.id.float_pay);

//        try {
//            floatingActionButton.setOnClickListener (new View.OnClickListener () {
//                @Override
//                public void onClick(View v) {
//                    String am = editText.getText ().toString ();
//                    if(am != null) {
//                        temp_amount = Double.parseDouble (am);
//                        temp_amount = temp_amount * (0.02);
//                        textView1.setText (String.valueOf (temp_amount));
//                    }
//                }
//            });
//        ok = (Button)findViewById (R.id.float_pay);
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator");

        databaseReference.child (nego_user).addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                details = new NegotiatorDetails ();
                details = dataSnapshot.getValue (NegotiatorDetails.class);
                textView = (TextView) findViewById (R.id.nego_payname);
                name = details.getFirstname () + " " + details.getLastname ();
                textView.setText (name);
                Log.d ("amount id", details.getAmount ());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ok.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                 message = editText.getText ().toString ();
                proceed_payment ();
            }
        });


//            databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator");
//
//            databaseReference.child (nego_user).addValueEventListener (new ValueEventListener () {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    details = new NegotiatorDetails ();
//                    details = dataSnapshot.getValue (NegotiatorDetails.class);
//                    textView = (TextView) findViewById (R.id.nego_payname);
//                    name = details.getFirstname () + " " + details.getLastname ();
//                    textView.setText (name);
//                    Log.d ("amount id", details.getAmount ());
//
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

        }
    private void proceed_payment() {
        try {


            double temp = Double.parseDouble (details.getAmount ());
            double temp1 = Double.parseDouble (message);
            String tt = String.valueOf (temp + temp1);
            details.setAmount (String.valueOf (tt));
            Log.v ("final amount",details.getAmount ());
           databaseReference.child (nego_user).child ("amount").setValue (details.getAmount ());
            Toast.makeText (amount_payable.this,"Payment completed",Toast.LENGTH_LONG).show ();
            startActivity (new Intent (amount_payable.this,ShopperHomepage.class));
        }catch (NumberFormatException e)
        {

        }


    }

    }


