package com.example.ayush.finalapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatSearch extends AppCompatActivity implements SearchView.OnQueryTextListener{

    Toolbar toolbar;
    ViewPager pager;
    TabLayout tab;
    List<String[]> people = new ArrayList<>();
    DatabaseReference reference;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        InitializeFields();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//
//        myPagerAdapter adapter = new myPagerAdapter(getSupportFragmentManager(),new PersonFragment(),new GroupSearchFragment());
//        pager.setAdapter(adapter);

        tab.setupWithViewPager(pager);
        tab.getTabAt(0).setText("People");
        tab.getTabAt(1).setText("Groups");

        GetAllPeopleInfo();



        

    }

    private void GetAllPeopleInfo() {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    people.clear();
                    String id = snapshot.getKey();
                    if( !id.equals(userId) && !dataSnapshot.child(userId).child("friend_requests").child(id).exists() &&
                            !dataSnapshot.child(userId).child("sent_requests").child(id).exists()) {
                        String name = snapshot.child("name").getValue(String.class),
                                imageUrl = snapshot.child("image").getValue(String.class);

                        people.add(new String[]{name, id, imageUrl});
                    }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void InitializeFields() {
        toolbar = findViewById(R.id.Search_Toolbar);
        pager = findViewById(R.id.Search_Pager);
        tab = findViewById(R.id.Search_Tab);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_menu,menu);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
