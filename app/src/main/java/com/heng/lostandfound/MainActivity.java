package com.heng.lostandfound;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.heng.lostandfound.activity.BaseActivity;
import com.heng.lostandfound.adapter.MainPagerAdapter;
import com.heng.lostandfound.entity.TabEntity;
import com.heng.lostandfound.fragment.HomeFragment;
import com.heng.lostandfound.fragment.MeFragment;
import com.heng.lostandfound.fragment.NoticeFragment;

import java.util.ArrayList;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {
    private CommonTabLayout commonTabLayout;
    private String[] mTitles = {"首页", "招领", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.application_false, R.mipmap.add_false,
            R.mipmap.me_false};
    private int[] mIconSelectIds = {
            R.mipmap.application_true, R.mipmap.add_true,
            R.mipmap.me_true};
    private ViewPager viewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }

    @Override
    protected void initData() {

        String userAccount = (String) getIntent().getSerializableExtra("userAccount");
        Log.e("TAG", "initData: userAccount: " + userAccount);
        //todo: 加载数据到内存里
        Bundle bundle = new Bundle();
        bundle.putSerializable("userAccount", userAccount);

        mFragments.add(HomeFragment.newInstance());
        mFragments.add(NoticeFragment.newInstance());
        mFragments.add(MeFragment.newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    }


}