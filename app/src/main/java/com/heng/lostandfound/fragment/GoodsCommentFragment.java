package com.heng.lostandfound.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heng.lostandfound.R;
import com.heng.lostandfound.adapter.CommentsLvAdapter;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.Comment;
import com.heng.lostandfound.entity.CommentItem;
import com.heng.lostandfound.entity.GoodsInfoItem;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.utils.Constant;
import com.heng.lostandfound.utils.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/13 11:27
 * 物品详细信息--评论界面
 */

public class GoodsCommentFragment extends BaseFragment {
    EditText commentEd;
    Button submitBtn;
    ListView commentLv;
    GoodsInfoItem goodsInfoItem = null;
    String userAccount;

    List<CommentItem> commentItems = new ArrayList<>();

    public GoodsCommentFragment() {
    }

    public static GoodsCommentFragment newInstance() {
        GoodsCommentFragment fragment = new GoodsCommentFragment();
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
        setComments();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userAccount = sharedPreferences.getString("username", "");
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if ((StringUtils.isEmpty(commentEd.getText().toString().trim()))) {
                    showToast("不能发表空评论!!");
                } else {
                    addComment();

                }
            }
        });
    }

    //封装数据
    private void addComment() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle goodsInfoBundle = getActivity().getIntent().getExtras();
        goodsInfoItem = (GoodsInfoItem) goodsInfoBundle.getSerializable("goodsInfoItem");

        submitComment(new Comment(goodsInfoItem.getGoods().getgName(), userAccount,
                commentEd.getText().toString().trim(), new Timestamp(System.currentTimeMillis()).toString(),
                Constant.COMMENT_ACTIVE_TRUE, goodsInfoItem.getAuthorName()));
    }

    //提交数据
    public void submitComment(Comment comment) {
        Gson gson = new Gson();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "submitComment");
        params.put("comment", gson.toJson(comment));

        System.out.println("submitOrder param:" + params.toString());
        Api.config(ApiConfig.ADD_GOODS_COMMENT, params).postRequest(getActivity(), new ApiCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("submitComment onSuccess", res);
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    Log.e("submitOrder", "onSuccess: " + myResponse);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("启事提交成功");
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("启事提交失败");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    //显示所有评论
    private void setComments() {
        try {
            Thread.sleep(150);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle commentsBundle = getActivity().getIntent().getExtras();
        String commentListStr = (String) commentsBundle.getSerializable("commentListStr");
        List<CommentItem> commentItems = new Gson().fromJson(commentListStr, new TypeToken<List<CommentItem>>() {
        }.getType());

        CommentsLvAdapter commentsLvAdapter = new CommentsLvAdapter(getContext(), commentItems);
        commentLv.setAdapter(commentsLvAdapter);
    }
}
