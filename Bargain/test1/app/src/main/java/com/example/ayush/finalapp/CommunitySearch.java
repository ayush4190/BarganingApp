package com.example.ayush.finalapp;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CommunitySearch extends Fragment {
    SearchView msearchview;
    DatabaseReference databaseReference;
    EditText msearchtext;
    String searchtext;
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
        return inflater.inflate(R.layout.community_search_frag, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        msearchview = (SearchView) view.findViewById(R.id.searchcommunitynext);
        KeyList=new ArrayList<String>();
        count=0;
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_community);//
        adapter = new ShopperProfileAdapter(shopperList,getActivity (),0);
        fdb= FirebaseDatabase.getInstance().getReference();
        fba=FirebaseAuth.getInstance();
        user=fba.getCurrentUser();
        shopper = fdb.child ("Shopper").child (user.getUid ());

        int idtxt = msearchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        msearchtext = (EditText) view.findViewById(idtxt);
        msearchtext.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        msearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchtext = msearchview.getQuery().toString();
                GetDataFireBase();
                recyclerView.setAdapter(adapter);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());//
                recyclerView.setLayoutManager(mLayoutManager);//n
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }


    void GetDataFireBase() {

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Shopper");
        Query query=databaseReference.orderByChild("username").equalTo(searchtext);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    ShopperDetails data =dataSnapshot.getValue(ShopperDetails.class);
                    adapter.addItem(data,dataSnapshot.getKey());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
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