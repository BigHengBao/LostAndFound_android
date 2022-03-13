package com.heng.lostandfound.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heng.lostandfound.R;
import com.heng.lostandfound.adapter.GoodsInfoVPAdapter;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.GoodsInfoItem;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.entity.RecyclerItem;
import com.heng.lostandfound.fragment.GoodsComment;
import com.heng.lostandfound.fragment.GoodsIntroductionFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/13 10:14
 * title： 物品详情界面
 */
public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener {

    ImageView goodsImg, backIv;
    Button introductionBtn, commentsBtn;
    ViewPager goodsInfoVp;
    List<Fragment> VPList;
    GoodsInfoVPAdapter adapter;
    String goodsName, authorName;
    GoodsIntroductionFragment introductionFrag;
    GoodsComment commentsFrag;
    GoodsInfoItem goodsInfoItem;

    @Override
    protected int initLayout() {
        return R.layout.activity_goods_info;
    }

    @Override
    protected void initView() {
        goodsImg = findViewById(R.id.goods_image);
        introductionBtn = findViewById(R.id.goods_introduction);
        commentsBtn = findViewById(R.id.goods_comment);
        goodsInfoVp = findViewById(R.id.goods_info_vp);
        backIv = findViewById(R.id.image_goodsinfo_back);
    }

    @Override
    protected void initData() {

        //todo: 获取数据
        goodsName = (String) getIntent().getSerializableExtra("goodsName");
        authorName = (String) getIntent().getSerializableExtra("authorName");

        getGoodsInfo(goodsName, authorName);

        setVPSelectListener();
        backIv.setOnClickListener(this);
        introductionBtn.setOnClickListener(this);
        commentsBtn.setOnClickListener(this);


        initFrag();
    }

    // 获取服务端的指定goodsInfo
    private void getGoodsInfo(String goodsName, String authorName) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "getGoodsInfo");
        params.put("goodsName", goodsName);
        params.put("authorName", authorName);

        Api.config(ApiConfig.GET_GOODS_INFO, params).postRequest(this, new ApiCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(final String res) {
//                Log.e("getGoodsInfo onSuccess", res);
                Gson gson = new Gson();
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    Log.e("", "myResponse onSuccess: " + myResponse);
                    goodsInfoItem = gson.fromJson(myResponse.getMsg(), GoodsInfoItem.class);
                    Log.e("", "goodsInfoItem: " + goodsInfoItem);

                    //todo: 加载数据到内存里
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goodsInfoItem", goodsInfoItem);
                    getIntent().putExtras(bundle);

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
                            Toast.makeText(GoodsInfoActivity.this, "数据更新失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    // 设置vp的监听事件
    private void setVPSelectListener() {

        goodsInfoVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setButtonStyle(position);
            }
        });
    }

    private void initFrag() {

        VPList = new ArrayList<>();

        //添加对象
        introductionFrag = GoodsIntroductionFragment.newInstance();
        commentsFrag = GoodsComment.newInstance();

        VPList.add(introductionFrag);
        VPList.add(commentsFrag);
        assert this.getFragmentManager() != null;
        adapter = new GoodsInfoVPAdapter(getSupportFragmentManager(), VPList);
        goodsInfoVp.setAdapter(adapter);
    }

    /* 设置按钮样式的改变 */
    private void setButtonStyle(int kind) {
        if (kind == 1) {
            introductionBtn.setBackgroundResource(R.drawable.operation_bg);
            commentsBtn.setBackgroundResource(R.drawable.opration_btn_bg);
        } else if (kind == 0) {
            commentsBtn.setBackgroundResource(R.drawable.operation_bg);
            introductionBtn.setBackgroundResource(R.drawable.opration_btn_bg);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goods_introduction:
                setButtonStyle(0);
                goodsInfoVp.setCurrentItem(0);
                break;
            case R.id.goods_comment:
                setButtonStyle(1);
                goodsInfoVp.setCurrentItem(1);
                break;
            case R.id.image_goodsinfo_back:
                finish();
                break;
        }
    }
}