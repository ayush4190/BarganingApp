package com.example.ayush.finalapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class previouspaymentsnego extends Fragment {
    private List<TransactionsDetails> negotiatorList = new ArrayList<>();

    private RecyclerView recyclerView;
    private PrevPaymentAdapterNego adapter;
    FirebaseDatabase firebaseDatabase;
    TransactionsDetails transactionsDetails;
    FirebaseUser  firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_previouspaymentsnego, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_passbooknego);//
        adapter = new PrevPaymentAdapterNego(negotiatorList,getActivity ());

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser ();

        GetDataFireBase();

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());//
        recyclerView.setLayoutManager(mLayoutManager);//n

        adapter.notifyDataSetChanged();
    }


    void GetDataFireBase(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Transactions").child(firebaseUser.getUid());

        Query q=databaseReference;
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    transactionsDetails=dataSnapshot.getValue(TransactionsDetails.class);
                    adapter.addItem(transactionsDetails);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
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


    }
}
