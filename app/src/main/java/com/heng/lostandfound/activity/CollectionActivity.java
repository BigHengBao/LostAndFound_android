package com.heng.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.heng.lostandfound.entity.CommentItem;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.entity.RecyclerItem;
import com.heng.lostandfound.entity.UserCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/17 20:42
 * 我的收藏界面
 */
public class CollectionActivity extends BaseActivity {
    ImageView backIv;
    RecyclerView mRecyclerView;

    private HomeRecyclerAdapter mHomeAdapter;
    private List<RecyclerItem> mList = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView() {
        backIv = findViewById(R.id.image_collection_back);
        mRecyclerView = findViewById(R.id.me_collection_recycle);

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

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "getCollectionListById");
        params.put("uAccount", getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", ""));

        Api.config(ApiConfig.GET_COLLECTIONS, params).postRequest(this, new ApiCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(final String res) {
//                Log.e("getGoodsInfo onSuccess", res);
                Gson gson = new Gson();
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    List<RecyclerItem> list = gson.fromJson(myResponse.getMsg(), new TypeToken<List<RecyclerItem>>() {
                    }.getType());
                    mList.clear();
                    for (RecyclerItem recyclerItem : list) {
                        if (!mList.contains(recyclerItem)) {
                            mList.add(recyclerItem);
                        }
                    }
                    Log.e("", "getCommentListById:  " + list);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getActivity(), "数据更新成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CollectionActivity.this, "数据更新失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // 设置 item 增加和删除时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeRecyclerAdapter(mList, this);
        mRecyclerView.setAdapter(mHomeAdapter);
    }

}