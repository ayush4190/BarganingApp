package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NegotiatorId extends AppCompatActivity {
    ImageView viewImage;
    Button b;
    ////// java class for adding profile photo
    private Uri selectedImage;

    ///to store image

    private FirebaseStorage mstorage;

    private StorageReference store;

    //
    DatabaseReference data;

    //
    public static final String FB_STORAGE_PATH = "image/";

    public static final String FB_DATABASE_PATH = "image";

    //  private ProgressDialog progressDial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder ();
        StrictMode.setVmPolicy (builder.build ());
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_negotiator_id);


        //////////
        store = FirebaseStorage.getInstance ().getReference ();
        data = FirebaseDatabase.getInstance ().getReference ();

        //////////


        b = (Button) findViewById (R.id.btnSelectPhoto);

        viewImage = (ImageView) findViewById (R.id.profilepic);


        b.setOnClickListener (new View.OnClickListener () {

            @Override

            public void onClick(View v) {

                selectImage ();

            }

        });

        Button nextButton = (Button) findViewById (R.id.next1);

        nextButton.setOnClickListener (new View.OnClickListener () {


            @Override
            public void onClick(View v) {

                //3c2
                final ImageView test = (ImageView) findViewById (R.id.profilepic); //image stored here
                final Bitmap bmap = ((BitmapDrawable) test.getDrawable ()).getBitmap ();
                Drawable myDrawable = getResources ().getDrawable (R.drawable.user);
                final Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap ();

                if (bmap.sameAs (myLogo)) {
                    //   k--;
                    Toast.makeText (NegotiatorId.this, "Add Profile Picture", Toast.LENGTH_SHORT).show ();

                } else {
                   // uploadprofilephoto ();
                    Toast.makeText (NegotiatorId.this, "what the ",Toast.LENGTH_SHORT).show ();
                    Intent myIntent = new Intent (NegotiatorId.this,
                            Negotiator_final.class);
                    startActivity (myIntent);

                }
            }
        });

    }


    private void selectImage() {


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder (NegotiatorId.this);

        builder.setTitle ("Add Photo!");

        builder.setItems (options, new DialogInterface.OnClickListener () {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals ("Take Photo")) {

                    Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File (android.os.Environment.getExternalStorageDirectory (), "temp.jpg");

                    intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));

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



   /* public String getImage(Uri uri)
    {

        ContentResolver contentResolver = getContentResolver ();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton ();
        return mimeTypeMap.getExtensionFromMimeType (contentResolver.getType (uri));
    }
    ///// function to add image
    //// function not complete yet*/

    private void uploadprofilephoto() {

        StorageReference str;


            str = FirebaseStorage.getInstance ().getReference ().child ("photo").child (selectedImage.getLastPathSegment ());
            profileSource src = new profileSource (str.toString ());
            str.putFile (selectedImage).addOnSuccessListener (new OnSuccessListener <UploadTask.TaskSnapshot> () {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText (NegotiatorId.this,"hello uploaded",Toast.LENGTH_SHORT).show ();

                }
            }).addOnFailureListener (new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText (NegotiatorId.this,"hello " + e.getMessage (),Toast.LENGTH_SHORT).show ();

                }
            });

    }
}


