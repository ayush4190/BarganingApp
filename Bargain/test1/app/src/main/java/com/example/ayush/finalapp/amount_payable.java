package com.example.ayush.finalapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;

public class amount_payable extends AppCompatActivity implements Serializable {

//    static String MostRecentIdShopper,MostRecentDate,MostRecentNameShopper,MostRecentNameNegotiator;
//    static String MostRecentIdNegotiator;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String name;
    NegotiatorDetails details;
    TransactionsDetails transactionsDetails;
    String nego_user;
    boolean check,check2;
    String nego_transaction_id;
    String shop_transaction_id;
    String shop_user;
    TextView textView;
    EditText editText;
    FirebaseUser firebaseUser;
    TextView textView1;
    double temp_amount;
   Button ok;
   Button procced ;
    String message;
    AlertDialog.Builder builder2;
    DatabaseReference mroot;
    double existing_amount;
    String amount_pay;
    double finaly;
    ShopperDetails shopperDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_amount_payable);


        editText = (EditText) findViewById (R.id.Amount_pay);
        nego_user = (String) getIntent ().getSerializableExtra ("uid");
        firebaseUser=FirebaseAuth.getInstance ().getCurrentUser ();
        shop_user=firebaseUser.getUid ();
        Log.v ("uid name", nego_user);
        procced = (Button)findViewById (R.id.float_pay);

        //######################## fetching existing balnace of shopper

        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Shopper").child (firebaseUser.getUid ());
        databaseReference.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               shopperDetails = new ShopperDetails ();
                shopperDetails = dataSnapshot.getValue (ShopperDetails.class);
                existing_amount = Double.parseDouble (shopperDetails.getAmount ());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //######################## fecthing nego name
        DatabaseReference mroot = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator").child (nego_user);
        mroot.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                details = new NegotiatorDetails ();
                details = dataSnapshot.getValue (NegotiatorDetails.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        procced.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble (editText.getText ().toString ()) * (0.02);
                Log.v ("final amount", String.valueOf (amount));
                Intent intent = new Intent (amount_payable.this,finalpage.class);
                intent.putExtra ("nego_user",nego_user);
                intent.putExtra ("amount",amount);
                intent.putExtra ("username",shopperDetails.getFname () + shopperDetails.getLname ());
                intent.putExtra ("negoname",details.getFirstname ()+details.getLastname ());
                intent.putExtra ("shopper_details",shopperDetails);
                if(existing_amount-amount > 0)
                {
                    startActivity (intent);
                }
                else
                {
                    Toast.makeText (amount_payable.this,"insufficient balance",Toast.LENGTH_LONG).show ();
                    startActivity (new Intent (amount_payable.this,ShopperHomepage.class));
                }
            }
        });
    }
}


