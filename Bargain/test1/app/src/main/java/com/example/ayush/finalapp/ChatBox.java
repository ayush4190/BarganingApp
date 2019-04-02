package com.example.ayush.finalapp;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ChatBox extends AppCompatActivity {

    //Variable declaration
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayDate;
    private FirebaseAuth firebaseAuth;
    private static final String channelId ="com.example.ayush.finalapp";
    private FirebaseUser firebaseUser;
    NotificationCompat.Builder builder;
    private DatabaseReference databaseReference;
    private EditText place;
    private EditText time;
    RecyclerView recyclerView;
    private EditText messagebox;
    private ImageButton sendButton;
    private MeetDetails meetDetails;
    AlertDialog.Builder builder2;
    final static int Left = 1;
    final static int Right = 2;
    private String User;
    private String[] Reciever;
    private String message_sent;
    private String meet_date;
    static String ChatRoom = "manas";
    private ChatBoxAdapter adapter;
    static List<Message> chats = new ArrayList<>();


    //Oncreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        InitializeFields();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser ();
        databaseReference=FirebaseDatabase.getInstance ().getReference ();
        Intent intent = getIntent();
        User = intent.getStringExtra("User");
        Reciever = intent.getStringArrayExtra("Reciever");

        //notifications are switched off
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.None)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.chat_meet_propose, menu);
        return true;
    }

    //app bar menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ();
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            OneSignal.startInit(this)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .init();
        }
        if (id == R.id.action_meet)
        {
            builder2 = new AlertDialog.Builder (ChatBox.this);
            LayoutInflater inflater = ChatBox.this.getLayoutInflater ();
            final View v1= inflater.inflate(R.layout.meet_frag,null);
            builder2.setView (v1);
            builder2.setNegativeButton ("Close", new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel (); }
                });
            mDisplayDate = (TextView) v1.findViewById(R.id.meet_date_edit);
            mDisplayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(
                            ChatBox.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mDateSetListener,
                            year,month,day);
                    dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                    dialog.getDatePicker().setMaxDate(System.currentTimeMillis()+31536000000L);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }

            });
            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    String date = day + "/" + month + "/" + year;
                    mDisplayDate.setText(date);
                    meet_date=mDisplayDate.getText ().toString ().trim ();
                }
            };
            builder2.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("Negotiator").child(Reciever[1]).child("requestno").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    String s=dataSnapshot.getValue(String.class);
                                    s=String.valueOf(Integer.parseInt(s)+1);
                                    FirebaseDatabase.getInstance().getReference().child("Negotiator").child(Reciever[1]).child("requestno").setValue(s);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                        place=(EditText)v1.findViewById(R.id.meet_place_edit);
                        time=(EditText)v1.findViewById(R.id.meet_time_edit);
                        meetDetails=new MeetDetails("none","none",firebaseUser.getUid(),place.getText().toString(),meet_date,time.getText().toString(),Reciever[1],false,Reciever[0]);
                        databaseReference.child("Shopper").child(firebaseUser.getUid()).child("meet").child(Reciever[1]).setValue(meetDetails);
                        databaseReference.child("Negotiator").child(Reciever[1]).child("meet").child(ShopperHomepage.shopper_uid).setValue(meetDetails);
                        messagebox.setText("Meet Proposal from "+ShopperHomepage.shopper_name+"\nPlace: "+place.getText().toString()+"\nDate: "+meet_date+"\nTime: "+time.getText().toString());
                        sendButton.performClick();
                        builder = new NotificationCompat.Builder(ChatBox.this, channelId)
                                .setContentTitle("Request sent successfully")
                                .setSmallIcon(R.drawable.notify)
                                .setContentText("Please wait for "+Reciever[0]+" to accept")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                        R.drawable.appicon1));
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ChatBox.this);
                        notificationManager.notify(12, builder.build());

                    }
                });
                builder2.setTitle("Enter Details for meet*");
            AlertDialog alert = builder2.create ();
            alert.show ();
        }
        return true;
    }


    //Function to read messages
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
                    adapter = new ChatBoxAdapter(chats,getApplicationContext(),User,Reciever[1],ChatBox.this);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("databaseerror",databaseError.getMessage());
            }
        });
    }


    //Initialize attributes
    private void InitializeFields() {
        recyclerView = findViewById(R.id.ChatAct_Recycler);
        messagebox = findViewById(R.id.ChatAct_message);
        sendButton = findViewById(R.id.ChatAct_Send);
        ChatRoom =null;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    //Send message to negotiator function
    public void SendMessage(View view) {

        String message = messagebox.getText().toString();
        message=Pattern.compile("^\n*",Pattern.DOTALL).matcher(message).replaceFirst("");
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


    //Function to delete messages
    void DeleteMessage(int i)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats/"+ChatRoom);
        reference.child(chats.get(i).id).removeValue();
        chats.remove(i);
        adapter = new ChatBoxAdapter(chats,getApplicationContext(),User,Reciever[1],ChatBox.this);
        recyclerView.setAdapter(adapter);
    }


    //One signal notification sending function
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
//                                + "\"headings\": {\"en\":\""+ShopperHomepage.shop_name+"\"}"
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
