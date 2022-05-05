package com.example.englanguage.adapter;

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

public class ViewPagerAdapterFragment456 extends FragmentStatePagerAdapter {


    public ViewPagerAdapterFragment456(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FlipCardFragment4();
            case 1:
                return new FlipCardFragment5();
            case 2:
                return new FlipCardFragment6();
            default:
                return new FlipCardFragment4();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
