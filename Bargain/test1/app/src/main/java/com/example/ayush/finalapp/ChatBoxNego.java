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
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayDate;
    private FirebaseAuth firebaseAuth;
    private static final String channelId ="com.example.ayush.finalapp";
String message_sent;
     DatabaseReference databaseReference1;
    int amount_int;
String name;
    static Context mContext;
    NotificationCompat.Builder builder;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference mdatabaseReference;
    EditText place;
    String nego_id;
    String shop_id;
    EditText time;
    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText messagebox;
    Button displayMeet;
    String nameshop;
    ImageButton sendButton;
    int i,alpha;
    TransactionsDetails transactionsDetails;
    MeetDetails meetDetails;
    TextView placeText,dateText,timeText,noMeet;
    TextView placeText1,dateText1,timeText1;
    AlertDialog.Builder builder2;

    final static int Left = 1;
    final static int Right = 2;

    String User;
    String[] Reciever;
    String meet_date;
    static String empty="does not exist";
    static String ChatRoom = "manas";
    ChatBoxNegoAdapter adapter;
    static List<Message> chats = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box_nego);
        InitializeFields();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        displayMeet=findViewById(R.id.Button_display_meet);
        firebaseAuth=FirebaseAuth.getInstance();
        mContext=getApplicationContext ();
        firebaseUser = firebaseAuth.getCurrentUser ();
        databaseReference=FirebaseDatabase.getInstance ().getReference ();
        Intent intent = getIntent();
        User = intent.getStringExtra("User");
        Reciever = intent.getStringArrayExtra("Reciever");
        int Num = intent.getIntExtra("Number",0);


//        OneSignal.startInit(this)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.None)
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(Reciever[0]);
//        createNotificationChannel();
        setTitle(Reciever[0]);
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
//        displayMeet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                builder2 = new AlertDialog.Builder (ChatBoxNego.this);
//                LayoutInflater inflater = ChatBoxNego.this.getLayoutInflater ();
//                final View v1= inflater.inflate(R.layout.meet_nego_frag,null);
//                builder2.setView (v1);
//
//                dateText=v1.findViewById(R.id.meet_date_edit);
//                placeText=v1.findViewById(R.id.meet_place_edit);
//                timeText=v1.findViewById(R.id.meet_time_edit);
//                dateText1=v1.findViewById(R.id.meet_date);
//                placeText1=v1.findViewById(R.id.meet_place);
//                noMeet=v1.findViewById(R.id.no_meet);
//                timeText1=v1.findViewById(R.id.meet_time);
//                dateText.setText(empty);
//                placeText.setText(empty);
//                timeText.setText(empty);
//                i=0;
//                alpha=0;
//
////                                            Log.v("manas",mdatabaseReference.get);
//
//                Query query2=databaseReference.child("Negotiator").child(firebaseUser.getUid()).child("meet").orderByChild("shopper").equalTo(Reciever[1]).limitToLast(1);
//
//                query2.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
//                        if(dataSnapshot.exists())
//                        {
//                            if(dataSnapshot.child("place").getValue().toString()!=null)
//                            {
//
//                                i++;
//
//                            }
//                            if(dataSnapshot.child("time").getValue().toString()!=null)
//                            {
//                                i++;
//
//                            }
//                            if(dataSnapshot.child("date").getValue().toString()!=null)
//                            {
//                                i++;
//
//                            }
//                            if(i==3) {
//
//                                meetDetails=dataSnapshot.getValue(MeetDetails.class);
//                                if(meetDetails.isAccepted){
//                                    Log.v("me inside i==3",String.valueOf(i));
//                                    dateText.setVisibility(v1.GONE);
//                                    timeText.setVisibility(v1.GONE);
//                                    placeText.setVisibility(v1.GONE);
//                                    dateText1.setVisibility(v1.GONE);
//                                    timeText1.setVisibility(v1.GONE);
//                                    placeText1.setVisibility(v1.GONE);
//                                    noMeet.setVisibility(v1.VISIBLE);
//                                    noMeet.setText("The latest pending meet is already accepted");
//                                    builder2.setTitle("Accept or decline the meet");
//
//                                    AlertDialog alert = builder2.create ();
//                                    alert.show ();
//
//                                }else {
//                                    Log.v("me inside i==3 and else",String.valueOf(i));
//                                    placeText.setText(dataSnapshot.child("place").getValue().toString());
//                                    timeText.setText(dataSnapshot.child("time").getValue().toString());
//                                    dateText.setText(dataSnapshot.child("date").getValue().toString());
//                                    alpha=1;
//                                    builder2.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            meetDetails.isAccepted=false;
//                                            dataSnapshot.getRef().setValue(meetDetails);
//                                        }
//                                    });
//                                    builder2.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            meetDetails.isAccepted=true;
//                                            dataSnapshot.getRef().setValue(meetDetails);
//
//                                            nego_id=firebaseAuth.getCurrentUser().getUid();
//                                            shop_id=meetDetails.getShopper();
//
////                                            mdatabaseReference.addValueEventListener(new ValueEventListener() {
////                                                @Override
////                                                public void onDataChange(DataSnapshot dataSnapshot) {
////                                                    NegotiatorDetails negotiatorDetails = dataSnapshot.getValue(NegotiatorDetails.class);
////                                                    name=negotiatorDetails.getFirstname()+"  "+negotiatorDetails.getLastname();
////                                                }
////
////                                                @Override
////                                                public void onCancelled(DatabaseError databaseError) {
////                                                    System.out.println("The read failed: " + databaseError.getCode());
////                                                }
////                                            });
////                                            Log.v("manas2",name);
//                                            transactionsDetails=new TransactionsDetails(shop_id,nego_id,meetDetails.getNegoname(),meetDetails.getDate(),"pending","0.0",Reciever[0]);
//                                            FirebaseDatabase.getInstance().getReference().child("Transactions").child(shop_id).push().setValue(transactionsDetails);
//                                            FirebaseDatabase.getInstance().getReference().child("Transactions").child(nego_id).push().setValue(transactionsDetails);
//                                            //here transaction is initialized
//
//                                        }
//                                    });
//
//                                    builder2.setTitle("Accept or decline the meet");
//
//                                    AlertDialog alert = builder2.create ();
//                                    alert.show ();
//                                }
//
//
//                                }else{
//                                Log.v("me inside i!=3",String.valueOf(i));
//                                dateText.setVisibility(v1.GONE);
//                                timeText.setVisibility(v1.GONE);
//                                placeText.setVisibility(v1.GONE);
//                                dateText1.setVisibility(v1.GONE);
//                                timeText1.setVisibility(v1.GONE);
//                                placeText1.setVisibility(v1.GONE);
//                                noMeet.setVisibility(v1.VISIBLE);
//                                builder2.setTitle("Accept or decline the meet");
//
//                                AlertDialog alert = builder2.create ();
//                                alert.show ();
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//        }
//        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId ();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_meet)
//        {
//            builder2 = new AlertDialog.Builder (ChatBoxNego.this);
//            LayoutInflater inflater = ChatBoxNego.this.getLayoutInflater ();
//            final View v1= inflater.inflate(R.layout.meet_frag,null);
//            builder2.setView (v1);
//
//            builder2.setNegativeButton ("Close", new DialogInterface.OnClickListener () {
//                public void onClick(DialogInterface dialog, int id) {
//                    dialog.cancel ();
//                }
//            });
//            mDisplayDate = (TextView) v1.findViewById(R.id.meet_date_edit);
//
//            mDisplayDate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Calendar cal = Calendar.getInstance();
//                    int year = cal.get(Calendar.YEAR);
//                    int month = cal.get(Calendar.MONTH);
//                    int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                    DatePickerDialog dialog = new DatePickerDialog(
//                            ChatBoxNego.this,
//                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                            mDateSetListener,
//                            year,month,day);
//
//                    dialog.getDatePicker().setMaxDate(new Date().getTime());
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    dialog.show();
//                }
//
//            });
//
//            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                    month = month + 1;
//
//                    String date = day + "/" + month + "/" + year;
//                    mDisplayDate.setText(date);
//                    meet_date=mDisplayDate.getText ().toString ().trim ();
//                }
//            };
//            builder2.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    place=(EditText)v1.findViewById(R.id.meet_place_edit);
//                    time=(EditText)v1.findViewById(R.id.meet_time_edit);
//                    meetDetails=new MeetDetails(firebaseUser.getUid(),place.getText().toString(),meet_date,time.getText().toString(),Reciever[1],false);
//                    databaseReference.child("Shopper").child(firebaseUser.getUid()).child("meet").push().setValue(meetDetails);
//                    databaseReference.child("Negotiator").child(Reciever[1]).child("meet").push().setValue(meetDetails);
//                }
//            });
//            builder2.setTitle("Enter Details for meet");
//            builder2.setCancelable (false);
//            AlertDialog alert = builder2.create ();
//            alert.show ();
//        }
//        return true;
//    }

    /////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.chat_meet_accept, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_accept) {
            /////////
            //check here
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
            timeText1=v1.findViewById(R.id.meet_time);
            dateText.setText(empty);
            placeText.setText(empty);
            timeText.setText(empty);
            i=0;
            alpha=0;

//                                            Log.v("manas",mdatabaseReference.get);

            Query query2=databaseReference.child("Negotiator").child(firebaseUser.getUid()).child("meet").orderByChild("shopper").equalTo(Reciever[1]).limitToLast(1);

            query2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                    if(dataSnapshot.exists())
                    {
                        if(dataSnapshot.child("place").getValue().toString()!=null)
                        {

                            i++;

                        }
                        if(dataSnapshot.child("time").getValue().toString()!=null)
                        {
                            i++;

                        }
                        if(dataSnapshot.child("date").getValue().toString()!=null)
                        {
                            i++;

                        }
                        if(i==3) {

                            meetDetails=dataSnapshot.getValue(MeetDetails.class);
                            if(meetDetails.isAccepted){
                                Log.v("me inside i==3",String.valueOf(i));
                                dateText.setVisibility(v1.GONE);
                                timeText.setVisibility(v1.GONE);
                                placeText.setVisibility(v1.GONE);
                                dateText1.setVisibility(v1.GONE);
                                timeText1.setVisibility(v1.GONE);
                                placeText1.setVisibility(v1.GONE);
                                noMeet.setVisibility(v1.VISIBLE);
                                noMeet.setText("The latest pending meet is already accepted");
                                builder2.setTitle("Accept or decline the meet");

                                AlertDialog alert = builder2.create ();
                                alert.show ();

                            }else {
                                Log.v("me inside i==3 and else",String.valueOf(i));
                                placeText.setText(dataSnapshot.child("place").getValue().toString());
                                timeText.setText(dataSnapshot.child("time").getValue().toString());
                                dateText.setText(dataSnapshot.child("date").getValue().toString());
                                alpha=1;
                                builder2.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        meetDetails.isAccepted=false;
                                        message_sent="Sorry, "+Negotiator_dash.nego_name+" has rejected your request";
                                        sendNotification();

                                        dataSnapshot.getRef().setValue(meetDetails);

                                        databaseReference1 = FirebaseDatabase.getInstance().getReference();
                                        nego_id=firebaseAuth.getCurrentUser().getUid();

                                        Query d=databaseReference1.child("Negotiator").child(nego_id).child("Amount");
                                        d.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String amount="";
                                                amount =dataSnapshot.getValue(String.class);
//                              Log.v("amount",amount);
                                                amount_int =Integer.parseInt(amount);
                                               int deductable_amount = new Random().nextInt((50 - 20) + 1) + 20;
                                                if (amount_int>=50){
                                                    amount_int-=deductable_amount;
                                                    amount= String.valueOf(amount_int);
                                                }
                                                databaseReference.child("Negotiator").child(nego_id).child("Amount").setValue(amount);
                                                nameshop=Reciever[0];
                                                builder = new NotificationCompat.Builder(ChatBoxNego.this, channelId)
                                                        .setContentTitle("Amount Deducted")
                                                        .setSmallIcon(R.drawable.appicon1)
                                                        .setContentText("Rs." + deductable_amount +" deducted for rejecting service of "+Reciever[0])
                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                                                R.drawable.appicon1));
                                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ChatBoxNego.this);

// notificationId is a unique int for each notification that you must define
                                                notificationManager.notify(11, builder.build());

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                });
                                builder2.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        meetDetails.isAccepted=true;
                                        message_sent=Negotiator_dash.nego_name+" has accepted your request, Happy Bargaining:)";
                                        sendNotification();

                                        dataSnapshot.getRef().setValue(meetDetails);

                                        nego_id=firebaseAuth.getCurrentUser().getUid();
                                        shop_id=meetDetails.getShopper();

//                                            mdatabaseReference.addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    NegotiatorDetails negotiatorDetails = dataSnapshot.getValue(NegotiatorDetails.class);
//                                                    name=negotiatorDetails.getFirstname()+"  "+negotiatorDetails.getLastname();
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//                                                    System.out.println("The read failed: " + databaseError.getCode());
//                                                }
//                                            });
//                                            Log.v("manas2",name);
                                        transactionsDetails=new TransactionsDetails(shop_id,nego_id,meetDetails.getNegoname(),meetDetails.getDate(),"pending","0.0",Reciever[0]);
                                        DatabaseReference m1=FirebaseDatabase.getInstance().getReference().child("Transactions").child(shop_id).push ();
                                        m1.setValue(transactionsDetails);
//                                        amount_payable.MostRecentIdShopper=m1.getKey ();
//                                        Log.v ("manas",amount_payable.MostRecentIdShopper);
                                        DatabaseReference m2=FirebaseDatabase.getInstance().getReference().child("Transactions").child(nego_id).push();
                                        m2.setValue (transactionsDetails);
                                        builder = new NotificationCompat.Builder(ChatBoxNego.this, channelId)
                                                .setContentTitle("Request Accepted")
                                                .setSmallIcon(R.drawable.appicon1)
                                                .setContentText("Open wallet to initialize payment")
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                                        R.drawable.appicon1));
                                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ChatBoxNego.this);

// notificationId is a unique int for each notification that you must define
                                        notificationManager.notify(12, builder.build());

//                                        amount_payable.MostRecentIdNegotiator=m2.getKey ();
                                        //here transaction is initialized
//                                        Log.v ("manas",amount_payable.MostRecentIdNegotiator);
//                                        amount_payable.MostRecentDate=transactionsDetails.getDate ();
//                                        amount_payable.MostRecentNameNegotiator=transactionsDetails.getCreditedToName ();
//                                        amount_payable.MostRecentNameShopper=transactionsDetails.getDebitedFromName ();

                                    }
                                });

                                builder2.setTitle("Accept or decline the meet");

                                AlertDialog alert = builder2.create ();
                                alert.show ();
                            }


                        }else{
                            Log.v("me inside i!=3",String.valueOf(i));
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

                        }
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            ////////

        }
        return true;
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
                        Log.v("datap",message.message);

                        // Toast.makeText(ChatActivity.this, "AfterWhile", Toast.LENGTH_SHORT).show();
                        chats.add(message);
                        //   Toast.makeText(ChatActivity.this, chats.get(0).message, Toast.LENGTH_SHORT).show();
                    }
                    adapter = new ChatBoxNegoAdapter(chats,getApplicationContext(),User,Reciever[1],ChatBoxNego.this);
                    Log.v("datap",adapter.receiver);
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
        recyclerView = findViewById(R.id.ChatAct_Recycler_nego);
//        toolbar = findViewById(R.id.ChatAct_Toolbar);
        messagebox = findViewById(R.id.ChatAct_message);
        sendButton = findViewById(R.id.ChatAct_Send);
        ChatRoom =null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ChatFragmentNego.Opened=0;
    }

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
                    Log.v("nego_name",Negotiator_dash.nego_name);
                }
            });

        }


    }
    void DeleteMessage(int i)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats/"+ChatRoom);
        reference.child(chats.get(i).id).removeValue();
        chats.remove(i);
        adapter = new ChatBoxNegoAdapter(chats,getApplicationContext(),User,Reciever[1],ChatBoxNego.this);
        recyclerView.setAdapter(adapter);

    }
    public void sendNotification()
    {

//        Toast.makeText(this, "Current Recipients is : user1@gmail.com ( Just For Demo )", Toast.LENGTH_SHORT).show();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    //This is a Simple Logic to Send Notification different Device Programmatically....
//                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals()) {
//                        send_email = "user2@gmail.com";
//                    } else {
//                        send_email = "user1@gmail.com";
//                    }
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
//                                + "\"headings\": {\"en\":\""+Negotiator_dash.nego_name+"\"}"
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
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel1";
            String description = "Channel discription";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    //
    public static Context getContextOfApplication()
    {
        return mContext;
    }


}