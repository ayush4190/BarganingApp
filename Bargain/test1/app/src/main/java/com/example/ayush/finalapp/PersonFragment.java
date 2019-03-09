package com.example.ayush.finalapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment {

    List<String[]> list;
    public PersonFragment() {
        // Required empty public constructor
    }

    static ListView personlist = null;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_person, container, false);
        personlist = view.findViewById(R.id.Person_List);

        return view;
    }

    class SearchAdapter extends BaseAdapter {
       
        Context context;
         public SearchAdapter(List<String[]> list1) { list = list1;
         this.context = view.getContext(); }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
             convertView = LayoutInflater.from(context).inflate(R.layout.search_design,parent,false);
             TextView name = convertView.findViewById(R.id.SearchDesign_text);
             TextView add = convertView.findViewById(R.id.SearchDesign_add);
             ImageView image = convertView.findViewById(R.id.SearchDesign_image);

             name.setText(list.get(position)[0]);
             add.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) { SendFriendRequest(list.get(position)[1],position); }
             });
            return null;
            }}


       void SendFriendRequest(String Id,int position)
       {
           String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
           DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
           Map<String,String> map = new HashMap<>();
           
           reference.child(Id+"/friend_requests").child(Uid);
           reference.child(Uid+"/sent_requests").child(Id);
           list.remove(position);
           
       }
}
