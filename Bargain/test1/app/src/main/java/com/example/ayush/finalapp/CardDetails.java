package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CardDetails extends AppCompatActivity {
    int amount_int;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.card_frag);
        Intent i=getIntent();
        NegotiatorDetails n=(NegotiatorDetails)i.getSerializableExtra("nego_data");
        final String pos=(String)i.getStringExtra("pos");
        int favbool=(int)i.getIntExtra("favbool",0);
        TextView first_name=(TextView)findViewById(R.id.first_name);
        TextView last_name=(TextView)findViewById(R.id.last_name);
        TextView card_phone=(TextView)findViewById(R.id.card_phone);
        TextView card_cat1=(TextView)findViewById(R.id.card_cat1);
        TextView card_cat2=(TextView)findViewById(R.id.card_cat2);
        TextView card_cat3=(TextView)findViewById(R.id.card_cat3);
        TextView card_email=(TextView)findViewById(R.id.card_email);
        TextView card_city=(TextView)findViewById(R.id.card_city);
        TextView card_pincode=(TextView)findViewById(R.id.card_pincode);
        TextView card_state=(TextView)findViewById(R.id.card_state);
        RatingBar ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        CardView pro_image = (CardView)findViewById(R.id.sportsImage);

        first_name.setText(n.getFirstname());
        last_name.setText(n.getLastname());
        card_cat1.setText(n.getCategory1());
        card_cat2.setText(n.getCategory2());
        card_cat3.setText(n.getCategory3());
        card_phone.setText(n.getPhone());
        card_email.setText(n.getEmail());
        card_pincode.setText(n.getPincode());
        card_state.setText(n.getState());
        card_city.setText(n.getCity());
        ratingBar.setRating(3.5f);

        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.parent_of_card);

        //set background according to the category 1
        String cat1=n.getCategory1();
        if(cat1.compareToIgnoreCase("Electronics")==0){
            relativeLayout.setBackgroundResource(R.drawable.electronics);
        }
        else if(cat1.compareToIgnoreCase("Furniture")==0){
            relativeLayout.setBackgroundResource(R.drawable.furniture);
        }
        else if(cat1.compareToIgnoreCase("Jewellery")==0){
            relativeLayout.setBackgroundResource(R.drawable.jewellery);
        }
        else if(cat1.compareToIgnoreCase("Groceries")==0){
            relativeLayout.setBackgroundResource(R.drawable.groceries);
        }

        //go back on above back button press
        ImageView backButton = (ImageView) findViewById(R.id.backarrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //open chat activity on chatbutton click

        //favourite button on click

                final ImageView fav = (ImageView) findViewById(R.id.fav);
                final  ImageView favdone=(ImageView)findViewById(R.id.favdone);
        ImageView chatButton = (ImageView)findViewById(R.id.message_button);

                if(favbool==1){
                    fav.setVisibility(View.GONE);
                    favdone.setVisibility(View.VISIBLE);
                }

                fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fav.setVisibility(View.GONE);
                        favdone.setVisibility(View.VISIBLE);
                        String negokey = pos;
                        Log.v("fsgfht",pos);
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference favourite;
                        favourite=databaseReference.child("Shopper").child(firebaseUser.getUid());
                        favourite.child("Favourite").push().setValue(negokey);
                    }
                });

                favdone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favdone.setVisibility(View.GONE);
                        fav.setVisibility(View.VISIBLE);
                        String negokey = pos;
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference favourite;
                        favourite = databaseReference.child("Shopper").child(firebaseUser.getUid());
                        Query qremove = favourite.child("Favourite").orderByValue().equalTo(negokey);
                        qremove.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                                    itemSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                });

                chatButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                        Query d=databaseReference.child("Negotiator").child(pos).child("Amount");
                      d.addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              String amount="";
                              amount =dataSnapshot.getValue(String.class);
//                              Log.v("amount",amount);
                              amount_int =Integer.parseInt(amount);
                              if (amount_int>=50){
                                  amount_int-=50;
                                  amount= String.valueOf(amount_int);
                              }
                            databaseReference.child("Negotiator").child(pos).child("Amount").setValue(amount);
                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {

                          }
                      });




                    }
                });


    }
}
