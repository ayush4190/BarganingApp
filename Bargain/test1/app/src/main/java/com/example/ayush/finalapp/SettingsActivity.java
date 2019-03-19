package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.L;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class SettingsActivity extends AppCompatPreferenceActivity implements Serializable {
    private static final String TAG = SettingsActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();

    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        AlertDialog.Builder builder2;
        FirebaseUser user;
        FirebaseAuth fba;
        StorageReference photo_storage;
        String imageURL;
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);
            fba = FirebaseAuth.getInstance ();
            photo_storage=FirebaseStorage.getInstance ().getReference ().child ("Shopper_profile_image");
            user = fba.getCurrentUser ();
            // gallery EditText change listener
            //bindPreferenceSummaryToValue(findPreference(getString(R.string.key_gallery_name)));

            // notification preference change listener
            bindPreferenceSummaryToValue(findPreference(getString(R.string.key_notifications_new_message_ringtone)));

            // feedback preference click listener
            Preference myPref = findPreference(getString(R.string.key_send_feedback));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    sendFeedback(getActivity());
                    return true;
                }
            });

            Preference toc = findPreference(getString(R.string.key_terms));

            toc.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    FragmentTransaction fragmentTransaction = getFragmentManager ().beginTransaction ();
                    fragmentTransaction.replace(android.R.id.content,new TocFrag ());
                    fragmentTransaction.addToBackStack("wallet");
                    fragmentTransaction.commit ();
                    return false;
                }
            });
            Preference privacy = findPreference(getString(R.string.key_privacy));

            privacy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    FragmentTransaction fragmentTransaction = getFragmentManager ().beginTransaction ();
                    fragmentTransaction.replace(android.R.id.content,new PrivacyFrag ());
                    fragmentTransaction.addToBackStack("wallet");
                    fragmentTransaction.commit ();
                    return false;
                }
            });
            Preference delete = findPreference(getString(R.string.key_delete));

            delete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    builder2 = new AlertDialog.Builder (getActivity());
                    builder2.setTitle("Delete Account");
                    builder2.setMessage("Are you sure? This action cannot be reversed. Your account will be permanently deleted.");

                    builder2.setPositiveButton("Accept and Continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                            String location = user.getUid () + "." + "jpg";
//                            photo_storage.child (location).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener <Uri> () {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    imageURL = uri.toString ();
//                                    Log.v("manas2",imageURL);
////                                    Glide.with (getApplicationContext ()).load (imageURL).into (shopper_pic);
//                                }
//                            }).addOnFailureListener (new OnFailureListener () {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    // Handle any errors
////                                    Toast.makeText (ShopperHomepage.this, exception.getMessage (), Toast.LENGTH_LONG).show ();
//                                }
//                            });

//                            Log.v("manas",photo_storage.getPath().toString());
                            DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference().child("Shopper").child(user.getUid());
                           String path = user.getUid ()+".jpg";
                           StorageReference storageReference = photo_storage.child (path);
                            try{
                                storageReference.delete ().addOnSuccessListener (new OnSuccessListener <Void> () {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.v ("Sucessful deletion ",aVoid.toString ());
                                    }
                                }).addOnFailureListener (new OnFailureListener () {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.v ("error while deletion",e.getMessage ());

                                    }
                                });
                            driverRef.removeValue();
//                            StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageURL);
//                            photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    // File deleted successfully
//                                    Log.d(TAG, "onSuccess: deleted file");
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    // Uh-oh, an error occurred!
//                                    Log.d(TAG, "onFailure: did not delete file");
//                                }
//                            });
////                            photo_storage.child(imageURL).delete();
                            user.delete();
                                fba.signOut ();
                               SessionManagment sessionManagment = new SessionManagment (getActivity ().getApplicationContext ());


                                sessionManagment.logoutUser ();

                            Intent intent = new Intent (getActivity (),WelcomePage.class);
                            intent.putExtra ("key","deleted");
                            startActivity (intent);

                        }catch (NullPointerException e)
                        {

                        }

                        // add account delete code
                        }
                    });
                    builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                            // here you can add functions
                        }
                    });


                    builder2.show();  //<-- See This!

                    return false;
                }
            });

//            SwitchPreference notification =(SwitchPreference)findPreference(getString(R.string.notifications_new_message));
//            notification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    boolean istrue = (boolean)newValue;
//                    if(istrue){
//
//
//
//                    }
//
//                    return false;
//                }
//            });






        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(R.string.summary_choose_ringtone);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            }
//            else if (preference instanceof EditTextPreference) {
//                if (preference.getKey().equals("key_gallery_name")) {
//                    // update the changed gallery name to summary filed
//                    preference.setSummary(stringValue);
//                }
//            }
            else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Email client intent to send support mail
     * Appends the necessary device information to email body
     * useful when providing support
     */
    public static void sendFeedback(Context context) {
        String body = null;
        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact@BargainingPartners.info"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack From Android App");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
    }
}
