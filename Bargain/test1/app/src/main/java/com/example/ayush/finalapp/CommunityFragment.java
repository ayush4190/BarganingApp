package com.example.ayush.finalapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
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

public class CommunityFragment extends Fragment {

    SearchView msearchview;
    EditText msearchtext;
    FirebaseAuth fba;
    FirebaseUser user;
    ImageView cartoon;
    TextView quote;
    int count;
    public ArrayList<String> KeyList;
    private List<ShopperDetails> shopperList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ShopperProfileAdapter adapter;
    private DatabaseReference fdb,shopper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.community_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        msearchview=(SearchView) view.findViewById(R.id.searchcommunity);
        quote = (TextView)view.findViewById(R.id.quotecommunity);
        int idtxt = msearchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        msearchtext = (EditText)view.findViewById(idtxt);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewcommunityfrag);//
        cartoon=(ImageView)view.findViewById(R.id.communitycarton);
        cartoon.setVisibility(View.GONE);
        quote.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        msearchtext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction ();
                fragmentTransaction.replace(R.id.content_frame,new CommunitySearch ());
                fragmentTransaction.addToBackStack ("SearchTagEdit");
                fragmentTransaction.commit ();
                return false;
            }
        });

        msearchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction ();
                fragmentTransaction.replace(R.id.content_frame,new CommunitySearch ());
                fragmentTransaction.addToBackStack ("SearchTag");
                fragmentTransaction.commit ();
            }
        });

        KeyList=new ArrayList<String>();
        count=0;
        adapter = new ShopperProfileAdapter(shopperList,getActivity (),1);
        fdb= FirebaseDatabase.getInstance().getReference();
        fba=FirebaseAuth.getInstance();
        user=fba.getCurrentUser();
        shopper = fdb.child ("Shopper").child (user.getUid ());
        Getdatafirebase();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());//
        recyclerView.setLayoutManager(mLayoutManager);//n
        adapter.notifyDataSetChanged();
    }




    void Getdatafirebase() {
        Query query = shopper;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Community").exists()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    String ss;
                    for (DataSnapshot postSnapshot: dataSnapshot.child("Community").getChildren()) {
                        ss = postSnapshot.getValue(String.class);
                        newfunction(ss);
                    }
                }else {
                    cartoon.setVisibility(View.VISIBLE);
                    quote.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }



    void newfunction(String tt){
        DatabaseReference databaseReference;
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Shopper");
        Query query2 = databaseReference.orderByKey().equalTo(tt);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    ShopperDetails data = dataSnapshot.getValue(ShopperDetails.class);
                    adapter.addItem(data, dataSnapshot.getKey());
                    adapter.notifyDataSetChanged();
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
