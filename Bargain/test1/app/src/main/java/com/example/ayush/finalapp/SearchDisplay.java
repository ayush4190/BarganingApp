package com.example.ayush.finalapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class SearchDisplay extends AppCompatActivity {


    private List<NegotiatorProfile> negotiatorList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NegotiatorProfileAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);//

        adapter = new NegotiatorProfileAdapter(negotiatorList);
        firebaseDatabase = FirebaseDatabase.getInstance();
        GetDataFireBase();

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());//
        recyclerView.setLayoutManager(mLayoutManager);//


        adapter.notifyDataSetChanged();

    }


    void GetDataFireBase() {
/////////
        databaseReference=firebaseDatabase.getReference();



        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                NegotiatorProfile data=new NegotiatorProfile();
                data= dataSnapshot.getValue(NegotiatorProfile.class);

                //Log.i("data",data.title);
                adapter.addItem(data);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
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