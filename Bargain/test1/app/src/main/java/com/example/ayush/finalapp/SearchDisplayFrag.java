package com.example.ayush.finalapp;

import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
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

public class SearchDisplayFrag extends Fragment{
    SearchView msearchview;
    EditText msearchtext;
    private List<NegotiatorDetails> negotiatorList = new ArrayList<>();
    private RecyclerView recyclerView;
    ImageView cartoon;
    NegotiatorProfileAdapter adapter;
    TextView quote,textbelow;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //String searchvalue = (Searchfrag)getIntent().getExtras("Searchtext");
    String searchvalue;
    Binder binder;
    int age =0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate (R.layout.search_display_frag,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        msearchview=(SearchView) view.findViewById(R.id.search_in_list);//intialisig searchView
        Bundle bundle = this.getArguments ();
        if(bundle != null)
        {
            searchvalue = getArguments().getString("Searchtext");
            age=Integer.parseInt(getArguments().getString ("Agevalue"));
            //Toast.makeText (getActivity (),"here",Toast.LENGTH_SHORT).show ();

        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);//
        quote=(TextView)view.findViewById(R.id.quotesearch);
        textbelow=(TextView)view.findViewById(R.id.search_text_nomore);
        cartoon=(ImageView)view.findViewById(R.id.search_cartoon);

        recyclerView.setVisibility(View.GONE);
        cartoon.setVisibility(View.VISIBLE);
        quote.setVisibility(View.VISIBLE);
        textbelow.setVisibility(View.GONE);

        //String pos=bundle.getString("filter_result_pos");
        //anime
        // Log.v("filter position" , pos);

        int idtxt = msearchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        msearchtext = (EditText)view.findViewById(idtxt);//initialising the searched text in an edit text

        msearchtext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction ();
                fragmentTransaction.replace(R.id.content_frame,new Searchfrag ());
                fragmentTransaction.addToBackStack ("SearchTagEdit");
                fragmentTransaction.commit ();
                return false;
            }
        });

        msearchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction ();
                fragmentTransaction.replace(R.id.content_frame,new Searchfrag ());
                fragmentTransaction.addToBackStack ("SearchTag");
                fragmentTransaction.commit ();

            }
        });



        Log.v("Serachdis age:",String.valueOf (age));
        Log.v("Searchdis Search text" , searchvalue);
        // Toast.makeText(SearchDisplay.this,searchvalue,Toast.LENGTH_SHORT);


        adapter = new NegotiatorProfileAdapter(negotiatorList,getActivity (),0);
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.v("before getdata" , "hello ");

        GetDataFireBase();
        Log.v("after getdata" , "hello ");
        adapter.clear();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());//
        recyclerView.setLayoutManager(mLayoutManager);//n

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

/////////////////////////chi;d evemt ghjkl;
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Negotiator");
//        Query query=databaseReference.child("Negotiator").child("category1").startAt(searchvalue).endAt(searchvalue+'\uf8ff');
        Query query=databaseReference.orderByChild("category1").equalTo(searchvalue);
        Log.v("inside query 1" , "hello ");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("inondata beforeifexists" , "hello ");

                if(dataSnapshot.exists()){
                    Log.v("inondata after ifexists" , "hello ");
                    recyclerView.setVisibility(View.VISIBLE);
                    textbelow.setVisibility(View.VISIBLE);
                    cartoon.setVisibility(View.GONE);
                    quote.setVisibility(View.GONE);
                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                    Log.v("inside loop" , "hello ");
                    NegotiatorDetails data =dataSnapshot.getValue(NegotiatorDetails.class);
                    if(age==0)
                    {
                        adapter.addItem(data,dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                        Log.v("inside age 0" , "hello ");
                        recyclerView.setAdapter(adapter);
                        //   }
                    }
                    else if(data.getYear ().compareToIgnoreCase (String.valueOf (age))==0){
                        //    Log.v("display data" , data.getFirstname());
                        adapter.addItem(data,dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        //   }
                    }}
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
        Log.v("inside query 2" , "hello ");

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("inondata beforeifexists" , "hello ");

                if(dataSnapshot.exists()){
                    recyclerView.setVisibility(View.VISIBLE);
                    textbelow.setVisibility(View.VISIBLE);
                    cartoon.setVisibility(View.GONE);
                    quote.setVisibility(View.GONE);
                    Log.v("inondata after ifexists" , "hello ");

                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                    Log.v("inside loop" , "hello ");

                    NegotiatorDetails data =dataSnapshot.getValue(NegotiatorDetails.class);
                    //    Log.v("display data" , data.getFirstname());


                    if(age==0)
                    {
                        adapter.addItem(data,dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        //   }
                    }
                    else if(data.getYear ().compareToIgnoreCase (String.valueOf (age))==0){
                        //    Log.v("display data" , data.getFirstname());
                        adapter.addItem(data,dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        //   }
                    }}
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
        Log.v("inside query 3" , "hello ");

        query3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("inondata beforeifexists" , "hello ");

                if(dataSnapshot.exists()){
                    recyclerView.setVisibility(View.VISIBLE);

                    textbelow.setVisibility(View.VISIBLE);
                    cartoon.setVisibility(View.GONE);
                    quote.setVisibility(View.GONE);
                    Log.v("inondata after ifexists" , "hello ");

                    // for(DataSnapshot issue: dataSnapshot.getChildren()) {
                    Log.v("inside loop" , "hello ");

                    NegotiatorDetails data =dataSnapshot.getValue(NegotiatorDetails.class);
                    //    Log.v("display data" , data.getFirstname());


                    if(age==0)
                    {
                        adapter.addItem(data,dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        //   }
                    }
                    else if(data.getYear ().compareToIgnoreCase (String.valueOf (age))==0){
                        //    Log.v("display data" , data.getFirstname());
                        adapter.addItem(data,dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        //   }
                    }}
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


