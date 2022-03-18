package com.heng.lostandfound.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.heng.lostandfound.R;
import com.heng.lostandfound.activity.AllUseActivity;
import com.heng.lostandfound.activity.CodeActivity;
import com.heng.lostandfound.activity.CollectionActivity;
import com.heng.lostandfound.activity.PluginActivity;
import com.heng.lostandfound.activity.SettingActivity;
import com.heng.lostandfound.activity.UserInfoActivity;
import com.heng.lostandfound.activity.UserNoticeActivity;
import com.heng.lostandfound.adapter.MeLvAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : HengZhang
 * @date : 2022/3/6 17:17
 * 我的界面
 */

public class MeFragment extends BaseFragment {
    CircleImageView iconIv;
    ListView meLv;
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
        meLv = mRootView.findViewById(R.id.lv_me);
        collection = mRootView.findViewById(R.id.collection);
        userInfo = mRootView.findViewById(R.id.user_Info);
        notice = mRootView.findViewById(R.id.notice);
    }

    @Override
    protected void initData() {

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
