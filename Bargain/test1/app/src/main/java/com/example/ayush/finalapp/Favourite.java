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
import android.widget.ImageView;
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

public class Favourite extends Fragment {
    FirebaseAuth fba;
    FirebaseUser user;
    int count;
    ImageView cartoon;
    public ArrayList<String> KeyList;
    private List<NegotiatorDetails> negotiatorList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NegotiatorProfileAdapter adapter;
    private DatabaseReference fdb,shopper;
    TextView quote;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.favourite_fragment,null);


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        KeyList=new ArrayList<String>();
        count=0;
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);//
        adapter = new NegotiatorProfileAdapter(negotiatorList,getActivity (),1);
        adapter.clear();

        fdb= FirebaseDatabase.getInstance().getReference();
        fba=FirebaseAuth.getInstance();
        user=fba.getCurrentUser();
        quote = (TextView)view.findViewById(R.id.quotefav) ;
        shopper = fdb.child ("Shopper").child (user.getUid ());
        cartoon=(ImageView)view.findViewById(R.id.favcarton);
        cartoon.setVisibility(View.GONE);
        quote.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

       //shopper
        Log.v("before getdata" , "hello ");
        adapter.clear();
        recyclerView.setAdapter(adapter);

        Getdatafirebase();
//        newfunction();
        Log.v("after getdata" , "hello ");
//        adapter.clear();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());//
        recyclerView.setLayoutManager(mLayoutManager);//n
        adapter.notifyDataSetChanged();
        //

adapter.clear();
    }
    void Getdatafirebase() {
        //
adapter.clear();
        // databaseReference=FirebaseDatabase.getInstance().getReference().child("Negotiator");
//        Query query=databaseReference.child("Negotiator").child("category1").startAt(searchvalue).endAt(searchvalue+'\uf8ff');
        Query query = shopper;
        Log.v("inside query 1", "hello ");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("inondata beforeifexists", "hello ");

                if (dataSnapshot.child("Favourite").exists()) {
                    Log.v("inondata after ifexists", "hello ");


                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                    Log.v("inside loop", "hello ");
//                    Iterable q =dataSnapshot.child("Favourite").getChildren();
                    String ss;
                    for (DataSnapshot postSnapshot: dataSnapshot.child("Favourite").getChildren()) {
                        ss = postSnapshot.getValue(String.class);

                        Log.v("before keylist add", ss);
                        newfunction(ss);
//                        Log.e("Get Data", post.<YourMethod>());
                    }
//                    String[] q=new String[((int) dataSnapshot.getChildrenCount())];

//                    q=dataSnapshot.child("Favouri;

//                    String ss = dataSnapshot.child("Favourite").getValue();
                    //
//                    Log.v("before keylist add", ss);
//                    KeyList.add(count,ss);
//                    KeyList.add(ss);
//
//
//                    count++;

//                    newfunction(ss);
//                    Log.v("after keylist add", KeyList.get(count-1));
                }else{
                    cartoon.setVisibility(View.VISIBLE);
                    quote.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Log.v("aya na tu","aa gaya");}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.v("inondata beforeifexists", "hello ");
//
//                if (dataSnapshot.child("Favourite").exists()) {
//                    Log.v("inondata after ifexists", "hello ");
//
//
//                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
//                    Log.v("inside loop", "hello ");
//                    String ss = dataSnapshot.child("Favourite").getValue().toString();
//                    //
//                    Log.v("before keylist add", ss);
////                    KeyList.add(count,ss);
////                    KeyList.add(ss);
////
////
////                    count++;
//
//                    newfunction(ss);
////                    Log.v("after keylist add", KeyList.get(count-1));
//                }else{
//                    cartoon.setVisibility(View.VISIBLE);
//                    quote.setVisibility(View.VISIBLE);
//                    Log.v("aya na tu","aa gaya");}
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        Log.v("out funcytion", KeyList.get(count-1));
    }

        void newfunction(String tt){
        Log.v("starting newfunction", "hello ");
        DatabaseReference databaseReference;
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Negotiator");
//        Log.v("size of keylist", KeyList.size()+"");
//            Log.v("ke", KeyList.get(0));
//        for(int i=0;i<KeyList.size();i++) {

////////////////////////////

            adapter.clear();
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

/////////////////////////////
            Log.v("after loop for loop", "hello ");
            Query query2 = databaseReference.orderByKey().equalTo(tt);

            Log.v("after query 2", "hello ");

            query2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Log.v("inondata beforeifexists", "hello ");

                    if (dataSnapshot.exists()) {
                        recyclerView.setVisibility(View.VISIBLE);

                        Log.v("inondata after ifexists", "hello ");

                        // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                        Log.v("gamma","fghg" );
//                        dataSnapshot.getValue(NegotiatorDetails.class);
                        NegotiatorDetails data = dataSnapshot.getValue(NegotiatorDetails.class);
//                            Log.v("display data" , dataSnapshot.ge);

                        adapter.addItem(data, dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                   adapter.clear();
                    adapter.notifyDataSetChanged();
recyclerView.setAdapter(adapter);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
adapter.clear();
                    adapter.notifyDataSetChanged();
recyclerView.setAdapter(adapter);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        //
        //
    }
}
