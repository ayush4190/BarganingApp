package com.example.ayush.finalapp;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Debug;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.L;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static com.example.ayush.finalapp.ChatBox.chats;
import static com.example.ayush.finalapp.NegotiatorProfileAdapter.n;

public class ChatBoxNego extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private static final String channelId ="com.example.ayush.finalapp";
    private String message_sent;
    DatabaseReference databaseReference1;
    static Context mContext;
    NotificationCompat.Builder builder;
    private FirebaseUser firebaseUser;
    DatabaseReference m1;
    private DatabaseReference databaseReference;
    private String nego_id;
    private String shop_id;
    RecyclerView recyclerView;
    private EditText messagebox;
    private String nameshop;
    private ImageButton sendButton;
    private int i,alpha;
    TransactionsDetails transactionsDetails;
    MeetDetails meetDetails;
    private TextView placeText,dateText,timeText,noMeet,already_accepted;
    private TextView placeText1,dateText1,timeText1;
    AlertDialog.Builder builder2;
    final static int Left = 1;
    final static int Right = 2;
    private String User;
    String[] Reciever;
    static String empty="does not exist";
    static String ChatRoom = "manas";
    ChatBoxNegoAdapter adapter;
    static List<Message> chats = new ArrayList<>();


    //Oncreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box_nego);


        InitializeFields();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth=FirebaseAuth.getInstance();
        mContext=getApplicationContext ();
        firebaseUser = firebaseAuth.getCurrentUser ();
        databaseReference=FirebaseDatabase.getInstance ().getReference ();
        Intent intent = getIntent();
        User = intent.getStringExtra("User");

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.None)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        Reciever = intent.getStringArrayExtra("Reciever");

        setTitle(Reciever[0]);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        if(User.compareTo(Reciever[1])>0)
            ChatRoom = User + Reciever[1];
        else
            ChatRoom = Reciever[1] + User;

        ReadMessages();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.chat_meet_accept, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            OneSignal.startInit(this)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .init();
        }
        if (id == R.id.action_accept) {
            builder2 = new AlertDialog.Builder (ChatBoxNego.this);
            LayoutInflater inflater = ChatBoxNego.this.getLayoutInflater ();
            final View v1= inflater.inflate(R.layout.meet_nego_frag,null);
            builder2.setView (v1);
            dateText=v1.findViewById(R.id.meet_date_edit);
            placeText=v1.findViewById(R.id.meet_place_edit);
            timeText=v1.findViewById(R.id.meet_time_edit);
            dateText1=v1.findViewById(R.id.meet_date);
            placeText1=v1.findViewById(R.id.meet_place);
            noMeet=v1.findViewById(R.id.no_meet);
            already_accepted=v1.findViewById(R.id.already_accepted);
            timeText1=v1.findViewById(R.id.meet_time);
            dateText.setText("empty");
            placeText.setText("empty");
            timeText.setText("empty");
            i=0;
            alpha=0;
            m1=FirebaseDatabase.getInstance().getReference().child("Negotiator").child(firebaseUser.getUid()).child("meet");
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()) {
                        dateText.setVisibility(v1.GONE);
                        timeText.setVisibility(v1.GONE);
                        placeText.setVisibility(v1.GONE);
                        dateText1.setVisibility(v1.GONE);
                        timeText1.setVisibility(v1.GONE);
                        placeText1.setVisibility(v1.GONE);
                        noMeet.setVisibility(v1.VISIBLE);
                        builder2.setTitle("Accept or decline the meet");
                        AlertDialog alert = builder2.create ();
                        alert.show ();

                    }else {
                        meetDetails=dataSnapshot.getValue(MeetDetails.class);
                        Negotiator_dash.nego_name=meetDetails.negoname;

                        //if already accepted show dialog already accepted
                        if(meetDetails.isAccepted){
                            dateText.setVisibility(v1.GONE);
                            timeText.setVisibility(v1.GONE);
                            placeText.setVisibility(v1.GONE);
                            dateText1.setVisibility(v1.GONE);
                            timeText1.setVisibility(v1.GONE);
                            placeText1.setVisibility(v1.GONE);
                            already_accepted.setVisibility(v1.VISIBLE);
                            builder2.setTitle("Accept or decline the meet");
                            AlertDialog alert = builder2.create ();
                            alert.show ();
                        }
                        //else display all details and give two options accept or reject in dialog and show it
                        else{
                            placeText.setText(meetDetails.getPlace());
                            timeText.setText(meetDetails.getTime());
                            dateText.setText(meetDetails.getDate());

                            //now two options to accept or reject
                            //reject
                            builder2.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    message_sent="Sorry, your service request has been denied by "+Negotiator_dash.nego_name+". For further grievances and refund related queries, please contact us at refund@bargainers.com";
                                    messagebox.setText(message_sent);
                                    sendButton.performClick();
                                    //refund issues
                                    databaseReference1 = FirebaseDatabase.getInstance().getReference();
                                    nego_id=firebaseAuth.getCurrentUser().getUid();
                                    Query d=databaseReference1.child("Negotiator").child(nego_id);
                                    d.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                        nameshop=Reciever[0];
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                    //now after all this remove meet from both nego and shopper meet field as it is deleted
                                    m1.child(Reciever[1]).removeValue();
                                    FirebaseDatabase.getInstance().getReference().child("Shopper").child(Reciever[1]).child("meet").child(firebaseUser.getUid()).removeValue();
                                }
                            });
                            //end of reject button

                            //accept
                            builder2.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    meetDetails.isAccepted=true;
                                    //set transaction id also
                                    FirebaseDatabase.getInstance().getReference().child("Shopper").child(Reciever[1]).child("meet").child(firebaseUser.getUid()).setValue(meetDetails);
                                    message_sent=Negotiator_dash.nego_name+" has accepted your request, Happy Bargaining:)";
                                    messagebox.setText(message_sent);
                                    sendButton.performClick();
                                    nego_id=firebaseAuth.getCurrentUser().getUid();
                                    shop_id=meetDetails.getShopper();
                                    FirebaseDatabase.getInstance().getReference().child("Negotiator").child(nego_id).child("acceptno").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()){
                                                String s=dataSnapshot.getValue(String.class);
                                                s=String.valueOf(Integer.parseInt(s)+1);
                                                FirebaseDatabase.getInstance().getReference().child("Negotiator").child(nego_id).child("acceptno").setValue(s);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    transactionsDetails=new TransactionsDetails(shop_id,nego_id,meetDetails.getNegoname(),meetDetails.getDate(),"pending","0.0",Reciever[0]);
                                    DatabaseReference m2=FirebaseDatabase.getInstance().getReference().child("Transactions").child(shop_id).push();
                                    m2.setValue(transactionsDetails);
                                    meetDetails.transaction_id_shopper=m2.getKey();
                                    m2=FirebaseDatabase.getInstance().getReference().child("Transactions").child(nego_id).push();
                                    m2.setValue (transactionsDetails);
                                    meetDetails.transaction_id_nego=m2.getKey();
                                    m1.child(Reciever[1]).setValue(meetDetails);
                                    FirebaseDatabase.getInstance().getReference().child("Shopper").child(Reciever[1]).child("meet").child(firebaseUser.getUid()).setValue(meetDetails);
                                    builder = new NotificationCompat.Builder(ChatBoxNego.this, channelId)
                                            .setContentTitle("Request Accepted")
                                            .setSmallIcon(R.drawable.appicon1)
                                            .setContentText("Open wallet to initialize payment")
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT).setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                                    R.drawable.appicon1));
                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ChatBoxNego.this);
                                    notificationManager.notify(12, builder.build());
                                }
                            });
                            //end of accept button

                            //show dialog
                            builder2.setTitle("Accept or decline the meet");
                            AlertDialog alert = builder2.create ();
                            alert.show ();
                        }//finish of if condition of isaccepted!
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            m1.child(Reciever[1]).addListenerForSingleValueEvent(eventListener);
        }
        return true;
    }

    //Read messages function
    private void ReadMessages() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                if(dataSnapshot.child(ChatRoom).exists())
                {
                    for(DataSnapshot snapshot : dataSnapshot.child(ChatRoom).getChildren()) {
                        Message message = snapshot.getValue(Message.class);
                        while (message.message == null) ;
                         chats.add(message);
                     }
                    adapter = new ChatBoxNegoAdapter(chats,getApplicationContext(),User,Reciever[1],ChatBoxNego.this);
                    Log.v("datap",adapter.receiver);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("databaseerror",databaseError.getMessage());
            }
        });
    }


    //initialization function
    private void InitializeFields() {
        recyclerView = findViewById(R.id.ChatAct_Recycler_nego);
        messagebox = findViewById(R.id.ChatAct_message);
        sendButton = findViewById(R.id.ChatAct_Send);
        ChatRoom =null;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    //Send messages function
    public void SendMessage(View view) {
        String message = messagebox.getText().toString();
        message= Pattern.compile("^\n*",Pattern.DOTALL).matcher(message).replaceFirst("");
        if(!TextUtils.isEmpty(message))
        {
            message_sent=message;
            sendNotification();
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


    //Delete messages
    void DeleteMessage(int i)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats/"+ChatRoom);
        reference.child(chats.get(i).id).removeValue();
        chats.remove(i);
        adapter = new ChatBoxNegoAdapter(chats,getApplicationContext(),User,Reciever[1],ChatBoxNego.this);
        recyclerView.setAdapter(adapter);
    }


    //Send notification by one signal notification
    public void sendNotification()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;
                    send_email=Reciever[1];
                    try {
                        String jsonResponse;
                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic OTk2YzU1NDktMzE5Yi00MGEwLTk0NDMtYzZkMzI1YzNjM2Jj");
                        con.setRequestMethod("POST");
                        String strJsonBody = "{"
                                + "\"app_id\": \"7b482b83-38de-4653-ba78-c04403c3b4c9\","
                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"USER_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"
                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \""+message_sent+"\"}"
                                + "}";

                        System.out.println("strJsonBody:\n" + strJsonBody);
                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);
                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);
                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }
}