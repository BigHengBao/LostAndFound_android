package com.heng.lostandfound.fragment;

import com.heng.lostandfound.R;

/**
 * @author : HengZhang
 * @date : 2022/3/6 17:17
 */

public class MyFragment extends BaseFragment {


    public MyFragment() {
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
