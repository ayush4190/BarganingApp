package com.example.ayush.finalapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ShopperForm extends Activity implements Serializable {
   private FirebaseAuth firebaseAuth;
   private FirebaseUser firebaseUser;
   private DatabaseReference databaseReference, mroot;



    ImageView viewImage;

    Button b;
    String userp, passwordv;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        super.onCreate(savedInstanceState);

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


                final EditText password = (EditText) findViewById(R.id.password);
                EditText confirmpassword = (EditText) findViewById(R.id.confirm_password);

                EditText username = (EditText) findViewById(R.id.username);
                ImageView propic = (ImageView) findViewById(R.id.profilepic);



                //username
                userp=username.getText().toString().trim();
                final String userv = "^[a-zA-Z0-9]+([_.a-zA-Z0-9])*$";

                Pattern userx= Pattern.compile(userv);
                Matcher matcher2 = userx.matcher(userp);

               passwordv = password.getText().toString().trim();

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                Pattern passwordp= Pattern.compile(PASSWORD_PATTERN);
                Matcher matcher = passwordp.matcher(passwordv);


                final ImageView test = (ImageView) findViewById(R.id.profilepic);
                final Bitmap bmap = ((BitmapDrawable)test.getDrawable()).getBitmap();
                Drawable myDrawable = getResources().getDrawable(R.drawable.user);
                final Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();


                if(!matcher2.matches()) {
                    k--;
                    username.setError("Invalid Username");

                }
                if(!matcher.matches()) {
                    k--;
                    password.setError("Invalid Password");

                }
                if(!confirmpassword.getText().toString().equals(password.getText().toString())){
                    k--;
                    confirmpassword.setError("Passwords are not same");
                }


                if(bmap.sameAs(myLogo)){
                    k--;
                    Toast.makeText(ShopperForm.this, "Add Profile Picture", Toast.LENGTH_SHORT).show();

                }

                if(k==4) {
                    registeruser ();
                    firebaseUser = firebaseAuth.getCurrentUser ();
                    basic ();

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
    private void registeruser()
    {
        ShopperProfile profile;
        profile = (ShopperProfile) getIntent ().getSerializableExtra ("profile");
        String muser, mpass;
        muser = userp;
        mpass = passwordv;
        firebaseAuth.createUserWithEmailAndPassword(profile.getMeamil (),mpass).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful ())
                {
                    Toast.makeText (ShopperForm.this,"Shopper registered successfully",Toast.LENGTH_SHORT).show ();
                }
                else
                {
                    Toast.makeText (ShopperForm.this,"please try after some time",Toast.LENGTH_SHORT).show ();
                }
            }
        });

    }

        private  void basic()
        {
            mroot = FirebaseDatabase.getInstance ().getReference ();
            ShopperProfile ob1;
            ob1 = (ShopperProfile) getIntent ().getSerializableExtra ("profile");
            databaseReference = mroot.child (firebaseUser.getUid ());
            databaseReference.child ("basic").setValue (ob1);
        }
}




