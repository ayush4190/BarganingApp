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
    int existing_amount;
    String amount_pay;
    double finaly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_amount_payable);

//        textView1 = (TextView) findViewById (R.id.payable_amount);
        editText = (EditText) findViewById (R.id.Amount_pay);
        nego_user = (String) getIntent ().getSerializableExtra ("uid");
        firebaseUser=FirebaseAuth.getInstance ().getCurrentUser ();
        shop_user=firebaseUser.getUid ();
        Log.v ("uid name", nego_user);
//        ok.setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View v) {
//                builder2 = new AlertDialog.Builder (amount_payable.this);
//                LayoutInflater inflater = amount_payable.this.getLayoutInflater ();
//                builder2.setPositiveButton ("Yes", new DialogInterface.OnClickListener () {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        message = editText.getText ().toString ();
//                        proceed_payment ();
//                        check=true;
//                        check2=true;
//
//                    }
//                });
////                final View v1= inflater.inflate(R.layout.meet_nego_frag,null);
////                builder2.setView (v1);
//            }
//        });
        transactionsDetails=new TransactionsDetails ();
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
        ok = (Button)findViewById (R.id.float_pay);
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
check=false;
check2=false;
        ok.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                builder2 = new AlertDialog.Builder (amount_payable.this);
                LayoutInflater inflater = amount_payable.this.getLayoutInflater ();
                builder2.setPositiveButton ("Yes", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        message = editText.getText ().toString ();
                        proceed_payment ();
                        check=true;
                        check2=true;

                    }
                });
                String total_amount = editText.getText ().toString ();
                int temp3 = Integer.parseInt (total_amount);
                 finaly = temp3 * 0.02 ;
                /////
                amount_pay = String.valueOf (finaly);
                //////
                mroot=  FirebaseDatabase.getInstance ().getReference ().child ("Shopper").child (firebaseUser.getUid ());
                mroot.addValueEventListener (new ValueEventListener () {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ShopperDetails details = dataSnapshot.getValue (ShopperDetails.class);
                        Log.v ("amount",details.getAmount ());
                        existing_amount = Integer.parseInt (details.getAmount ());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Log.v ("checking for it", String.valueOf (Double.compare (finaly,1000)));
//                if(Double.compare (finaly,existing_amount)==0)
//                {
//
//                    Toast.makeText (amount_payable.this,"Insufficient Balance"+amount_pay,Toast.LENGTH_LONG).show ();
//                    //startActivity (new Intent (amount_payable.this,ShopperHomepage.class));
//                }
//                else
//                {
//                    amount_pay = String.valueOf (finaly);
//                }
                /// this amount need to be updated

                ////////

                //check for comparision



                /////////
               // builder2.setTitle ("Total payable amount to negotiator =" +String.valueOf (finaly) );
                builder2.setTitle ("Total payable amount to negotiator =" +amount_pay );
                builder2.setNegativeButton ("No", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder2.setCancelable (false);
                AlertDialog alert = builder2.create ();
                alert.show ();
//                final View v1= inflater.inflate(R.layout.meet_nego_frag,null);
//                builder2.setView (v1);
            }
        });

//        ok.setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View v) {
////                 message = editText.getText ().toString ();
////                proceed_payment ();
////                check=true;
////                check2=true;
//            }
//        });


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

            //

//            final DatabaseReference databaseReference1=FirebaseDatabase.getInstance ().getReference ();
                    //
            Log.v("intel",nego_user);
            DatabaseReference mdatabaseReference=FirebaseDatabase.getInstance ().getReference ();
            Query query2=mdatabaseReference.child("Transactions").child(nego_user);
            Log.v("ASUS",query2.toString ());

            query2.addChildEventListener(new ChildEventListener () {
                @Override

                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                    if(dataSnapshot.exists() && check)
                    {
                        nego_transaction_id=dataSnapshot.getKey ();
                        Log.v("manas",nego_transaction_id);
                       transactionsDetails=dataSnapshot.getValue (TransactionsDetails.class);
                       if(transactionsDetails.getStatus ().compareToIgnoreCase ("completed")!=0 ) {
                           Log.v ("manas", transactionsDetails.getCreditedToName ());
                           transactionsDetails.setStatus ("Completed");
                           transactionsDetails.setAmount (message);
                           FirebaseDatabase.getInstance ().getReference ().child ("Transactions").child (nego_user).child (nego_transaction_id).setValue (transactionsDetails);
                            check=false;
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
//            Log.v("manas",transactionsDetails.getCreditedToName ());
//            transactionsDetails.setStatus ("Completed");
//            transactionsDetails.setAmount (message);

            Query query=FirebaseDatabase.getInstance ().getReference ().child("Transactions").child(shop_user);
            query.addChildEventListener(new ChildEventListener () {
                @Override
                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                    if(dataSnapshot.exists() && check2)
                    {
                        shop_transaction_id=dataSnapshot.getKey ();
                        Log.v("manas",shop_transaction_id);
//                        transactionsDetails=dataSnapshot.getValue (TransactionsDetails.class);

                        transactionsDetails=dataSnapshot.getValue (TransactionsDetails.class);
                        if(transactionsDetails.getStatus ().compareToIgnoreCase ("completed")!=0 ) {
                            Log.v ("manas", transactionsDetails.getDebitedFromName ());
                            transactionsDetails.setStatus ("Completed");
                            transactionsDetails.setAmount (message);
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
//
//            Log.v("manas",transactionsDetails.creditedToName);
//            FirebaseDatabase.getInstance ().getReference ().child ("Transactions").child (nego_user).child (nego_transaction_id).setValue (transactionsDetails);
//            FirebaseDatabase.getInstance ().getReference ().child ("Transactions").child (shop_user).child (shop_transaction_id).setValue (transactionsDetails);
            //
            //
//            DatabaseReference databaseReference=FirebaseDatabase.getInstance ().getReference ().child ("Transactions").child (nego_user).
//            Log.v ("final amount",MostRecentIdNegotiatoregotiator);
//                    TransactionsDetails transactionsDetails=new TransactionsDetails (shop_user,nego_user,MostRecentNameNegotiator,MostRecentDate,"completed",String.valueOf (temp1),MostRecentNameShopper);
//            Log.v ("final amount",shop_user);
//            Log.v ("final amount",nego_user);
//            Log.v ("final amount",MostRecentNameShopper);
//            Log.v ("final amount",MostRecentNameNegotiator);
//            Log.v ("final amount",MostRecentDate);


//
//            FirebaseDatabase.getInstance ().getReference ().child ("Transactions").child (shop_user).child (MostRecentIdShopper).setValue (transactionsDetails);
          Intent intent=  new Intent (amount_payable.this,RateandReview.class);
          intent.putExtra ("nego_user",nego_user);
          startActivity (intent);
        }catch (NumberFormatException e)
        {

        }


    }

    }


