package com.heng.lostandfound.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heng.lostandfound.R;
import com.heng.lostandfound.activity.AllUseActivity;
import com.heng.lostandfound.activity.CodeActivity;
import com.heng.lostandfound.activity.CollectionActivity;
import com.heng.lostandfound.activity.PluginActivity;
import com.heng.lostandfound.activity.SettingActivity;
import com.heng.lostandfound.activity.UserInfoActivity;
import com.heng.lostandfound.activity.UserNoticeActivity;
import com.heng.lostandfound.adapter.MeLvAdapter;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.entity.User;
import com.heng.lostandfound.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : HengZhang
 * @date : 2022/3/6 17:17
 * 我的界面
 */

public class MeFragment extends BaseFragment {
    User user;
    CircleImageView iconIv;
    ListView meLv;
    TextView rNameTv, userWriteTv;
    List<String> mLvDatas = new ArrayList<>();
    LinearLayout collection, userInfo, notice;

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
        iconIv = mRootView.findViewById(R.id.meFrag_iv);
        meLv = mRootView.findViewById(R.id.lv_me);
        collection = mRootView.findViewById(R.id.collection);
        userInfo = mRootView.findViewById(R.id.user_Info);
        notice = mRootView.findViewById(R.id.notice);
        rNameTv = mRootView.findViewById(R.id.tv_user_rname);
        userWriteTv = mRootView.findViewById(R.id.tv_user_write);
    }

    @Override
    protected void initData() {

        setSimpleInfo();

        // todo：listview装配数据
        addDataToList();
        //todo: listview设置触发事件
        setLvListener();
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(CollectionActivity.class);
            }
        });

        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(UserInfoActivity.class);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(UserNoticeActivity.class);
            }
        });
    }

    // todo：设置简易信息
    private void setSimpleInfo() {
        Gson gson = new Gson();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "getUserInfo");
        params.put("userAccount", getContext().getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", ""));

        Api.config(ApiConfig.GET_USER_INFO, params).postRequest(getContext(), new ApiCallback() {


            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(final String res) {
                Log.e("getUserInfo onSuccess", res);
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                Log.e("getUserInfo", myResponse.getRequestId() + myResponse.isResult() + "");
                if (myResponse.isResult()) {
                    user = gson.fromJson(myResponse.getMsg(), User.class);
                    Log.e("TAG", "MeFragment onSuccess: " + user);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void setLvListener() {
        meLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        showToast("二维码");
                        navigateTo(CodeActivity.class);
                        break;
                    case 1:
//                        showToast("通用");
                        navigateTo(AllUseActivity.class);
                        break;
                    case 2:
//                        showToast("插件");
                        navigateTo(PluginActivity.class);
                        break;

                    case 3:
//                        showToast("设置");
                        navigateTo(SettingActivity.class);
                        break;
                }
            }
        });
        try {
            Thread.sleep(600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rNameTv.setText(user.getrName());
        userWriteTv.setText(user.getuWrite());
        iconIv.setImageBitmap(new StringUtils().stringToBitmap(user.getUserImage()));

    }


    private void addDataToList() {
        mLvDatas.add("二维码");
        mLvDatas.add("通用");
        mLvDatas.add("插件");
        mLvDatas.add("设置");

        int[] images = {R.mipmap.me_code_image, R.mipmap.me_alluse_image,
                R.mipmap.me_other_image, R.mipmap.me_setting_image};

        MeLvAdapter meLvAdapter = new MeLvAdapter(getContext(), mLvDatas, images);
        meLv.setAdapter(meLvAdapter);
    }
}
