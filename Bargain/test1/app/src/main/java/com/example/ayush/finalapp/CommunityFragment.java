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
import android.widget.SearchView;

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

public class CommunityFragment extends Fragment {

    SearchView msearchview;
    EditText msearchtext;
    FirebaseAuth fba;
    FirebaseUser user;
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
        int idtxt = msearchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        msearchtext = (EditText)view.findViewById(idtxt);
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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewcommunityfrag);//
        adapter = new ShopperProfileAdapter(shopperList,getActivity (),1);
        fdb= FirebaseDatabase.getInstance().getReference();
        fba=FirebaseAuth.getInstance();
        user=fba.getCurrentUser();
        shopper = fdb.child ("Shopper").child (user.getUid ());

        //shopper
        Log.v("before getdata" , "hello ");

        Getdatafirebase();
//        newfunction();
        Log.v("after getdata" , "hello ");

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());//
        recyclerView.setLayoutManager(mLayoutManager);//n
        adapter.notifyDataSetChanged();
        //


    }
    void Getdatafirebase() {
        //

        // databaseReference=FirebaseDatabase.getInstance().getReference().child("Shopper");
//        Query query=databaseReference.child("Shopper").child("category1").startAt(searchvalue).endAt(searchvalue+'\uf8ff');
        Query query = shopper.child("Community");
        Log.v("inside query 1", "hello ");

        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("inondata beforeifexists", "hello ");

                if (dataSnapshot.exists()) {
                    Log.v("inondata after ifexists", "hello ");


                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                    Log.v("inside loop", "hello ");
                    String ss = dataSnapshot.getValue().toString();
                    //
                    Log.v("before keylist add", ss);
//                    KeyList.add(count,ss);
//                    KeyList.add(ss);
//                    count++;

                    newfunction(ss);
//                    Log.v("after keylist add", KeyList.get(count-1));
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

//        Log.v("out funcytion", KeyList.get(count-1));
    }

    void newfunction(String tt){
        Log.v("starting newfunction", "hello ");
        DatabaseReference databaseReference;
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Shopper");
//        Log.v("size of keylist", KeyList.size()+"");
//            Log.v("ke", KeyList.get(0));
//        for(int i=0;i<KeyList.size();i++) {
        Log.v("after loop for loop", "hello ");
        Query query2 = databaseReference.orderByKey().equalTo(tt);

        Log.v("after query 2", "hello ");

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("inondata beforeifexists", "hello ");

                if (dataSnapshot.exists()) {
                    Log.v("inondata after ifexists", "hello ");

                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                    Log.v("gamma","fghg" );
//                        dataSnapshot.getValue(ShopperDetails.class);
                    ShopperDetails data = dataSnapshot.getValue(ShopperDetails.class);
//                            Log.v("display data" , dataSnapshot.ge);

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
