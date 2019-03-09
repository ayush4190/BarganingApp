package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ShopperProfileFragment extends Fragment {
    private DatabaseReference fdb;
    FirebaseAuth fba;
    FirebaseUser user;
    CircleImageView viewImage;
    Context applicationContext;
    ShopperProfile profile;
    private TextView mDisplayDate;
    EditText profile_name_first;
    TextView profile_dob;
    EditText profile_name_last;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.shopper_profile_fragment,null);
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        fdb= FirebaseDatabase.getInstance().getReference();
        fba=FirebaseAuth.getInstance();
        user=fba.getCurrentUser();
        DatabaseReference shopper = fdb.child ("Shopper").child (user.getUid ());
        applicationContext = ShopperHomepage.getContextOfApplication();
         profile_name_first = (EditText) view.findViewById(R.id.shoppernameanifirst);
         profile_name_last = (EditText) view.findViewById(R.id.shoppernameanilast);
        final EditText profile_email =(EditText) view.findViewById (R.id.shopperprofilemailani);
        final Button edit = (Button) view.findViewById(R.id.editbutton);
        final Button save = (Button) view.findViewById(R.id.savebutton);
        final EditText profile_phone=(EditText) view.findViewById(R.id.shopperphonenumberani);
         profile_dob=(TextView) view.findViewById(R.id.shopperprofiledobani);
        final EditText profile_username=(EditText) view.findViewById(R.id.shopperprofileusernameani);
        final  TextView textView=(TextView)view.findViewById(R.id.dobtextview);
        final Button select = (Button)view.findViewById(R.id.selectbtnprofile);
      //  edit.setVisibility(View.VISIBLE);
        profile_name_first.setBackgroundResource(android.R.drawable.edit_text);
        profile_name_last.setBackgroundResource(android.R.drawable.edit_text);
        profile_phone.setBackgroundResource(android.R.drawable.edit_text);
        profile_email.setBackgroundResource(android.R.drawable.edit_text);
        profile_username.setBackgroundResource(android.R.drawable.edit_text);
        profile_dob.setBackgroundResource(android.R.drawable.edit_text);
        select.setVisibility(View.GONE);

        profile_name_first.setEnabled(false);
        profile_name_last.setEnabled(false);
        profile_email.setEnabled(false);
        //profile_dob.setEnabled(false);
        profile_username.setEnabled(false);
        profile_phone.setEnabled(false);
        profile_dob.setTextColor(getResources().getColor(R.color.diabledgray));



        shopper.addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profile = dataSnapshot.getValue (ShopperProfile.class);
                assert profile != null;
                profile_name_first.setText(profile.getFname ());
                profile_name_last.setText(profile.getLname ());
                profile_email.setText (profile.getEmail ());
                profile_dob.setText(profile.getDob());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

                viewImage=(CircleImageView) view.findViewById(R.id.shopperprofilepicani);

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

                        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
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



//                profile_name.setBackground(R.drawable.edit_text_shopper_profile);


            }
        });

    }
    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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


                Cursor c = applicationContext.getContentResolver().query(selectedImage,filePath, null, null, null);

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
}
