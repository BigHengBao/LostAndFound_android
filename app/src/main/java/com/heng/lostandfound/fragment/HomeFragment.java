package com.heng.lostandfound.fragment;

import com.heng.lostandfound.R;

/**
 * @author : HengZhang
 * @date : 2022/3/6 17:12
 * title： 主界面
 */

public class HomeFragment extends BaseFragment{

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
