package com.example.ayush.finalapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class NegotiatorWallet extends Fragment {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ImageView imageView;
    FragmentActivity f;
    String temp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.nego_wallet,null);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);



        f=getActivity ();

        mAuth = FirebaseAuth.getInstance ();
        mUser = mAuth.getCurrentUser ();
        assert mUser != null;
        temp = mUser.getUid ();
        Button button1 = (Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrdisplay next= new qrdisplay (f);
                f.getSupportFragmentManager ().beginTransaction()
                        .replace(R.id.content_frame, next, "qrcode")
                        .addToBackStack(null)
                        .commit();
            }
        });


//        Button button2 = (Button) view.findViewById(R.id.button2);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity ());
//                View mView = getLayoutInflater().inflate(R.layout.activity_add_money,null);
//                final EditText mAmount  = (EditText) mView.findViewById(R.id.add);
//                mBuilder.setView(mView);
//                AlertDialog dialog = mBuilder.create();
//                dialog.show();
//                Button button = (Button) mView.findViewById(R.id.button4);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TextView textView = (TextView) view.findViewById(R.id.amount);
//                        textView.setText(mAmount.getText().toString());
//
//
//                    }
//                });
//                //   mBuilder.setView(mView);
//                //  AlertDialog dialog = mBuilder.create();
//                // dialog.show();
//                dialog.cancel();
//
//                // Intent intent = new Intent(v.getContext(),AddMoney.class);
//                // v.getContext().startActivity(intent);
//            }
//        });
//
//        Button button3 = (Button) view.findViewById(R.id.button3);
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(),PreviousTransactions.class);
//                v.getContext().startActivity(intent);
//            }
//        });

    }
//    public void generate()
//    {
//
//        WindowManager windowManager = (WindowManager)getActivity ().getSystemService (Context.WINDOW_SERVICE);
//        assert windowManager != null;
//        Display display =windowManager.getDefaultDisplay ();
//        Point point = new Point ();
//        display.getSize (point);
//        int x = point.x;
//        int y = point.y;
//
//        int icon = x<y? x:y;
//        icon = icon * 3/4;
//        //now using the library
//        com.example.myapplication.QRCodeEncoder qrCodeEncoder = new com.example.myapplication.QRCodeEncoder (temp,null, com.example.myapplication.Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString (),icon);
//        //convert the image into bitmap
//
//
//
//        try {
//            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap ();
//            imageView.setImageBitmap (bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace ();
//        }
//
//    }
}



