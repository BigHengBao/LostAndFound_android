package com.heng.lostandfound.fragment;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.heng.lostandfound.R;

/**
 * @author : HengZhang
 * @date : 2022/3/13 11:27
 * 物品详细信息--评论界面
 */

public class GoodsComment extends BaseFragment {
    EditText commentEd;
    Button submitBtn;
    ListView commentLv;

    public GoodsComment() {
    }

    public static GoodsComment newInstance() {
        GoodsComment fragment = new GoodsComment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_goods_comment;
    }

    @Override
    protected void initView() {
        commentEd = mRootView.findViewById(R.id.ed_new_comment);
        submitBtn = mRootView.findViewById(R.id.btn_comment_submit);
        commentLv = mRootView.findViewById(R.id.lv_comment);
    }

    @Override
    protected void initData() {

    }
}
