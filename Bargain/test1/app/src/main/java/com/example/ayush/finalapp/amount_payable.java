package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_amount_payable);

        textView1 = (TextView) findViewById (R.id.payable_amount);
        editText = (EditText) findViewById (R.id.Amount_pay);
        nego_user = (String) getIntent ().getSerializableExtra ("uid");
        Log.d ("uid name",nego_user);

        try {
            NumberFormat nf = NumberFormat.getCurrencyInstance ();
//            temp_amount = (double) nf. parse (String.valueOf (Double.parseDouble (( (editText.getText ().toString ().trim ())))));
//            temp_amount =  (temp_amount*(0.02));
            temp_amount = (double) nf.parse (editText.getText ().toString ().trim ());
            temp_amount = temp_amount * (0.02);
            textView1.setText (String.valueOf (temp_amount));
            databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator");

            databaseReference.child (nego_user).addValueEventListener (new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    details = new NegotiatorDetails ();
                    details = dataSnapshot.getValue (NegotiatorDetails.class);
                    textView = (TextView) findViewById (R.id.nego_payname);
                    try {
                        name = details.getFirstname () + " " + details.getLastname ();

                        textView.setText (name);
                        Log.d ("amount id",details.getAmount ());
                        // here open a new fragment here for asking permission about completing the payment


                    } catch (NullPointerException e) {
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (NullPointerException e) {
        } catch (ParseException e) {
            e.printStackTrace ();
        }
//        try {
//            TextView button = (TextView) findViewById (R.id.buttonProceed);
//            button.setOnClickListener (new View.OnClickListener () {
//                @Override
//                public void onClick(View v) {
//                    if (proceed_payment () == 1) {
//                        startActivity (new Intent (amount_payable.this, ShopperHomepage.class));
//                    } else {
//                        Toast.makeText (amount_payable.this, "try after some time", Toast.LENGTH_LONG).show ();
//                        startActivity (new Intent (amount_payable.this, ShopperHomepage.class));
//                    }
//                }
//            });
//        } catch (NullPointerException e) {
//        }
    }
    private int proceed_payment() {
        try {
            NumberFormat nf = NumberFormat.getCurrencyInstance ();

            double temp = (double) nf.parse (details.getAmount ());
            String tt = String.valueOf (temp + temp_amount);
            details.setAmount (String.valueOf (tt));
            databaseReference.child (nego_user).child ("amount").setValue (details.getAmount ());
            return 1;
        }catch (NumberFormatException e)
        {
                return 0;
        } catch (ParseException e) {
            e.printStackTrace ();
        }

        return 0;
    }

}

