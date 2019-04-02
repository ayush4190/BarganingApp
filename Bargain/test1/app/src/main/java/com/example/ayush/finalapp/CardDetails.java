package com.example.ayush.finalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.ayush.finalapp.ChatFragmentNego.list;

public class CardDetails extends AppCompatActivity {
    int amount_int;
    CircleImageView pro_image;
    NegotiatorDetails n;
    boolean boolFav;
    StorageReference photo_storage;
    String location2;
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
        n=(NegotiatorDetails)i.getSerializableExtra("nego_data");
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
        pro_image = (CircleImageView) findViewById(R.id.sportsImage_negoani);

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
        ratingBar.setRating(Float.parseFloat(n.getRatings()));
        fetch(pos);


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


/////////////
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////
                fav.setVisibility(View.GONE);
                favdone.setVisibility(View.VISIBLE);
                final String negokey = pos;
//                Log.v("fsgfht",pos);
//                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                final DatabaseReference favourite;
//                favourite=databaseReference.child("Shopper").child(firebaseUser.getUid());
//                favourite.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(!dataSnapshot.hasChild("Favourite"))
//                        {
//                            Log.v("meaaya","enteredfav");
//                            favourite.child("Favourite").push().setValue(negokey);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//                final DatabaseReference favChild;
//                favChild=favourite.child("Favourite");
//                favChild.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.getValue()!=null)
//                            if((dataSnapshot.getValue().toString().compareToIgnoreCase(negokey))!=0)
//                            {
//                                Log.v("meaayaidhar","2");
//                                favourite.child("Favourite").push().setValue(negokey);
//                            }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//                ///////////
//                      //open chatbox for this nego
                Log.v("lancer1", "0");
                final DatabaseReference mdatabaseReference =FirebaseDatabase.getInstance().getReference().child("Shopper").child(ShopperHomepage.shopper_uid).child("Favourite");

                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.v("lancer2", "0");
                        if(!dataSnapshot.exists()) {
                            //create new entry
                            Log.v("lancer3", "0");
                            mdatabaseReference.child(pos).setValue(pos);

//                                    if (ChatFragmentNego.Opened != 0)
//                                        return;
//                                    ChatFragmentNego.Opened = 1;
                            //important

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                mdatabaseReference.child(pos).addListenerForSingleValueEvent(eventListener);

                //if already there is some chat with this nego, start chat box actvity
                //else add it in the chats fields of this shopper


            }
        });

        //////////////////////////

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
//                      //open chatbox for this nego
                        Log.v("lancer1", "0");
                        final DatabaseReference mdatabaseReference =FirebaseDatabase.getInstance().getReference().child("Shopper").child(ShopperHomepage.shopper_uid).child("nego_chat");

                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.v("lancer2", "0");
                                if(!dataSnapshot.exists()) {
                                    //create new entry

                                    Log.v("lancer3", "0");
                                   DatabaseReference m=FirebaseDatabase.getInstance().getReference().child("Negotiator").child(pos).child("nego_chat");
                                   m.child(ShopperHomepage.shopper_uid).setValue(ShopperHomepage.shopper_uid);
                                   m.child(ShopperHomepage.shopper_uid).child("name").setValue(ShopperHomepage.shopper_name);
                                    mdatabaseReference.child(pos).setValue(pos);
                                    mdatabaseReference.child(pos).child("name").setValue(n.getFirstname()+" "+n.getLastname());
                                    Log.v("lancer3", "0");
//                                    if (ChatFragmentNego.Opened != 0)
//                                        return;
//                                    ChatFragmentNego.Opened = 1;
                                    //important
                                    Log.v("lancer", pos);
                                    //x contains x[0] contains name and x[1] contains uid of that negotiator
                                    String[] x = {n.getFirstname() + " " + n.getLastname(), pos};
                                    String User = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                    Intent intent = new Intent(CardDetails.this, ChatBox.class);
                                    intent.putExtra("User", User);
                                    intent.putExtra("Reciever", x);
                                    intent.putExtra("Number", 0);
                                    startActivity(intent);
                                }else {

//                                    if (ChatFragmentNego.Opened != 0)
//                                        return;
//                                    ChatFragmentNego.Opened = 1;
                                    //important
                                    Log.v("lancer", pos);
                                    //x contains x[0] contains name and x[1] contains uid of that negotiator
                                    String[] x = {n.getFirstname() + " " + n.getLastname(), pos};
                                    String User = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    Intent intent = new Intent(getApplicationContext(), ChatBox.class);
                                    intent.putExtra("User", User);
                                    intent.putExtra("Reciever", x);
                                    intent.putExtra("Number", 0);
                                    startActivity(intent);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };
                        mdatabaseReference.child(pos).addListenerForSingleValueEvent(eventListener);

                        //if already there is some chat with this nego, start chat box actvity
                           //else add it in the chats fields of this shopper


                    }
                });





    }
    public void fetch(String pos) {
        photo_storage = FirebaseStorage.getInstance ().getReference ().child ("Negotiator" +
                "_profile_image");
        try {


            String location = pos + "." + "jpg";
            location2 = pos+"."+"null";
            photo_storage.child(location).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String imageURL = uri.toString();
                    Glide.with(getApplicationContext()).load(imageURL).into(pro_image);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    photo_storage.child (location2).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener <Uri> () {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageurl2 = uri.toString ();
                            Log.v ("url link", imageurl2);
                            Glide.with (getApplicationContext ()).load (imageurl2).into (pro_image);
                            // Toast.makeText(CardDetails.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        } catch (NullPointerException e) {
            Toast.makeText(CardDetails.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
