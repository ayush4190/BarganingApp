package com.example.ayush.finalapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomerServiceFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.customer_service_frag,null);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        CardView address= (CardView)view.findViewById(R.id.cs_address);
        CardView email= (CardView)view.findViewById(R.id.cs_email);
        CardView phone= (CardView)view.findViewById(R.id.cs_phone);
        TextView phone_value =(TextView)view.findViewById(R.id.cs_phone_value);
        final String ph_value = phone_value.getText().toString();
        TextView email_value =(TextView)view.findViewById(R.id.cs_email_value);
        final String e_value = email_value.getText().toString();
        TextView add_value = (TextView)view.findViewById(R.id.cs_address_value) ;
        final String a_value = add_value.getText().toString();

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ph_value ));
                startActivity(intent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = null;
                try {
                    body = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
                    body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
                            Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                            "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
                } catch (PackageManager.NameNotFoundException e) {
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{e_value});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Customer Service Request From Android App");
                intent.putExtra(Intent.EXTRA_TEXT, body);
                getActivity().startActivity(Intent.createChooser(intent, getActivity().getString(R.string.choose_email_client)));
            }

        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=1600"+a_value);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }

}
