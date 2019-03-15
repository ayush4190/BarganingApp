package com.example.ayush.finalapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.Serializable;

public class QrCode extends AppCompatActivity implements Serializable {
    SurfaceView surfaceView;
    TextView textView;
     CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String name;
    NegotiatorDetails details;
    String nego_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
        textView = (TextView) findViewById(R.id.textView);

        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Negotiator");

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

//        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();
        int i = 0b1010000000;
        int j = 0b111100000;
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize (i,j)
                .setAutoFocusEnabled(true).build();




        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                try {
                    cameraSource.start(holder);

                }catch (IOException e)
                {
                    e.printStackTrace();
                }

            }


            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrcode = detections.getDetectedItems();

                if(qrcode.size() != 0)
                {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(VIBRATOR_SERVICE);



                                vibrator.vibrate (100);


                             nego_user = qrcode.valueAt (0).displayValue;
                            try {
                                databaseReference.child (nego_user).addValueEventListener (new ValueEventListener () {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                         details = new NegotiatorDetails ();
                                        details = dataSnapshot.getValue (NegotiatorDetails.class);
                                        try
                                        {
                                        name = details.getFirstname () + " " + details.getLastname ();

                                            textView.setText (name);
                                            // here open a new fragment here for asking permission about completing the payment
                                            textView.setOnClickListener (new View.OnClickListener () {
                                                @Override
                                                public void onClick(View v) {



                                                      Intent intent = new Intent (QrCode.this,amount_payable.class);
                                                      intent.putExtra ("uid",nego_user);
                                                      startActivity (intent);

                                                }
                                            });
//

                                        }
                                        catch (NullPointerException e){
                                            String temp = "PLEASE FOCUS THE QR CODE";
                                            textView.setText (temp);
                                           // proceed_payment();
                                        }
                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }catch (NullPointerException e)
                            {
                                Log.d("error",e.getMessage ());
                            }


//                            textView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    FragmentTransaction fragmentTransaction = getSupportFragmentManager ().beginTransaction ();
//                                    fragmentTransaction.replace(R.id.content_frame,new FinalpayementFragment ());
//                                    fragmentTransaction.commit ();
//
//                                }
//                            });

                        }
                    });
                }

            }
        });

    }

    private void proceed_payment() {
        try {


            int temp = Integer.parseInt (details.getAmount () + 50);
            details.setAmount (String.valueOf (temp));
            databaseReference.child (nego_user).child ("amount").setValue (details.getAmount ());
        }catch (NumberFormatException e)
        {

        }

    }

}
//////////////////////////  check why the fragment is not working