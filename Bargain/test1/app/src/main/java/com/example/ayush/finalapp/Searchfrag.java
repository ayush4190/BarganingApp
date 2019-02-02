package com.example.ayush.finalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

public class Searchfrag extends Fragment {

    SearchView searchbut;
    EditText searchfragedit;
    String searchtext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.search_frag,null);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        searchbut=(SearchView) view.findViewById(R.id.search);
        int idtxt = searchbut.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        searchfragedit = (EditText)view.findViewById(idtxt);//initialising the searched text in an edit text
        //to make the keyboard pop up when fragment opens
        searchfragedit.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);




//       searchtext= searchbut.getQuery().toString();
//        Log.v("search value ",searchtext);

        searchbut.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchtext= searchbut.getQuery().toString();
                Log.v("search value ",searchtext);
                Intent intent = new Intent(getActivity(), SearchDisplay.class);
                intent.putExtra("Searchtext",searchtext);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




    }

}

