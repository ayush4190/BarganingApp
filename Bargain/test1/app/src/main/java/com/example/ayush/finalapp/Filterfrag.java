package com.example.ayush.finalapp;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Filterfrag extends Fragment{
     TextView agetext;
     SeekBar ageseek;
    int ageprogress=0;
    Button submitButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.filterfrag,null);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        ageseek=(SeekBar)view.findViewById(R.id.seekBar);
        agetext=(TextView)view.findViewById(R.id.agetextview);
       // agetext.setText(ageseek.getProgress());
        submitButton=(Button)view.findViewById(R.id.submitfilter);
        ageseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ageprogress=progress;
                agetext.setText(String.valueOf (ageprogress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    submitButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Toast.makeText (getActivity (),"here",Toast.LENGTH_SHORT).show ();
            FragmentManager fragmentManager = getActivity().getFragmentManager();

            assert getFragmentManager() != null;
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            Searchfrag ldf = new Searchfrag ();
            Bundle args = new Bundle();
            args.putString ("Agevalue", String.valueOf (ageprogress));
            ldf.setArguments(args);
            fragmentTransaction.addToBackStack("Age");

            fragmentTransaction.replace(R.id.content_frame, ldf).commit();

        }
    });

    }




}
