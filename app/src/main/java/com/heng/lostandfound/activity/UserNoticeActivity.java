package com.heng.lostandfound.activity;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heng.lostandfound.R;
import com.heng.lostandfound.adapter.HomeRecyclerAdapter;
import com.heng.lostandfound.entity.RecyclerItem;

import java.util.ArrayList;
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


        // 设置 item 增加和删除时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeRecyclerAdapter(mList, this);
        mRecyclerView.setAdapter(mHomeAdapter);
    }
}