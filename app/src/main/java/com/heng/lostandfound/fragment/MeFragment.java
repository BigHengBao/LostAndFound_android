package com.heng.lostandfound.fragment;

import com.heng.lostandfound.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : HengZhang
 * @date : 2022/3/6 17:17
 */

public class MeFragment extends BaseFragment {
    CircleImageView iconIv;

    public MeFragment() {
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
