package com.heng.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heng.lostandfound.R;
import com.heng.lostandfound.adapter.HomeRecyclerAdapter;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.entity.RecyclerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserNoticeActivity extends BaseActivity {
    ImageView backIv;
    private RecyclerView mRecyclerView;
    private HomeRecyclerAdapter mHomeAdapter;
    private List<RecyclerItem> mList = new ArrayList<>();


    @Override
    protected int initLayout() {
        return R.layout.activity_user_notice;
    }

    @Override
    protected void initView() {
        backIv = findViewById(R.id.image_usernotice_back);
        mRecyclerView = findViewById(R.id.usernotice_recycler_view);
    }

    @Override
    protected void initData() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initRecycler();
    }

    // 加载显示界面
    private void initRecycler() {
        // 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        getUserOrders();

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置 item 增加和删除时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeRecyclerAdapter(mList, this);
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    //获得当前用户发布的所有启事
    private void getUserOrders() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "getUserAllOrder");
        params.put("userAccount", getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", ""));


        Api.config(ApiConfig.GET_USER_ORDER, params).postRequest(UserNoticeActivity.this, new ApiCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(final String res) {
                Log.e("HomeFragment getAllOrder onSuccess", res);
                Gson gson = new Gson();
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    Log.e("", "onSuccess: " + myResponse);

                    /**
                     * 解析赋值
                     * 把Json字符串 解析成List<RecyclerItem>
                     * 相当于走了两部，json->list,list<object>-->list<RecyclerItem>
                     */

                    List<RecyclerItem> list = gson.fromJson(myResponse.getMsg(), new TypeToken<List<RecyclerItem>>() {
                    }.getType());
                    mList.clear();
                    for (RecyclerItem recyclerItem : list) {
                        if (!mList.contains(recyclerItem)) {
                            mList.add(recyclerItem);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getActivity(), "数据更新成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}