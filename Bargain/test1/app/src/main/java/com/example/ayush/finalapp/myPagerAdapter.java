package com.example.ayush.finalapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class myPagerAdapter extends FragmentStatePagerAdapter {

    Fragment frag1;

    public myPagerAdapter(FragmentManager fm, Fragment frag1) {
        super(fm);
        this.frag1 = frag1;

    }

    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                return frag1;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    //@Nullable
    //@Override
    //public CharSequence getPageTitle(int position) {
      //  switch(position)
        //{
          //  case 1:

        //}
   // }
}
