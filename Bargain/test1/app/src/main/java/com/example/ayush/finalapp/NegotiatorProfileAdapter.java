package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class NegotiatorProfileAdapter extends RecyclerView.Adapter<NegotiatorProfileAdapter.NegotiatorProfileViewHolder>{

    private List <NegotiatorDetails> negotiatorProfileList;

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
//   // FragmentActivity mContext;
    Bundle mBundle;

    public static NegotiatorDetails n;

    public NegotiatorProfileAdapter(List<NegotiatorDetails> negotiatorProfileList,FragmentActivity f) {
        this.mContext= f;
        this.negotiatorProfileList = negotiatorProfileList;
    }



    public class NegotiatorProfileViewHolder extends RecyclerView.ViewHolder {

        public TextView first;
        ImageButton imgbutton;
        public TextView last;
        public TextView phone;




        public NegotiatorProfileViewHolder(View view) {
            super(view);

            first =(TextView) view.findViewById(R.id.first_name);
            last =(TextView) view.findViewById(R.id.last_name);
            phone=(TextView) view.findViewById(R.id.ph_no);
            imgbutton= (ImageButton) view.findViewById(R.id.card_click);

        }
    }

    public NegotiatorProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_layout, parent, false);
        return new NegotiatorProfileViewHolder(itemView);
    }


    //////////////////////////
    @Override
    public void onBindViewHolder(NegotiatorProfileViewHolder holder, final int position) {
         n = negotiatorProfileList.get(position);
         final NegotiatorDetails nego =negotiatorProfileList.get(position);

        holder.first.setText(n.getFirstname());
        holder.last.setText(n.getLastname());
        holder.phone.setText(n.getPhone());


        final NegotiatorDetails negotiatorProfile=negotiatorProfileList.get(position);
        holder.imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                n = negotiatorProfileList.get(position);

                //////////////////////
//use alert dialog
////////////////
                builder2 = new AlertDialog.Builder (mContext);
                LayoutInflater inflater = mContext.getLayoutInflater ();
                View v1= inflater.inflate(R.layout.card_frag,null);
                builder2.setView (v1);
                fname=(TextView) v1.findViewById(R.id.neg_profile_firstname);
                lname=(TextView) v1.findViewById(R.id.neg_profile_lastname);
                phno=v1.findViewById(R.id.neg_profile_phno);
                pincode=v1.findViewById(R.id.neg_profile_pincode);
                cat1=v1.findViewById(R.id.neg_profile_cat1);
                cat2=v1.findViewById(R.id.neg_profile_cat2);
                cat3=v1.findViewById(R.id.neg_profile_cat3);
                city=(TextView) v1.findViewById(R.id.neg_profile_city);
                city.setText(n.getCity());
                fname.setText(n.getFirstname());
                lname.setText(n.getLastname());
                pincode.setText(n.getPincode());
                cat2.setText(n.getCategory2());
                cat1.setText(n.getCategory1());
                cat3.setText(n.getCategory3());
                phno.setText(n.getPhone());
                        builder2.setNegativeButton ("Close", new DialogInterface.OnClickListener () {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel ();
                            }
                        });

                        // Add action buttons

                //Setting message manually and performing action on button click
                builder2.setCancelable (false);

                //Creating dialog box
                AlertDialog alert = builder2.create ();

                alert.show ();

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

    public void addItem(NegotiatorDetails eventsList)
    {
        this.negotiatorProfileList.add(eventsList);
    }

    @Override
    public int getItemCount() {

        Log.i("Item-Count",Integer.toString(negotiatorProfileList.size()));
        return negotiatorProfileList.size();
    }



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