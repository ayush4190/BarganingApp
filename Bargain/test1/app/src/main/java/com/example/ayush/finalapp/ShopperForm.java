package com.example.ayush.finalapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ShopperForm extends Activity implements Serializable {
    AlertDialog.Builder builder2;

    private static final String TAG = "ShopperForm";

    private FirebaseAuth firebaseAuth;
   private FirebaseUser firebaseUser;
   private DatabaseReference databaseReference, mroot;


    EditText phno;
    TextView dob ;


    ImageView viewImage;

    Button b;


    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;






    @Override

    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        super.onCreate(savedInstanceState);
        final CheckBox rb2= (CheckBox) findViewById(R.id.rb);
        rb2.setChecked(!rb2.isChecked());
        TextView term1 = (TextView)findViewById(R.id.terms);

        term1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {


                builder2 = new AlertDialog.Builder(ShopperForm.this);

                LayoutInflater inflater = ShopperForm.this.getLayoutInflater();
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

        mDisplayDate = (TextView) findViewById(R.id.dob);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ShopperForm.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);

                dialog.getDatePicker().setMaxDate(new Date ().getTime());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable (Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };
        setContentView(R.layout.activity_shopper_form);
        firebaseAuth = FirebaseAuth.getInstance();

        b=(Button)findViewById(R.id.btnSelectPhoto);

        viewImage=(ImageView)findViewById(R.id.profilepic);

        b.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                selectImage();

            }

        });
        Button nextButton = (Button) findViewById(R.id.next1);


        // Capture button clicks
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=4;

                phno = (EditText) findViewById(R.id.phone);
                dob =(TextView) findViewById(R.id.dob);

                final EditText password = (EditText) findViewById(R.id.password);
                EditText confirmpassword = (EditText) findViewById(R.id.confirm_password);

                EditText username = (EditText) findViewById(R.id.username);
                ImageView propic = (ImageView) findViewById(R.id.profilepic);



                //username

                final ImageView test = (ImageView) findViewById(R.id.profilepic);
                final Bitmap bmap = ((BitmapDrawable)test.getDrawable()).getBitmap();
                Drawable myDrawable = getResources().getDrawable(R.drawable.user);
                final Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();

                if( TextUtils.isEmpty(dob.getText())) {

                    k--;
                    Toast.makeText(ShopperForm.this, "Date of Birth is Required!", Toast.LENGTH_SHORT).show();

                }
                if( TextUtils.isEmpty(phno.getText()) || phno.length()!=10) {

                    k--;
                    phno.setError("Invalid Phone number");

                }

                if(bmap.sameAs(myLogo)){
                    k--;
                    Toast.makeText(ShopperForm.this, "Add Profile Picture", Toast.LENGTH_SHORT).show();

                }

                if(k==4  && rb2.isChecked()) {
//                    registeruser ();
                    firebaseUser = firebaseAuth.getCurrentUser ();
                   // basic ();
//// open this activity only when shoper is signed in or has  registered successfully.
                    Intent myIntent = new Intent(ShopperForm.this,
                           ShopperHomepage.class);
                    startActivity(myIntent);
                }


            }

        });

    }







    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        AlertDialog.Builder builder = new AlertDialog.Builder(ShopperForm.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    startActivityForResult(intent,1);

                }

                else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new   Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

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



                    String path = Environment

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

                String[] filePath = { MediaStore.Images.Media.DATA };

                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                Log.w("pery", picturePath+"");

                viewImage.setImageBitmap(thumbnail);

            }

        }

    }

//    private void registeruser()
//    {
//        ShopperProfile profile;
//        profile = (ShopperProfile) getIntent ().getSerializableExtra ("profile");
//        String muser, mpass;
//        muser = userp;
//        mpass = passwordv;
//        firebaseAuth.createUserWithEmailAndPassword(profile.getMeamil (),mpass).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful ())
//                {
//                    Toast.makeText (ShopperForm.this,"Shopper registered successfully",Toast.LENGTH_SHORT).show ();
//                }
//                else
//                {
//                    Toast.makeText (ShopperForm.this,"please try after some time",Toast.LENGTH_SHORT).show ();
//                }
//            }
//        });
//
//    }

      /*  private  void basic()
        {
            mroot = FirebaseDatabase.getInstance ().getReference ();
            ShopperProfile ob1;
            ob1 = (ShopperProfile) getIntent ().getSerializableExtra ("profile");
            databaseReference = mroot.child (firebaseUser.getUid ());
            databaseReference.child ("basic").setValue (ob1);
        }*/
}




