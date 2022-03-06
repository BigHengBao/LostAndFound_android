package com.heng.lostandfound.fragment;

import com.heng.lostandfound.R;

/**
 * @author : HengZhang
 * @date : 2022/3/6 17:17
 */

public class NewsFragment extends BaseFragment {

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
