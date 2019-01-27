package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Negotiator_final extends AppCompatActivity {
    ImageView viewImage;
    Button b;
     String s1,s2,s3,phone;
     private DatabaseReference databaseReference , mroot;
     private NegotiatorField obj;



    Button proceed;
    AlertDialog.Builder builder2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negotiator_final);
        final CheckBox rb2= (CheckBox) findViewById(R.id.rb);
       mroot = FirebaseDatabase.getInstance().getReference();
    phone = getIntent().getExtras().getString("phon");

        rb2.setChecked(!rb2.isChecked());

        b=(Button)findViewById(R.id.btnSelectID);

        viewImage=(ImageView)findViewById(R.id.idimage); // id picture for valid id .


        TextView term1 = (TextView)findViewById(R.id.terms);

        term1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {


                builder2 = new AlertDialog.Builder(Negotiator_final.this);

                LayoutInflater inflater = Negotiator_final.this.getLayoutInflater();
                builder2.setView(inflater.inflate(R.layout.terms, null))
                        // Add action buttons

                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...
                                //radio
                                CheckBox rb= (CheckBox) findViewById(R.id.rb);
                                rb.setChecked(true);


                            }
                        })
                        .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CheckBox rb= (CheckBox) findViewById(R.id.rb);
                                if(rb.isChecked())
                                    rb.setChecked(!rb.isChecked());
                                dialog.cancel();
                            }
                        });


                //Setting message manually and performing action on button click
                builder2.setCancelable(false);

                //Creating dialog box
                AlertDialog alert = builder2.create();

                alert.show();

            }




            //

        });

        //spinner
        final Spinner mySpinner1 = (Spinner) findViewById(R.id.area1);
        final Spinner mySpinner2= (Spinner) findViewById(R.id.area2);
        final Spinner mySpinner3 = (Spinner) findViewById(R.id.area3);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Negotiator_final.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cat))
        {

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0 || (mySpinner1.getSelectedItemPosition()== position) || (mySpinner2.getSelectedItemPosition() == position)||(mySpinner3.getSelectedItemPosition() == position)){
                    // Set the hint text color gray

                    tv.setTextColor(Color.GRAY);
                }

                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };


        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(myAdapter);
        mySpinner2.setAdapter(myAdapter);
        mySpinner3.setAdapter(myAdapter);



        b.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                selectImage();

            }

        });




        Button nextButton = (Button) findViewById(R.id.next3);


        nextButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                int k=2;

// changed final string to variable name
                 s1=  mySpinner1.getSelectedItem().toString().trim();// Stored value is here
                 s2=  mySpinner2.getSelectedItem().toString().trim();
                s3=  mySpinner3.getSelectedItem().toString().trim();
                if (s1.equals("Select Category") && s2.equals("Select Category") && s3.equals("Select Category") ) {
                    k--;
                    Toast.makeText(Negotiator_final.this, "not all can be empty", Toast.LENGTH_SHORT).show();
                }

                if(viewImage.getDrawable()==null){
                    k--;
                    Toast.makeText(Negotiator_final.this, "Enter an ID", Toast.LENGTH_SHORT).show();

                }
                if(k==2 && rb2.isChecked()){
                    categoryregis();
                    Intent myIntent = new Intent(Negotiator_final.this,
                            MainActivity.class);
                    startActivity(myIntent);
                }

            }
        });


    }





    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        AlertDialog.Builder builder = new AlertDialog.Builder(Negotiator_final.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    startActivityForResult(intent,1);

                }

                else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);



                }

                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("ssasad","RESULTCODE:" + Integer.toString(requestCode) );

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;

                        break;

                    }

                }

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),

                            bitmapOptions);


                    viewImage.setImageBitmap(bitmap);


                    String path = android.os.Environment

                            .getExternalStorageDirectory()

                            + File.separator

                            + "Phoenix" + File.separator + "default";

                    f.delete();

                    OutputStream outFile = null;

                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {

                        outFile = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);

                        outFile.flush();

                        outFile.close();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            } else if (requestCode == 2) {


                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                Log.w("pery", picturePath + "");

                viewImage.setImageBitmap(thumbnail);

            }

        }
    }
    private void categoryregis()
    {
        String cat1,cat2,cat3;
        cat1 =  s1;
        cat2 = s2;
        cat3 = s3;
        obj =new NegotiatorField(s1,s2,s3);


        databaseReference = mroot.child("Negotiator");
        databaseReference.child(phone).child("Field").setValue(obj);
        Toast.makeText(Negotiator_final.this,"Registered successfully",Toast.LENGTH_LONG).show();



    }
}




