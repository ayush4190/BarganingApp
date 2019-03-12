package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrevPaymentAdapter extends RecyclerView.Adapter<PrevPaymentAdapter.PrevPaymentViewHolder> implements Serializable {
///

    private List <TransactionsDetails> transactionDetailsList;
    FragmentActivity mContext;


//    private List <ShopperDetails> shopperProfileList;
//    private List <String> s;
    //    static View v1;
//    CardFrag cardFrag;
//    AlertDialog.Builder builder2;
//    public TextView fname;
//    public TextView city;
//    public TextView lname;
//    public TextView pincode;
//    public TextView cat1;
//    public TextView cat2;
//    public TextView cat3;
//    public TextView phno;

//    StorageReference photo_storage;
//    int favbool;
    //   // FragmentActivity mContext;
//    Bundle mBundle;

    public static TransactionsDetails n;

///

    public PrevPaymentAdapter(List<TransactionsDetails> transactionDetailsList, FragmentActivity f) {
        this.mContext= f;
        this.transactionDetailsList = transactionDetailsList;
    }


    public class PrevPaymentViewHolder extends RecyclerView.ViewHolder {

        public TextView first_name_prev;
        public TextView payment_prev;
        public TextView date_prev;
        public TextView status_prev;




        public PrevPaymentViewHolder(View view) {
            super(view);
            first_name_prev=(TextView)view.findViewById(R.id.first_name_prev);
            payment_prev=(TextView)view.findViewById(R.id.payment_prev);
            date_prev = (TextView)view.findViewById(R.id.date_prev);
            status_prev = (TextView)view.findViewById(R.id.status_prev);
        }
    }

    public PrevPaymentAdapter.PrevPaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previouspaymentcard, parent, false);
        return new PrevPaymentAdapter.PrevPaymentViewHolder(itemView);
    }


    //////////////////////////
    @Override
    public void onBindViewHolder(final PrevPaymentAdapter.PrevPaymentViewHolder holder, final int position) {
        n = transactionDetailsList.get(position);
//        final TransactionsDetails nego = transactionDetailsList.get(position);
//        photo_storage = FirebaseStorage.getInstance ().getReference ().child ("Negotiator_profile_image");

        holder.first_name_prev.setText(n.getCreditedToName());
        holder.status_prev.setText(n.getStatus());
        holder.date_prev.setText(n.getDate());
        holder.payment_prev.setText("- "+n.getAmount()+" ₹");
//        holder.phone.setText(n.getPhone());
//        try {
//
//
//            String location = s.get(position)+ "." + "jpg";
//
//            photo_storage.child (location).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    String imageURL = uri.toString ();
//                    Glide.with (mContext.getApplicationContext()).load (imageURL).into (holder.proimage);
//                }
//            }).addOnFailureListener (new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                    Toast.makeText (mContext.getApplicationContext(), exception.getMessage (), Toast.LENGTH_LONG).show ();
//                }
//            });
//        }catch (NullPointerException e)
//        {
//            Toast.makeText (mContext.getApplicationContext(),e.getMessage (),Toast.LENGTH_LONG).show ();
//        }

//        final TransactionsDetails negotiatorProfile=transactionDetailsList.get(position);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                n = transactionDetailsList.get(position);
//
//                //////////////////////
////use alert dialog
//////////////////
//
////                myIntent.putExtra ("profile", profile);
//
//                Intent intent = new Intent (v.getContext (),CardDetails.class);
//                intent.putExtra("nego_data",n);
//                intent.putExtra("favbool",favbool);
//                intent.putExtra("pos",s.get(position));
//                v.getContext ().startActivity (intent);
//            }
//        });

////////////
//        n=transactionDetailsList.get(position);


    }//bind end

    public void addItem(TransactionsDetails eventsList)
    {
//        this.s.add(t);
        this.transactionDetailsList.add(eventsList);
    }

    @Override
    public int getItemCount() {

        Log.i("Item-Count",Integer.toString(transactionDetailsList.size()));
        return transactionDetailsList.size();
    }


////
}

