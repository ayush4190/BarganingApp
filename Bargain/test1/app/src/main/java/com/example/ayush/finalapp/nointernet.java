package com.example.ayush.finalapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class nointernet extends AppCompatActivity {
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_nointernet);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh.setRefreshing(true);
                connectivityManager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getActiveNetworkInfo ()!=null)
                {
                    startActivity (new Intent (nointernet.this,ShopperHomepage.class));
                    finish ();
                }

                //pullToRefresh.setRefreshing(true);
            }
        });


    }
}
