package com.example.ayush.finalapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class NegotiatorProfileAdapter extends RecyclerView.Adapter<NegotiatorProfileAdapter.NegotiatorProfileViewHolder>{

    private List <NegotiatorDetails> negotiatorProfileList;

    public static NegotiatorDetails n;

    public NegotiatorProfileAdapter(List<NegotiatorDetails> negotiatorProfileList) {
        this.negotiatorProfileList = negotiatorProfileList;
    }


    public class NegotiatorProfileViewHolder extends RecyclerView.ViewHolder {

        public TextView first;
        public TextView last;
        public TextView phone;



        public NegotiatorProfileViewHolder(View view) {
            super(view);

            first =(TextView) view.findViewById(R.id.first_name);
            last =(TextView) view.findViewById(R.id.last_name);
            phone=(TextView) view.findViewById(R.id.ph_no);


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

        NegotiatorDetails negotiatorProfile=negotiatorProfileList.get(position);

        holder.first.setText(n.getFirstname());
        holder.last.setText(n.getLastname());
       holder.phone.setText(n.getPhone());


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

    ////////////////////////////


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