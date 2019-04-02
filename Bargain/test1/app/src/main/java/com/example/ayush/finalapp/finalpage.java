package com.example.ayush.finalapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
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

import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class finalpage extends AppCompatActivity implements Serializable {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String name;
    NegotiatorDetails details;
    TransactionsDetails transactionsDetails;
    String nego_user;
    String shop_user;
    TextView negonametxt;
    TextView amountpaytxt;
    String message_sent;
    Button procced;
    String message;
    AlertDialog.Builder builder2;
    double existing_amount;
    MeetDetails meetDetails;
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
                   finish ();
                   startActivity (intent);
                   finish ();
                }
            }
        });
    }


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
                  details = dataSnapshot.getValue (NegotiatorDetails.class);
                  existing_amount = Double.parseDouble (details.getAmount ());
                  existing_amount = existing_amount + amount_pay;
                  mroot.setValue (String.valueOf (existing_amount));
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {
              }
          });

            Toast.makeText (finalpage.this, "Payment completed", Toast.LENGTH_LONG).show ();
            //go to transactions in both and using meetdetails nego transaction id and shopper transaction id update transactions for both
            //also need the amount of transaction here
            DatabaseReference p=FirebaseDatabase.getInstance().getReference().child("Shopper").child(ShopperHomepage.shopper_uid).child("meet").child(nego_user);
            p.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        meetDetails=dataSnapshot.getValue(MeetDetails.class);
                        final String dd=String.valueOf(amount_pay);
                        TransactionsDetails transactionsDetails=new TransactionsDetails(ShopperHomepage.shopper_uid,nego_user,meetDetails.negoname,meetDetails.date,"completed",dd,ShopperHomepage.shopper_name);
                        FirebaseDatabase.getInstance().getReference().child("Transactions").child(ShopperHomepage.shopper_uid).child(meetDetails.getTransaction_id_shopper()).setValue(transactionsDetails);
                        FirebaseDatabase.getInstance().getReference().child("Transactions").child(nego_user).child(meetDetails.getTransaction_id_nego()).setValue(transactionsDetails);
                        message_sent=transactionsDetails.debitedFromName+" paid you ₹ "+dd+" .";
                        sendNotification();
                    }
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



    //onesignal notification
    public void sendNotification()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;
                    send_email=nego_user;

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic OTk2YzU1NDktMzE5Yi00MGEwLTk0NDMtYzZkMzI1YzNjM2Jj");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"7b482b83-38de-4653-ba78-c04403c3b4c9\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"USER_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"
                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \""+message_sent+"\"}"
                                + "}";
                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }

}