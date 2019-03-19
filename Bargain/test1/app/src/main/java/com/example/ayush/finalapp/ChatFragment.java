package com.example.ayush.finalapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    //Account
    static String NameUser,DobUser;

  //  static int Method_Used = 1;

    static ProgressDialog dialog;

    public ChatFragment() {
        // Required empty public constructor
    }

    View view;
   //List
    ListView chatlist;
    ChatAdapter adapter1;

    String User;
    ImageView cartoon;
    TextView quote;
    DatabaseReference reference;
    DatabaseReference mreference;
    static ArrayList<String[]> list = new ArrayList<>();

//    static int Opened=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_chat, container, false);


        InitializeFields();
        quote = (TextView)view.findViewById(R.id.quotechat) ;
        cartoon=(ImageView)view.findViewById(R.id.chatcartoon);
        cartoon.setVisibility(View.GONE);
        quote.setVisibility(View.GONE);
        chatlist.setVisibility(View.GONE);
        //ListVIew Setup
        adapter1 = new ChatAdapter();
        chatlist.setAdapter(adapter1);
        //User is not null confirmation
        User = "Alpha";
            if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                User = FirebaseAuth.getInstance().getCurrentUser().getUid();
          //Retrieving data from database
            mreference = FirebaseDatabase.getInstance().getReference().child("Shopper").child(ShopperHomepage.shopper_uid).child("nego_chat");
        reference = FirebaseDatabase.getInstance().getReference().child("Negotiator");

                AtStartUp();
               UpdateAccount();
             //   UpdateContacts();


        return view;
    }

    void AtStartUp()
    {
        dialog.show();
        mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                //Deleting Previous Data on List
                list.clear();
                //Retrieving data
                if(dataSnapshot.exists()) {
                    chatlist.setVisibility(View.VISIBLE);
                    cartoon.setVisibility(View.GONE);
                    quote.setVisibility(View.GONE);
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if (data.getKey().equals(User)) {
                            continue;
                        }
                        //Adding names of friends to list
                        try {
                            String name;
                            String uid;
                            name = data.child("name").getValue(String.class);
                            uid = data.getKey();

                            while (name == null) ;
                            list.add(new String[]{name, uid});
                        } catch (Exception e) {
                            Log.d("ChatFragmentGet", e.getMessage());
                        }
                    }
                    //updating listview
                    adapter1.notifyDataSetChanged();
                    if (i == 0)
                        dialog.dismiss();
                }
                else{
                    dialog.dismiss();
                    chatlist.setVisibility(View.GONE);
                    cartoon.setVisibility(View.VISIBLE);
                    quote.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    void UpdateAccount()
    {

        dialog.show();
        reference.child(User).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child("firstname").getValue()!=null&&dataSnapshot.child("lastname").getValue()!=null)
                    NameUser = dataSnapshot.child("firstname").getValue().toString()+" "+dataSnapshot.child("lastname").getValue().toString();
//                DobUser = dataSnapshot.child("year").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Toast.makeText(getContext(), "I was here", Toast.LENGTH_SHORT).show();
        adapter1.notifyDataSetChanged();
    }


    private void InitializeFields() {
        chatlist = view.findViewById(R.id.Chat_List);
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Please Wait");
        dialog.setCanceledOnTouchOutside(false);

    }

    class ChatAdapter extends BaseAdapter {

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
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //Setting up view on the list
            Context context = getContext();
            convertView = ((FragmentActivity) context).getLayoutInflater().inflate(R.layout.chatdesign,parent,false);
            TextView text = convertView.findViewById(R.id.Design_text);
            ConstraintLayout constraintLayout=convertView.findViewById(R.id.chatdesign_constraint);
            text.setText(list.get(position)[0]);


            //show the most recent message
            //if message sent from here display tick

            //else display message only




            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(ChatFragment.Opened != 0)
//                        return;
//                    ChatFragment.Opened = 1;
                    //important
                    Intent intent = new Intent(getActivity(),ChatBox.class);
                    intent.putExtra("User",User);
                    intent.putExtra("Reciever",list.get(position));
                    intent.putExtra("Number",position);
                    startActivity(intent);
                }
            });


            return convertView;
        }

    }



}



