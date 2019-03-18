package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class finalpage extends AppCompatActivity implements Serializable {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String name;
    NegotiatorDetails details;
    TransactionsDetails transactionsDetails;
    String nego_user;
    boolean check, check2;
    String nego_transaction_id;
    String shop_transaction_id;
    String shop_user;
    TextView negonametxt;
    EditText editText;
    TextView amountpaytxt;
    double temp_amount;
    Button ok;
    Button procced;
    String message;
    AlertDialog.Builder builder2;
    double existing_amount;
    String amount_pay;
    double finaly;
    TextView usernametxt;
    DatabaseReference mroot ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_finalpage);
        firebaseUser = FirebaseAuth.getInstance ().getCurrentUser ();
        shop_user = firebaseUser.getUid ();

        transactionsDetails = new TransactionsDetails ();

        final double payable_amount = (double) getIntent ().getSerializableExtra ("amount");
        nego_user = (String) getIntent ().getSerializableExtra ("nego_user");
        Log.v ("uid name", nego_user);
        String username = (String) getIntent ().getSerializableExtra ("username");
        final String negoname = (String) getIntent ().getSerializableExtra ("negoname");

        usernametxt = (TextView) findViewById (R.id.username_final);
        usernametxt.setText (username);

        amountpaytxt = (TextView) findViewById (R.id.amount_final);
        amountpaytxt.setText (String.format ("₹%s", String.valueOf (payable_amount)));

        negonametxt = (TextView) findViewById (R.id.nego_payname);
        negonametxt.setText (negoname);

        procced = (Button) findViewById (R.id.complete_pay);

        procced.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(proceed_payment (payable_amount)== 1)
                {
                   Intent intent = new Intent (finalpage.this,RateandReview.class);
                   intent.putExtra ("nego_user",nego_user);
                   startActivity (intent);
                }
            }
        });


    }


    //########################### function to complete payment
    private int proceed_payment(final double amount_pay) {


        try {
            details = new NegotiatorDetails ();
            ShopperDetails shopperDetails = (ShopperDetails)getIntent ().getSerializableExtra ("shopper_details");
            final double[] temp = {Double.parseDouble (shopperDetails.getAmount ()) - amount_pay};
            String am = String.valueOf (temp[0]);

            mroot = FirebaseDatabase.getInstance ().getReference ().child ("Shopper").child (firebaseUser.getUid ()).child ("amount");
            mroot.setValue (am);
            Log.v ("final_amount",am);



final DatabaseReference mroot = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator").child (nego_user).child ("amount");
            databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator").child (nego_user);
          databaseReference.addListenerForSingleValueEvent (new ValueEventListener () {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  // details = new NegotiatorDetails ();
                  details = dataSnapshot.getValue (NegotiatorDetails.class);
                  existing_amount = Double.parseDouble (details.getAmount ());
                  existing_amount = existing_amount + amount_pay;
                  Log.v("amounttest", String.valueOf (existing_amount));
                  mroot.setValue (String.valueOf (existing_amount));
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });

//            databaseReference.addValueEventListener (new ValueEventListener () {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//        // update in negotiators wallet
//


//
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

            Log.v ("hello", String.valueOf (existing_amount));
            Toast.makeText (finalpage.this, "Payment completed", Toast.LENGTH_LONG).show ();
            Log.v ("intel", nego_user);




            DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance ().getReference ();
            mdatabaseReference.child ("Transactions").child (nego_user);


            //go to transactions in both
            Query query2 = mdatabaseReference.child ("Transactions").child (nego_user);
            Log.v ("ASUS", query2.toString ());
            query2.addChildEventListener (new ChildEventListener () {
                @Override
                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists () && check) {
                        nego_transaction_id = dataSnapshot.getKey ();
                        Log.v ("manas", nego_transaction_id);
                        transactionsDetails = dataSnapshot.getValue (TransactionsDetails.class);
                        if (transactionsDetails.getStatus ().compareToIgnoreCase ("completed") != 0) {
                            Log.v ("manas", transactionsDetails.getCreditedToName ());
                            transactionsDetails.setStatus ("Completed");
                            transactionsDetails.setAmount (String.valueOf (message));
                            FirebaseDatabase.getInstance ().getReference ().child ("Transactions").child (nego_user).child (nego_transaction_id).setValue (transactionsDetails);
                            check = false;
                        }
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
            Query query = FirebaseDatabase.getInstance ().getReference ().child ("Transactions").child (shop_user);
            query.addChildEventListener (new ChildEventListener () {
                @Override
                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists () && check2) {
                        shop_transaction_id = dataSnapshot.getKey ();
                        Log.v ("manas", shop_transaction_id);


                        transactionsDetails = dataSnapshot.getValue (TransactionsDetails.class);
                        if (transactionsDetails.getStatus ().compareToIgnoreCase ("completed") != 0) {
                            Log.v ("manas", transactionsDetails.getDebitedFromName ());
                            transactionsDetails.setStatus ("Completed");
                            transactionsDetails.setAmount (String.valueOf (message));
                            FirebaseDatabase.getInstance ().getReference ().child ("Transactions").child (shop_user).child (shop_transaction_id).setValue (transactionsDetails);
                            check2 = false;
                        }

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

           return 1;
        } catch (NumberFormatException e) {
            return 0;

        }

    }
}



//###############################


