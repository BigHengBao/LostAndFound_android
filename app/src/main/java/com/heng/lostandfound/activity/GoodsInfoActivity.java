package com.heng.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.heng.lostandfound.R;
import com.heng.lostandfound.adapter.GoodsInfoVPAdapter;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.GoodsInfoItem;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.entity.UserCollection;
import com.heng.lostandfound.fragment.GoodsCommentFragment;
import com.heng.lostandfound.fragment.GoodsIntroductionFragment;
import com.heng.lostandfound.utils.Constant;
import com.heng.lostandfound.utils.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : HengZhang
 * @date : 2022/3/13 10:14
 * title： 物品详情界面
 */
public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener {

    ImageView goodsInfoTv, backIv, collectionIv;
    CircleImageView goodsInfoCiv;
    Button introductionBtn, commentsBtn;
    ViewPager goodsInfoVp;
    List<Fragment> VPList;
    GoodsInfoVPAdapter adapter;
    String goodsName, authorName;
    GoodsIntroductionFragment introductionFrag;
    GoodsCommentFragment commentsFrag;
    GoodsInfoItem goodsInfoItem;
    boolean updateCollectionFrag = false;

    @Override
    protected int initLayout() {
        return R.layout.activity_goods_info;
    }

    @Override
    protected void initView() {
        goodsInfoTv = findViewById(R.id.goods_image);
        introductionBtn = findViewById(R.id.goods_introduction);
        commentsBtn = findViewById(R.id.goods_comment);
        goodsInfoVp = findViewById(R.id.goods_info_vp);
        backIv = findViewById(R.id.image_goodsinfo_back);
        collectionIv = findViewById(R.id.btn_collection);
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

        setGoodsComments();
        updateCollection();
    }


    //收藏
    private void updateCollection() {
        Gson gson = new Gson();
        UserCollection userCollection = new UserCollection();
        userCollection.setgName(goodsName);
        userCollection.setgAuthorName(authorName);
        userCollection.setuAccount(getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", ""));
        userCollection.setAddTime(new Timestamp(System.currentTimeMillis()).toString());

        //请求参数
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");

        collectionIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateCollectionFrag) {
                    userCollection.setActive(Constant.COLLECTION_ACTIVE_FALSE);
                    collectionIv.setBackgroundColor(Color.parseColor("#bbFFFFFF"));
                    collectionIv.setImageResource(R.mipmap.collection_image_false);
                    updateCollectionFrag = false;
                    params.put("requestId", "updateCollection");
                } else {
                    userCollection.setActive(Constant.COLLECTION_ACTIVE_TRUE);
                    collectionIv.setBackgroundColor(Color.parseColor("#99CCFF"));
                    collectionIv.setImageResource(R.mipmap.collection_image_true);
                    updateCollectionFrag = true;
                    params.put("requestId", "addCollection");
                }
                params.put("collection", gson.toJson(userCollection));

                Api.config(ApiConfig.OPERATE_COLLECTION, params).postRequest(GoodsInfoActivity.this, new ApiCallback() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onSuccess(final String res) {
                        Log.e("Collection onSuccess", res);
                        MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                        Log.e("collection", myResponse.getRequestId() + myResponse.isResult() + "");
                        if (myResponse.isResult()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(GoodsInfoActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GoodsInfoActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });

            }
        });
    }

    //获取服务带你对应物品的信息
    private void setGoodsComments() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "getCommentListById");
        params.put("goodsName", goodsName);
        params.put("authorName", authorName);

        Api.config(ApiConfig.GET_GOODS_COMMENTS, params).postRequest(this, new ApiCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(final String res) {
//                Log.e("getGoodsInfo onSuccess", res);
                Gson gson = new Gson();
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
//                    List<CommentItem> commentItems = gson.fromJson(myResponse.getMsg(), new TypeToken<List<CommentItem>>() {
//                    }.getType());
//                    Log.e("", "getCommentListById:  " + commentItems);

//                    //todo: 加载数据到内存里
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("commentListStr", myResponse.getMsg());
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

                    // todo：设置civ
                    goodsInfoTv.setImageBitmap(new StringUtils().stringToBitmap(goodsInfoItem.getGoods().getgImage()));
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
        commentsFrag = GoodsCommentFragment.newInstance();

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