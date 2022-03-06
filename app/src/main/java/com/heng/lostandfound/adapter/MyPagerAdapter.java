package com.heng.lostandfound.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
/**
 * @author : HengZhang
 * @date : 2022/3/6 17:22
 * title: main的viewpager的adapter
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private ArrayList<Fragment> mFragments;

    public MyPagerAdapter(FragmentManager fm, String[] titles, ArrayList<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
