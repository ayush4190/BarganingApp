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
    private List <ShopperDetails> shopperProfileList;
    private List <String> s;
    //    static View v1;
//    CardFrag cardFrag;
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
        TextView city;
        CircleImageView proimage;
        TextView age;
        CardView cardView;
        public TextView phone;



        public NegotiatorProfileViewHolder(View view) {
            super(view);
            city=(TextView) view.findViewById(R.id.city_search_list);
            first =(TextView) view.findViewById(R.id.first_name);
            last =(TextView) view.findViewById(R.id.last_name);
            age=(TextView)view.findViewById(R.id.search_list_age);
//            phone=(TextView) view.findViewById(R.id.ph_no);
            cardView=(CardView) view.findViewById(R.id.card_view);
            proimage = (CircleImageView)view.findViewById(R.id.search_list_sportsImage);
//            imgbutton= (ImageButton) view.findViewById(R.id.card_);

        }
    }

    public NegotiatorProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_layout, parent, false);
        return new NegotiatorProfileViewHolder(itemView);
    }


    //////////////////////////
    @Override
    public void onBindViewHolder(final NegotiatorProfileViewHolder holder, final int position) {
        n = negotiatorProfileList.get(position);
        final NegotiatorDetails nego =negotiatorProfileList.get(position);
        photo_storage = FirebaseStorage.getInstance ().getReference ().child ("Negotiator_profile_image");
        holder.first.setText(n.getFirstname());
        holder.last.setText(n.getLastname());
        holder.city.setText(n.getCity());
        holder.age.setText(n.getYear()+" Yrs");
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

                //////////////////////
//use alert dialog
////////////////

//                myIntent.putExtra ("profile", profile);

                Intent intent = new Intent (v.getContext (),CardDetails.class);
                intent.putExtra("nego_data",n);
                intent.putExtra("favbool",favbool);
                intent.putExtra("pos",s.get(position));
                v.getContext ().startActivity (intent);
//
//                FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager ().beginTransaction ();
//                fragmentTransaction.replace (R.id.content_frame, new CardFrag ());
//                fragmentTransaction.addToBackStack ("cardfrag");
//                fragmentTransaction.commit ();
//
//                builder2 = new AlertDialog.Builder (mContext);
//                LayoutInflater inflater = mContext.getLayoutInflater ();
//                View v1= inflater.inflate(R.layout.card_frag,null);
//                builder2.setView (v1);
//                fname=(TextView) v1.findViewById(R.id.first_name);
//                lname=(TextView) v1.findViewById(R.id.last_name);
//                phno=v1.findViewById(R.id.card_phone);
//                pincode=v1.findViewById(R.id.card_pincode);
//                cat1=v1.findViewById(R.id.card_cat1);
//                cat2=v1.findViewById(R.id.card_cat2);
//                cat3=v1.findViewById(R.id.card_cat3);
//                city=(TextView) v1.findViewById(R.id.card_city);
//                city.setText(n.getCity());
//                fname.setText(n.getFirstname());
//                lname.setText(n.getLastname());
//                pincode.setText(n.getPincode());
//                cat2.setText(n.getCategory2());
//                cat1.setText(n.getCategory1());
//                cat3.setText(n.getCategory3());
//                phno.setText(n.getPhone());
//                builder2.setNegativeButton ("Close", new DialogInterface.OnClickListener () {
////                    public void onClick(DialogInterface dialog, int id) {
////                        dialog.cancel ();
////
////                    }
////                });
////
////
//
//                final ImageView fav = (ImageView) v1.findViewById(R.id.fav);
//                final  ImageView favdone=(ImageView)v1.findViewById(R.id.favdone);
//
//                if(favbool==1){
//                    fav.setVisibility(View.GONE);
//                    favdone.setVisibility(View.VISIBLE);
//                }
//
//                fav.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        fav.setVisibility(View.GONE);
//                        favdone.setVisibility(View.VISIBLE);
//                        String negokey = s.get(position);
//                        Log.v("fsgfht",s.get(position));
//                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                        DatabaseReference favourite;
//                        favourite=databaseReference.child("Shopper").child(firebaseUser.getUid());
//                        favourite.child("Favourite").push().setValue(negokey);
//
////
//                    }
//                });
//
//                favdone.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        favdone.setVisibility(View.GONE);
//                        fav.setVisibility(View.VISIBLE);
//                        String negokey = s.get(position);
//                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                        DatabaseReference favourite;
//                        favourite = databaseReference.child("Shopper").child(firebaseUser.getUid());
//                        Query qremove = favourite.child("Favourite").orderByValue().equalTo(negokey);
//                        qremove.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
//                                    itemSnapshot.getRef().removeValue();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//
//
//                        });
//                    }
//
//
//                });
//
//
//
//                // Add action buttons
//
//                //Setting message manually and performing action on button click
//                builder2.setCancelable (false);
//
//                //Creating dialog box
//                AlertDialog alert = builder2.create ();
//
//                alert.show ();

                ///////////////
//                fragmentJump(n);
//                cardFrag=new CardFrag();
//                mBundle=new Bundle();
//                mBundle.putParcelable("item_selected_key",nego);

                ////////////
//fragment me jayega data

//
//                Bundle args = new Bundle();
//                args.putString("first_name",n.getFirstname());
//                args.putString("last_name",n.getLastname());
//                args.putString("phno",n.getPhone());
//                args.putString("city",n.getCity());
//                args.putString("category1",n.getCategory1());
//                args.putString("category2",n.getCategory2());
//                args.putString("category3",n.getCategory3());
//                args.putString("pincode",n.getPincode());
//                cardFrag.setArguments(args);



                //
//                fragmentTransaction.addToBackStack("searchbar");
//                fragmentTransaction.replace(R.id.content_frame, ldf).commit();
            }
        });

////////////
        n=negotiatorProfileList.get(position);

//
////////////
//        ValueEventListener(new ValueEventListener() {
//                //////////////
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            if (dataSnapshot.getValue() == null) {
//
//                                                                }
//                            else {
//
//
//                                  }
//
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });


    }//bind end

    ////////
//
//    ////////////////////////////
//    public void switchContent(int id, CardFrag fragment) {
//   //     if (mContext == null)
////            return;
//      //  if (mContext instanceof SearchDisplayFrag) {
//SearchDisplayFrag m= (SearchDisplayFrag)mContext.getSupportFragmentManager().findFragmentById(SearchDisplayFrag);
//            SearchDisplayFrag m = new SearchDisplayFrag();
//            CardFrag frag = fragment;
//            m.switchContent(id, fragment);
////        }
    //   }


//
//
//    private void fragmentJump(NegotiatorDetails mItemSelected) {
//        cardFrag = new CardFrag();
//        mBundle = new Bundle();
//        mBundle.putString("trem",mItemSelected.getFirstname());
////        mBundle.putParcelable("item_selected_key", (Parcelable) mItemSelected);
//        cardFrag.setArguments(mBundle);
//        switchContent(R.layout.card_frag, cardFrag);
//    }

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


//    String location = s.get(position)+"."+"jpg";
//        Log.v("animesh",location);
//        photo_storage.child (location).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//        @Override
//        public void onSuccess(Uri uri) {
//            String imageURL = uri.toString ();
//            Glide.with(mContext).load(imageURL).into(holder.proimage);
//        }
//    }).addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception exception) {
//            // Handle any errors
//            Toast.makeText (mContext,exception.getMessage (),Toast.LENGTH_LONG).show ();
//        }
//    });



}
///////////

//    @Override
//    public void onBindViewHolder(@NonNull NegotiatorProfileViewHolder holder,int position ){
//        NegotiatorProfile negotiatorProfile=negotiatorProfileList.get(position);
//
//        holder.setDetails(negotiatorProfile.getFirstname(),negotiatorProfile.getLastname(),negotiatorProfile.getPhno());
//
//    }
//
//