package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopperProfileActivity extends AppCompatActivity implements Serializable {
    private DatabaseReference fdb;
    FirebaseAuth fba;
    FirebaseUser user;
    CircleImageView viewImage;
    Context applicationContext;
    ShopperDetails profile;
    private TextView mDisplayDate;
    EditText profile_name_first;
    EditText profile_email,profile_phone,profile_username;
    TextView profile_dob;
    EditText profile_name_last;
    FirebaseDatabase firebaseDatabase;
    StorageReference photo_storage;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private FirebaseUser firebaseUser;
    Uri selectedImage;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String bool= "false";
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopper_profile_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fdb= FirebaseDatabase.getInstance().getReference();
        fba=FirebaseAuth.getInstance();
        user=fba.getCurrentUser();
        DatabaseReference shopper = fdb.child ("Shopper").child (user.getUid ());
        applicationContext = ShopperHomepage.getContextOfApplication();
        profile_name_first = (EditText) findViewById(R.id.shoppernameanifirst);
        profile_name_last = (EditText) findViewById(R.id.shoppernameanilast);
        profile_email =(EditText) findViewById (R.id.shopperprofilemailani);
        final Button edit = (Button) findViewById(R.id.editbutton);
        final Button save = (Button) findViewById(R.id.savebutton);
        profile_phone=(EditText) findViewById(R.id.shopperphonenumberani);
        profile_dob=(TextView) findViewById(R.id.shopperprofiledobani);
        profile_username=(EditText) findViewById(R.id.shopperprofileusernameani);
        final  TextView textView=(TextView)findViewById(R.id.dobtextview);
        final Button select = (Button)findViewById(R.id.selectbtnprofile);

        //  edit.setVisibility(View.VISIBLE);
        profile_name_first.setBackgroundResource(android.R.drawable.edit_text);
        profile_name_last.setBackgroundResource(android.R.drawable.edit_text);
        profile_phone.setBackgroundResource(android.R.drawable.edit_text);
        profile_email.setBackgroundResource(android.R.drawable.edit_text);
        profile_username.setBackgroundResource(android.R.drawable.edit_text);
        profile_dob.setBackgroundResource(android.R.drawable.edit_text);
        select.setVisibility(View.GONE);
        photo_storage = FirebaseStorage.getInstance ().getReference ().child ("Shopper_profile_image");

        profile_name_first.setEnabled(false);
        profile_name_last.setEnabled(false);
        profile_email.setEnabled(false);
        //profile_dob.setEnabled(false);
        profile_username.setEnabled(false);
        profile_phone.setEnabled(false);
        profile_dob.setTextColor(getResources().getColor(R.color.diabledgray));
        viewImage=(CircleImageView) findViewById(R.id.shopperprofilepicani);


        shopper.addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profile = dataSnapshot.getValue (ShopperDetails.class);
                assert profile != null;
                profile_name_first.setText(profile.getFname ());
                profile_name_last.setText(profile.getLname ());
                profile_email.setText (profile.getEmail ());
                profile_dob.setText(profile.getDob());
                profile_phone.setText(profile.getPhno());
                profile_username.setText(profile.getUsername());
//                profile_username.setText(profile.get);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fetch();

//        String location = user.getUid ()+"."+"jpg";
//        photo_storage.child (location).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                String imageURL = uri.toString ();
//                Glide.with(ShopperProfileActivity.this).load(imageURL).into(viewImage);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//                Toast.makeText (ShopperProfileActivity.this,exception.getMessage (),Toast.LENGTH_LONG).show ();
//            }
//        });

        edit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                select.setVisibility(View.VISIBLE);
                profile_name_first.setEnabled(true);
                profile_name_last.setEnabled(true);

//                profile_phone.setEnabled(true);
                //  profile_dob.setEnabled(true);
                profile_dob.setTextColor(getResources().getColor(R.color.black));



                select.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View v) {

                        selectImage();

                    }

                });



                profile_dob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(ShopperProfileActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year,month,day);

                        dialog.getDatePicker().setMaxDate(new Date().getTime());
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });

                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        Log.d("dsf", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                        String date = day + "/" + month + "/" + year;
                        profile_dob.setText(date);
                    }
                };


            }
        });
        save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                save.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                select.setVisibility(View.GONE);
                profile_name_first.setEnabled(false);
                profile_name_last.setEnabled(false);
                profile_phone.setEnabled(false);
                profile_dob.setEnabled(false);
                profile_name_first.setBackgroundResource(android.R.drawable.edit_text);
                profile_name_last.setBackgroundResource(android.R.drawable.edit_text);
                profile_dob.setTextColor(getResources().getColor(R.color.diabledgray));
                profile.setFname(profile_name_first.getText().toString().trim());
                profile.setLname(profile_name_last.getText().toString().trim());
                profile.setDob(profile_dob.getText().toString().trim());
                updatedata();
                upload_image ();
//                final Dialog dialog = new Dialog(ShopperProfileActivity.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.info_dialog);
//                dialog.setTitle("Info");
//                dialog.setCancelable(false);
//                ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress_Bar);




//dialog insert
//                profile_name.setBackground(R.drawable.edit_text_shopper_profile);


            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
return true;
    }
//    private void selectImage() {
//
//
//
//        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
//
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(ShopperProfileActivity.this);
//
//        builder.setTitle("Add Photo!");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (options[item].equals("Take Photo"))
//
//                {
//
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
//
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//
//                    startActivityForResult(intent,1);
//
//                }
//
//                else if (options[item].equals("Choose from Gallery"))
//
//                {
//
//                    Intent intent = new   Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                    startActivityForResult(intent, 2);
//
//
//
//                }
//
//                else if (options[item].equals("Cancel")) {
//
//                    dialog.dismiss();
//
//                }
//
//            }
//
//        });
//
//        builder.show();
//
//    }
//
//
//
//    @Override
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.v("ssasad","RESULTCODE:" + Integer.toString(requestCode) );
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            if (requestCode == 1) {
//
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//
//                for (File temp : f.listFiles()) {
//
//                    if (temp.getName().equals("temp.jpg")) {
//
//                        f = temp;
//                        selectedImage = Uri.fromFile (new File (f.toString ()));
//
//                        break;
//
//                    }
//
//                }
//
//                try {
//
//                    Bitmap bitmap;
//
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                            bitmapOptions);
//
//
//
//                    viewImage.setImageBitmap(bitmap);
//
//
//
//                    String path = Environment
//
//                            .getExternalStorageDirectory()
//
//                            + File.separator
//
//                            + "Phoenix" + File.separator + "default";
//
//                    f.delete();
//
//                    OutputStream outFile = null;
//
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//
//                    try {
//
//                        outFile = new FileOutputStream(file);
//
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//
//                        outFile.flush();
//
//                        outFile.close();
//
//                    } catch (FileNotFoundException e) {
//
//                        e.printStackTrace();
//
//                    } catch (IOException e) {
//
//                        e.printStackTrace();
//
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//
//                    }
//
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//
//                }
//
//            } else if (requestCode == 2) {
//
//
//
//                selectedImage = data.getData();
//
//                String[] filePath = { MediaStore.Images.Media.DATA };
//
//                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
//
//                c.moveToFirst();
//
//                int columnIndex = c.getColumnIndex(filePath[0]);
//
//                String picturePath = c.getString(columnIndex);
//
//                c.close();
//
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//                Log.w("pery", picturePath+"");
//
//                viewImage.setImageBitmap(thumbnail);
//
//            }
//
//        }
//
//    }
private void selectImage() {
//removed Take Photo

    final CharSequence[] options = { "Choose from Gallery", "Cancel"};


    AlertDialog.Builder builder = new AlertDialog.Builder (ShopperProfileActivity.this);

    builder.setTitle ("Add Photo!");

    builder.setItems (options, new DialogInterface.OnClickListener () {

        @Override

        public void onClick(DialogInterface dialog, int item) {

            if (options[item].equals ("Take Photo")) {

                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

                File f = new File (android.os.Environment.getExternalStorageDirectory (), "temp.jpg");

              //  intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                // check here for resolving error and you are good to go
                Uri uri = FileProvider.getUriForFile(ShopperProfileActivity.this, BuildConfig.APPLICATION_ID + ".provider",f);
              //  Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", createImageFile());
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult (intent, 1);

            } else if (options[item].equals ("Choose from Gallery")) {

                Intent intent = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult (intent, 2);


            } else if (options[item].equals ("Cancel")) {

                dialog.dismiss ();

            }

        }

    });

    builder.show ();

}


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v ("ssasad", "RESULTCODE:" + Integer.toString (requestCode));

        super.onActivityResult (requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                File f = new File (Environment.getExternalStorageDirectory ().toString ());

                for (File temp : f.listFiles ()) {

                    if (temp.getName ().equals ("temp.jpg")) {

                        f = temp;

                        // adding new peice of code here
                        selectedImage =  Uri.fromFile (new File (f.toString ()));

                        ///

                        break;

                    }

                }

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options ();


                    bitmap = BitmapFactory.decodeFile (f.getAbsolutePath (),

                            bitmapOptions);


                    viewImage.setImageBitmap (bitmap);


                    String path = android.os.Environment

                            .getExternalStorageDirectory ()

                            + File.separator

                            + "Phoenix" + File.separator + "default";

                    f.delete ();

                    OutputStream outFile = null;

                    File file = new File (path, String.valueOf (System.currentTimeMillis ()) + ".jpg");

                    try {

                        outFile = new FileOutputStream (file);

                        bitmap.compress (Bitmap.CompressFormat.JPEG, 85, outFile);

                        outFile.flush ();

                        outFile.close ();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace ();

                    } catch (IOException e) {

                        e.printStackTrace ();

                    } catch (Exception e) {

                        e.printStackTrace ();

                    }

                } catch (Exception e) {

                    e.printStackTrace ();

                }

            } else if (requestCode == 2) {

///// changed uri selectimage  to global variable

                selectedImage = data.getData ();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver ().query (selectedImage, filePath, null, null, null);

                c.moveToFirst ();

                int columnIndex = c.getColumnIndex (filePath[0]);

                String picturePath = c.getString (columnIndex);

                c.close ();

                Bitmap thumbnail = (BitmapFactory.decodeFile (picturePath));

                Log.w ("pery", picturePath + "");

                viewImage.setImageBitmap (thumbnail);

            }

        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (ShopperProfileActivity.this,ShopperHomepage.class);
        intent.putExtra ("bool",bool);
        startActivity (intent);

    }

    private void updatedata(){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myref= database.child("Shopper").child(user.getUid());
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("fname").setValue(profile.getFname());
                dataSnapshot.getRef().child("lname").setValue(profile.getLname());
                dataSnapshot.getRef().child("dob").setValue(profile.getDob());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private String getFileextension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver ();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton ();

        return mimeTypeMap.getExtensionFromMimeType (contentResolver.getType (uri)) ;
    }

    private void upload_image()
    {
        firebaseUser = FirebaseAuth.getInstance ().getCurrentUser ();
        storageReference = FirebaseStorage.getInstance ().getReference ("Shopper_profile_image");
        databaseReference= FirebaseDatabase.getInstance ().getReference ();
        if(selectedImage != null)
        {
//            StorageReference mstorage = storageReference.child (System.currentTimeMillis ()+"."+getFileextension (selectedImage));
            StorageReference mstorage = storageReference.child (firebaseUser.getUid ()+"."+getFileextension (selectedImage));


            mstorage.putFile (selectedImage).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler ();
                    handler.postDelayed (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (ShopperProfileActivity.this,"image uploaded",Toast.LENGTH_SHORT).show ();

                        }
                    },0);





                }
            }).addOnFailureListener (new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText (ShopperProfileActivity.this,e.getMessage (),Toast.LENGTH_SHORT).show ();

                }
            }).addOnProgressListener (new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress=(100.0 * taskSnapshot.getBytesTransferred () / taskSnapshot.getTotalByteCount ());
                    Log.d ("progress" , String.valueOf (progress));
                    dialog = new Dialog(ShopperProfileActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.info_dialog);
                    dialog.setTitle("Info");
                    dialog.setCancelable(false);
                    ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress_Bar);
                    dialog.show ();
                    if(progress == 100)
                    {
                        dialog.dismiss ();
                        Log.d("closing diaolog box" , String.valueOf (progress));
                        dialog.hide ();
                        bool = "true";
                        finish ();
                    }

                }
            });

        }
        else
        {
            Toast.makeText (ShopperProfileActivity.this,"no photo",Toast.LENGTH_SHORT).show ();
        }
    }
    public void fetch()
    {
        try {


            String location = user.getUid () + "." + "jpg";
            photo_storage.child (location).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener <Uri> () {
                @Override
                public void onSuccess(Uri uri) {
                    String imageURL = uri.toString ();
                    Glide.with (getApplicationContext ()).load (imageURL).into (viewImage);
                }
            }).addOnFailureListener (new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    String location2 = user.getUid ()+"."+"null";
                    photo_storage.child (location2).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener <Uri> () {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageurl2 =uri.toString ();
                            Log.v ("url link",imageurl2);
                            Glide.with (getApplicationContext ()).load (imageurl2).into (viewImage);
                        }
                    }).addOnFailureListener (new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText (ShopperProfileActivity.this,e.getMessage (),Toast.LENGTH_LONG).show ();
                        }
                    });
                    //Toast.makeText (ShopperProfileActivity.this, exception.getMessage (), Toast.LENGTH_LONG).show ();
                }
            });
        }catch (NullPointerException e)
        {
            Toast.makeText (ShopperProfileActivity.this,e.getMessage (),Toast.LENGTH_LONG).show ();
        }


    }
}
