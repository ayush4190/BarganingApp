package com.example.ayush.finalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class qrdisplay extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ImageView imageView;
    TextView name;
    String temp;
    NegotiatorDetails nd;

    FragmentActivity fragmentActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.qrdisplay, null);
    }
    @SuppressLint("ValidFragment")
    public qrdisplay(FragmentActivity fragmentActivity) {
        this.fragmentActivity =fragmentActivity;
    }

    public qrdisplay() {
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);





        imageView = (ImageView)view.findViewById (R.id.im_qr);
        ////

        name = (TextView)view.findViewById(R.id.qr_name);


        ////
        mAuth = FirebaseAuth.getInstance ();
        mUser = mAuth.getCurrentUser ();
        assert mUser != null;
        temp = mUser.getUid ();
        WindowManager windowManager = (WindowManager) fragmentActivity.getSystemService (Context.WINDOW_SERVICE);
        assert windowManager != null;
        Display display = windowManager.getDefaultDisplay ();
        Point point = new Point ();
        display.getSize (point);
        int x = point.x;
        int y = point.y;
        nd = new NegotiatorDetails();

        int icon = x < y ? x : y;
        icon = icon * 3 / 4;

        DatabaseReference dr;
        dr = FirebaseDatabase.getInstance().getReference().child("Negotiator").child(mUser.getUid());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nd = dataSnapshot.getValue(NegotiatorDetails.class);
                name.setText(nd.getFirstname() + " " + nd.getLastname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //now using the library
      QRCodeEncoder qrCodeEncoder = new QRCodeEncoder (mUser.getUid (), null, com.example.myapplication.Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString (), icon);
        //convert the image into bitmap


        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap ();
            imageView.setImageBitmap (bitmap);
        } catch (
                WriterException e) {
            e.printStackTrace ();
        }
    }
}
