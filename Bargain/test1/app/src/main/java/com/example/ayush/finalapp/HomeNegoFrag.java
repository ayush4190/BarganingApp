package com.example.ayush.finalapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeNegoFrag extends Fragment {

    private List<TransactionsDetails> transactionsDetailsList = new ArrayList<>();
    TransactionsDetails transactionsDetails;
    TextView first_name,last_name,count;
   RatingBar ratingBar;
   DatabaseReference databaseReference;
   FirebaseUser firebaseUser;
   NegotiatorDetails negotiatorDetails;

    private RecyclerView recyclerView;
    private PrevPaymentAdapterNego adapter;

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
    recyclerView = (RecyclerView) view.findViewById(R.id.nego_home_recyclerView);
    adapter = new PrevPaymentAdapterNego(transactionsDetailsList,getActivity ());

    adapter.clear();
    recyclerView.setAdapter(adapter);
    firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    databaseReference= FirebaseDatabase.getInstance().getReference().child("Negotiator").child(firebaseUser.getUid());

    fetch_data();
    fetch_meet();


        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());//
        recyclerView.setLayoutManager(mLayoutManager);//n

        adapter.notifyDataSetChanged();
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
//        FirebaseDatabase.getInstance().getReference().child("Transactions").child(firebaseUser.getUid()).
//        if(dataSnapshot.exists())
//        {
//            transactionsDetails=dataSnapshot.getValue(TransactionsDetails.class);
//            adapter.addItem(transactionsDetails);
//            adapter.notifyDataSetChanged();
//            recyclerView.setAdapter(adapter);
//            //   }
//        }

        Query q=FirebaseDatabase.getInstance().getReference().child("Transactions").child(firebaseUser.getUid());

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    transactionsDetails=dataSnapshot.getValue(TransactionsDetails.class);
                    if(transactionsDetails.status.compareTo("pending")==0){
                        adapter.addItem(transactionsDetails);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                     //   }
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

//        q.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists())
//                {
//                    transactionsDetails=dataSnapshot.getValue(TransactionsDetails.class);
//                    adapter.addItem(transactionsDetails);
//                    adapter.notifyDataSetChanged();
//                    recyclerView.setAdapter(adapter);
//                    //   }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//




    }
}
