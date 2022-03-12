package com.heng.lostandfound.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.heng.lostandfound.R;
import com.heng.lostandfound.adapter.LvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/12 20:26
 * 设置界面
 */
public class SettingActivity extends BaseActivity {
    ListView settingLv;
    ImageView backIv;
    List<String> mLvDatas = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        settingLv = findViewById(R.id.lv_setting);
        backIv = findViewById(R.id.image_setting_back);
    }

    @Override
    protected void initData() {
        // todo：listview装配数据
        addDataToList();
        //todo: listview设置触发事件
        setLvListener();

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setLvListener() {
        settingLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showToast("退出登陆");
//                        startActivity(new Intent(AllUseActivity.this, **.class));
                        break;
                    case 1:
                        showToast("隐私权限");
                        break;
                }
            }
        });
    }

    private void addDataToList() {
        mLvDatas.add("退出登陆");
        mLvDatas.add("隐私权限");

        int[] images = {R.mipmap.quit_image, R.mipmap.permissions_image};

        LvAdapter lvAdapter = new LvAdapter(this, mLvDatas, images);
        settingLv.setAdapter(lvAdapter);
    }
}