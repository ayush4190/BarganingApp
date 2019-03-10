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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class qrdisplay extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ImageView imageView;
    String temp;

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

        int icon = x < y ? x : y;
        icon = icon * 3 / 4;
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
