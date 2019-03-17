package com.example.ayush.finalapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class SearchDisplay extends AppCompatActivity {

    private List<NegotiatorDetails> negotiatorList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NegotiatorProfileAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //String searchvalue = (Searchfrag)getIntent().getExtras("Searchtext");
    String searchvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);
        Bundle bundle = getIntent().getExtras();
        //anime
        if(bundle != null)
            searchvalue = bundle.getString("Searchtext");
        Log.v("Search text" , searchvalue);
        // Toast.makeText(SearchDisplay.this,searchvalue,Toast.LENGTH_SHORT);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);//
        adapter = new NegotiatorProfileAdapter(negotiatorList,this,0);
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.v("before getdata" , "hello ");

        GetDataFireBase();
        Log.v("after getdata" , "hello ");

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());//
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
    }


    void GetDataFireBase() {

//        databaseReference=firebaseDatabase.getReference().child("Negotiator");
////        Query query=databaseReference.child("Negotiator").child("category1").startAt(searchvalue).endAt(searchvalue+'\uf8ff');
//        Query query=databaseReference.orderByChild("category1").equalTo(searchvalue);
//        Log.v("inside query" , "hello ");
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                NegotiatorDetails negotiatorDetails= dataSnapshot.getValue(NegotiatorDetails.class);
//                assert negotiatorDetails!=null;
//                        adapter.addItem(negotiatorDetails);
//                        adapter.notifyDataSetChanged();
//                        recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

/////////single evet listemer
//
//        databaseReference=FirebaseDatabase.getInstance().getReference().child("Negotiator");
////        Query query=databaseReference.child("Negotiator").child("category1").startAt(searchvalue).endAt(searchvalue+'\uf8ff');
//        Query query=databaseReference.orderByChild("category1").equalTo(searchvalue);
//        Log.v("inside query" , "hello ");
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.v("inondata beforeifexists" , "hello ");
//
//                if(dataSnapshot.exists()){
//                    Log.v("inondata after ifexists" , "hello ");
//
//                 for(DataSnapshot issue: dataSnapshot) {
////                        Log.v("inside loop" , "hello ");
//
//                        NegotiatorDetails data = dataSnapshot.getValue(NegotiatorDetails.class);
//                     //   Log.v("display data" , data.getFirstname());
//
//                        adapter.addItem(data);
//                        adapter.notifyDataSetChanged();
//                        recyclerView.setAdapter(adapter);
////                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

/////////////////////////chi;d evemt ghjkl;'
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Negotiator");
//        Query query=databaseReference.child("Negotiator").child("category1").startAt(searchvalue).endAt(searchvalue+'\uf8ff');
        Query query=databaseReference.orderByChild("category1").equalTo(searchvalue);
        Log.v("inside query" , "hello ");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("inondata beforeifexists" , "hello ");

                if(dataSnapshot.exists()){
                    Log.v("inondata after ifexists" , "hello ");

                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                    Log.v("inside loop" , "hello ");

                    NegotiatorDetails data =dataSnapshot.getValue(NegotiatorDetails.class);
                    //    Log.v("display data" , data.getFirstname());
                    adapter.addItem(data,dataSnapshot.getKey());
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

///
        Query query2=databaseReference.orderByChild("category2").equalTo(searchvalue);
        Log.v("inside query" , "hello ");

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("inondata beforeifexists" , "hello ");

                if(dataSnapshot.exists()){
                    Log.v("inondata after ifexists" , "hello ");

                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                    Log.v("inside loop" , "hello ");

                    NegotiatorDetails data =dataSnapshot.getValue(NegotiatorDetails.class);
                    //    Log.v("display data" , data.getFirstname());
                    adapter.addItem(data,dataSnapshot.getKey());
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


        ////

        Query query3=databaseReference.orderByChild("category3").equalTo(searchvalue);
        Log.v("inside query" , "hello ");

        query3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("inondata beforeifexists" , "hello ");

                if(dataSnapshot.exists()){
                    Log.v("inondata after ifexists" , "hello ");

                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                    Log.v("inside loop" , "hello ");

                    NegotiatorDetails data =dataSnapshot.getValue(NegotiatorDetails.class);
                    //    Log.v("display data" , data.getFirstname());
                    adapter.addItem(data,dataSnapshot.getKey());
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


//        databaseReference=FirebaseDatabase.getInstance().getReference().child("Negotiator");
//////
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                NegotiatorDetails data=new NegotiatorDetails();
//                data= dataSnapshot.getValue(NegotiatorDetails.class);
//                Log.i("data",data.getFirstname());
//                adapter.addItem(data);
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
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

//        databaseReference=FirebaseDatabase.getInstance().getReference().child("Negotiator");

    }
}

//

//
//
//        mUserDatabase = FirebaseDatabase.getInstance().getReference();
//
//
//        //    recyclerView.setAdapter(fbradapter);
//        mSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String searchResult = mSearchField.getText().toString();
//                firebaseUserSearch(searchResult);
//            }
//        });
//
//
//        //
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                negotiatorList.clear();
//
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        NegotiatorProfile negotiatorProfile = snapshot.getValue(NegotiatorProfile.class);
//                        negotiatorList.add(negotiatorProfile);
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//
//        mUserDatabase.addValueEventListener(valueEventListener);
//    }
//
//        private Query firebaseUserSearch (String searchResult){
//            Toast.makeText(SearchDisplay.this, "Started Search", Toast.LENGTH_LONG).show();
//            Query firebaseSearchQuery = (Query) mUserDatabase.orderByChild("Negotiator").startAt(searchResult).endAt(searchResult + "\uf8ff");
//            return firebaseSearchQuery;
//        }
////        FirebaseRecyclerOptions<NegotiatorProfile> options =
////                new FirebaseRecyclerOptions.Builder<NegotiatorProfile>()
////                        .setQuery(firebaseSearchQuery, NegotiatorProfile.class)
////                        .setLifecycleOwner(this)
////                        .build();
//
//
////        fbradapter = new FirebaseRecyclerAdapter<NegotiatorProfile, NegotiatorProfileViewHolder>(options) {
////            @NonNull
////            @Override
////            public NegotiatorProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////                return new NegotiatorProfileViewHolder(LayoutInflater.from(parent.getContext())
////                        .inflate(R.layout.search_list_layout, parent, false));
////            }
////
////            @Override
////
////            protected void onBindViewHolder(@NonNull NegotiatorProfileViewHolder holder,int position, @NonNull NegotiatorProfile model)
////            {
////                holder.setDetails(model.getFirstname(),model.getLastname(),model.getPhno());
////            }
////
////        };
//
//        //  mResultList.setAdapter(fbradapter);
//
//
//}