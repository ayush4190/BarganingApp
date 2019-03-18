package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PayementActivity extends Fragment {
    private static final int TEZ_REQUEST_CODE = 123;
    FragmentActivity f;
    TextView textView;
FirebaseUser firebaseUser;
DatabaseReference databaseReference;
EditText editText ;
boolean bool;
    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.payment_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        textView = (TextView) view.findViewById (R.id.amount);
        firebaseUser= FirebaseAuth.getInstance ().getCurrentUser ();
        databaseReference=  FirebaseDatabase.getInstance ().getReference ().child ("Shopper").child (firebaseUser.getUid ());
        databaseReference.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ShopperDetails details = dataSnapshot.getValue (ShopperDetails.class);
                Log.v ("amount",details.getAmount ());

                textView.setText (String.format ("₹%s", details.getAmount ()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TextView tez = (TextView)view.findViewById (R.id.tez);
       final TextView token = (TextView)view.findViewById (R.id.token);
       final TextView paytm = (TextView)view.findViewById (R.id.paytm);

        CardView cardView=(CardView) view.findViewById (R.id.init_payment_card);
        cardView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tez.setVisibility (View.VISIBLE);
                token.setVisibility (View.VISIBLE);
                paytm.setVisibility (View.VISIBLE);



            }
        });





        token.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getActivity (),QrCode.class));

            }
        });

        tez.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

//                Uri uri =
//                        new Uri.Builder()
//                                .scheme("upi")
//                                .authority("pay")
//                                .appendQueryParameter("pa", "a.ayushkumar1997@okaxis")
//                                .appendQueryParameter("pn", "Test Merchant")
//                                .appendQueryParameter("mc", "1234")
//                                .appendQueryParameter("tr", "123456789")
//                                .appendQueryParameter("tn", "test transaction note")
//                                .appendQueryParameter("am", "10.01")
//                                .appendQueryParameter("cu", "INR")
//                                .appendQueryParameter("url", "a.ayushkumar1997@okaxis")
//                                .build();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(uri);
//                intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
//                startActivityForResult(intent, TEZ_REQUEST_CODE);
                Toast.makeText (getActivity (),"in trail mode ",Toast.LENGTH_SHORT).show ();

            }
        });
//
//
       TextView moneytransfer = (TextView)view.findViewById (R.id.money_transfer) ;
       moneytransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder (v.getContext ());
//                View mView = getLayoutInflater ().inflate (R.layout.activity_add_money, null);
//                final EditText mAmount = (EditText) mView.findViewById (R.id.add);
//                mBuilder.setView (mView);
//                AlertDialog dialog = mBuilder.create ();
//
//                dialog.show ();
//
////
//                Button button = (Button) mView.findViewById(R.id.button4);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TextView textView = (TextView) v.findViewById (R.id.amount);
//                        textView.setText(mAmount.getText().toString());
//
//
//                    }
//                });
//
//                dialog.cancel();

                //#########

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View alertDialogView = inflater.inflate(R.layout.addmoney, null);
                alertDialog.setView(alertDialogView);

                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Shopper").child (firebaseUser.getUid ()).child ("amount");
                        databaseReference.addListenerForSingleValueEvent (new ValueEventListener () {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                editText =(EditText) alertDialogView.findViewById (R.id.amount_edit);
                                String temp = editText.getText ().toString ();
                                Log.v ("its done",temp);
                                String present = (String) dataSnapshot.getValue ();
                                Log.v ("present_amount",present);
                                double up = Double.parseDouble (temp)+ Double.parseDouble (present);
                                Log.v ("done",String.valueOf (up));
                                databaseReference.setValue (String.valueOf (up));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        dialog.cancel();
                    }
                });
                alertDialog.show();
                //#########


            }
        });

         f = getActivity ();

        TextView passbook =(TextView) view.findViewById (R.id.passbook);
        passbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(),PreviousTransactions.class);
//                v.getContext().startActivity(intent);
                assert f != null;
                previouspayment_fragment next= new previouspayment_fragment ();
                f.getSupportFragmentManager ().beginTransaction()
                        .replace(R.id.content_frame, next, "passbook")
                        .addToBackStack(null)
                        .commit();

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE) {
            // Process based on the data in response.
            Log.d("result", data.getStringExtra("Status"));
        }
    }

}





