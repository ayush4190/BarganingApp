package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatBox extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText messagebox;
    ImageButton sendButton;

    final static int Left = 1;
    final static int Right = 2;

    String User;
    String[] Reciever;
    static String ChatRoom = "manas";
    ChatBoxAdapter adapter;
    static List<Message> chats = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        InitializeFields();

        Intent intent = getIntent();
        User = intent.getStringExtra("User");
        Reciever = intent.getStringArrayExtra("Reciever");
        int Num = intent.getIntExtra("Number",0);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(Reciever[0]);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        //adapter = new ChatBoxAdapter(chats,getApplicationContext(),User,Reciever[1]);
        //recyclerView.setAdapter(adapter);

        if(User.compareTo(Reciever[1])>0)
            ChatRoom = User + Reciever[1];
        else
            ChatRoom = Reciever[1] + User;

        ReadMessages();
    }

    private void ReadMessages() {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chats.clear();
                if(dataSnapshot.child(ChatRoom).exists())
                {
                    //Toast.makeText(ChatActivity.this, "here", Toast.LENGTH_SHORT).show();
                    for(DataSnapshot snapshot : dataSnapshot.child(ChatRoom).getChildren()) {

                        Message message = snapshot.getValue(Message.class);
                        while (message.message == null) ;

                        // Toast.makeText(ChatActivity.this, "AfterWhile", Toast.LENGTH_SHORT).show();
                        chats.add(message);
                        //   Toast.makeText(ChatActivity.this, chats.get(0).message, Toast.LENGTH_SHORT).show();
                    }
                    adapter = new ChatBoxAdapter(chats,getApplicationContext(),User,Reciever[1],ChatBox.this);
                    recyclerView.setAdapter(adapter);

                }
                //else
                //   Toast.makeText(ChatActivity.this, "here1", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("databaseerror",databaseError.getMessage());

            }
        });

    }

    private void InitializeFields() {
        recyclerView = findViewById(R.id.ChatAct_Recycler);
        toolbar = findViewById(R.id.ChatAct_Toolbar);
        messagebox = findViewById(R.id.ChatAct_message);
        sendButton = findViewById(R.id.ChatAct_Send);
        ChatRoom =null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ChatFragment.Opened=0;
    }

    public void SendMessage(View view) {

        String message = messagebox.getText().toString();
        if(!TextUtils.isEmpty(message))
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats/"+ChatRoom);
            String key = reference.push().getKey();
            final Message messages = new Message(message,User,Reciever[1],key);
            reference.child(key).setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    messagebox.setText(null);
                }
            });

        }


    }
    void DeleteMessage(int i)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats/"+ChatRoom);
        reference.child(chats.get(i).id).removeValue();
        chats.remove(i);
        adapter = new ChatBoxAdapter(chats,getApplicationContext(),User,Reciever[1],ChatBox.this);
        recyclerView.setAdapter(adapter);

    }
    //


}
