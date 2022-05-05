package com.example.englanguage.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.englanguage.fragmentflipcard1.FlipCardFragment1;
import com.example.englanguage.fragmentflipcard1.FlipCardFragment2;
import com.example.englanguage.fragmentflipcard1.FlipCardFragment3;
import com.example.englanguage.fragmentflipcard2.FlipCardFragment4;
import com.example.englanguage.fragmentflipcard2.FlipCardFragment5;
import com.example.englanguage.fragmentflipcard2.FlipCardFragment6;

public class ViewPagerAdapterFragment123 extends FragmentStatePagerAdapter {

    public ViewPagerAdapterFragment123(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FlipCardFragment1();
            case 1:
                return new FlipCardFragment2();
            case 2:
                return new FlipCardFragment3();
            case 3:
                return new FlipCardFragment4();
            case 4:
                return new FlipCardFragment5();
            case 5:
                return new FlipCardFragment6();
            default:
                return new FlipCardFragment1();
        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}
