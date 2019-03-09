package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopperCardDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_frag_shopper);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        Intent i=getIntent();
        ShopperDetails n=(ShopperDetails) i.getSerializableExtra("shop_data");
        final String pos=(String)i.getStringExtra("pos");
        int favbool=(int)i.getIntExtra("favbool",0);
        TextView first_name=(TextView)findViewById(R.id.first_name);
        TextView last_name=(TextView)findViewById(R.id.last_name);
        ImageView pro_image = (ImageView) findViewById(R.id.sportsImageshopper);
        TextView age=(TextView)findViewById(R.id.card_age);
        TextView username=(TextView)findViewById(R.id.card_username);

        first_name.setText(n.getFname());
        last_name.setText(n.getLname());
        age.setText(n.getDob());
        username.setText(n.getEmail());


        //

        final ImageView fav = (ImageView) findViewById(R.id.fav);
        final  ImageView favdone=(ImageView)findViewById(R.id.favdone);

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
                favourite.child("Community").push().setValue(negokey);

//
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
                Query qremove = favourite.child("Community").orderByValue().equalTo(negokey);
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

        //
        ImageView backButton = (ImageView) findViewById(R.id.backarrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //
    }
}
