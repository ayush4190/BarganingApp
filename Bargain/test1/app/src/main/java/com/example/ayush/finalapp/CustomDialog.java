package com.example.ayush.finalapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button yes, no;
    String uid ;


    public CustomDialog(Activity a ,String uid) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.uid = uid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes: {
                Toast.makeText (getContext (),"Payment comleted",Toast.LENGTH_LONG).show ();

                c.finish ();
                break;
            }
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
    private void proceed_payment() {
        try {
                NegotiatorDetails details = new NegotiatorDetails ();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance ().getReference ();
            int temp = Integer.parseInt (details.getAmount () + 50);
            details.setAmount (String.valueOf (temp));
            databaseReference.child (uid).child ("amount").setValue (details.getAmount ());
        }catch (NumberFormatException e)
        {

        }

    }

}


