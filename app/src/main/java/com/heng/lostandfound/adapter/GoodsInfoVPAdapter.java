package com.heng.lostandfound.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/13 11:57
 */

public class GoodsInfoVPAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;

    public GoodsInfoVPAdapter(androidx.fragment.app.FragmentManager fragmentManager, List<Fragment> vpList) {
        super(fragmentManager);
        this.fragmentList = vpList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
