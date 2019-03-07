package com.example.ayush.finalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CardDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.card_frag);
    }
}
