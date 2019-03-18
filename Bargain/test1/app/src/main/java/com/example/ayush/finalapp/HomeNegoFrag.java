package com.example.ayush.finalapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeNegoFrag extends Fragment {


    TextView first_name,last_name,count;
   RatingBar ratingBar;
   DatabaseReference databaseReference;
   FirebaseUser firebaseUser;
   NegotiatorDetails negotiatorDetails;

   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.home_nego_frag,null);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);


   first_name=(TextView)view.findViewById(R.id.first_name);
   last_name=(TextView)view.findViewById(R.id.last_name);
   count=(TextView)view.findViewById(R.id.nego_home_meets);
    ratingBar=(RatingBar) view.findViewById(R.id.ratingBar_nego);
    firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    databaseReference= FirebaseDatabase.getInstance().getReference().child("Negotiator").child(firebaseUser.getUid());

    fetch_data();
    fetch_meet();
   }

    public void fetch_data(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    negotiatorDetails=dataSnapshot.getValue(NegotiatorDetails.class);
                    first_name.setText(negotiatorDetails.getFirstname());
                    last_name.setText(negotiatorDetails.getLastname());
                    count.setText(negotiatorDetails.getCount());
                    ratingBar.setRating(Float.parseFloat(negotiatorDetails.getRatings()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void fetch_meet(){

    }
}
