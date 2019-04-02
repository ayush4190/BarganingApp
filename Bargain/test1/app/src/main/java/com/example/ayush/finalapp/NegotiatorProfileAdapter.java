package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class NegotiatorProfileAdapter extends RecyclerView.Adapter<NegotiatorProfileAdapter.NegotiatorProfileViewHolder> implements Serializable {

    private List <NegotiatorDetails> negotiatorProfileList;
    private List <String> s;
    AlertDialog.Builder builder2;
    public TextView fname;
    public TextView city;
    public TextView lname;
    public TextView pincode;
    public TextView cat1;
    public TextView cat2;
    public TextView cat3;
    public TextView phno;
    FragmentActivity mContext;
    StorageReference photo_storage;
    int favbool;
    //   // FragmentActivity mContext;
    Bundle mBundle;

    public static NegotiatorDetails n;

    public NegotiatorProfileAdapter(List<NegotiatorDetails> negotiatorProfileList,FragmentActivity f,int favbool) {
        this.mContext= f;
        this.negotiatorProfileList = negotiatorProfileList;
        this.s= new ArrayList<String>();
        this.favbool=favbool;
    }



    public class NegotiatorProfileViewHolder extends RecyclerView.ViewHolder {

        public TextView first;
        ImageButton imgbutton;
        public TextView last;
        TextView city,rating;
        CircleImageView proimage;
        TextView age;
        CardView cardView;
        public TextView phone;



        public NegotiatorProfileViewHolder(View view) {
            super(view);
            rating=(TextView)view.findViewById(R.id.rate_search_list);
            city=(TextView) view.findViewById(R.id.city_search_list);
            first =(TextView) view.findViewById(R.id.first_name);
            last =(TextView) view.findViewById(R.id.last_name);
            age=(TextView)view.findViewById(R.id.search_list_age);

            cardView=(CardView) view.findViewById(R.id.card_view);
            proimage = (CircleImageView)view.findViewById(R.id.search_list_sportsImage);

        }
    }

    public NegotiatorProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_layout, parent, false);
        return new NegotiatorProfileViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final NegotiatorProfileViewHolder holder, final int position) {
        n = negotiatorProfileList.get(position);
        final NegotiatorDetails nego =negotiatorProfileList.get(position);
        photo_storage = FirebaseStorage.getInstance ().getReference ().child ("Negotiator_profile_image");
        holder.first.setText(n.getFirstname());
        holder.last.setText(n.getLastname());
        holder.rating.setText(n.getRatings());
        holder.city.setText(n.getCity());
        Double x;
        if(Double.parseDouble(n.getRequestno())==0.0){
            x=100.0;
        }else{

            x=(Double.parseDouble(n.getAcceptno())/Double.parseDouble(n.getRequestno()))*100;

        }
        holder.age.setText(String.valueOf(x)+" %");
//        holder.phone.setText(n.getPhone());
        try {


            String location = s.get(position)+ "." + "jpg";

            photo_storage.child (location).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener <Uri> () {
                @Override
                public void onSuccess(Uri uri) {
                    String imageURL = uri.toString ();
                    Glide.with (mContext.getApplicationContext()).load (imageURL).into (holder.proimage);
                }
            }).addOnFailureListener (new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText (mContext.getApplicationContext(), exception.getMessage (), Toast.LENGTH_LONG).show ();
                }
            });
        }catch (NullPointerException e)
        {
            Toast.makeText (mContext.getApplicationContext(),e.getMessage (),Toast.LENGTH_LONG).show ();
        }
        final NegotiatorDetails negotiatorProfile=negotiatorProfileList.get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = negotiatorProfileList.get(position);
                Intent intent = new Intent (v.getContext (),CardDetails.class);
                intent.putExtra("nego_data",n);
                intent.putExtra("favbool",favbool);
                intent.putExtra("pos",s.get(position));
                v.getContext ().startActivity (intent);
            }
        });
        n=negotiatorProfileList.get(position);

    }
    public void addItem(NegotiatorDetails eventsList,String t)
    {
        this.s.add(t);
        this.negotiatorProfileList.add(eventsList);
    }

    @Override
    public int getItemCount() {

        Log.i("Item-Count",Integer.toString(negotiatorProfileList.size()));
        return negotiatorProfileList.size();
    }
    public void clear(){
        negotiatorProfileList.clear();
        notifyDataSetChanged();
    }
}