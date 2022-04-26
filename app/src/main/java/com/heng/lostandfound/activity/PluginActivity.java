package com.heng.lostandfound.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.heng.lostandfound.R;
import com.heng.lostandfound.adapter.MeLvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/12 21:02
 * 插件界面
 */
public class PluginActivity extends BaseActivity {
    ListView pluginLv;
    ImageView backIv;
    List<String> mLvDatas = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_plugin;
    }

    @Override
    protected void initView() {
        backIv = findViewById(R.id.image_plugin_back);
        pluginLv = findViewById(R.id.lv_plugin);

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
        pluginLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showToast("天气");
                        startActivity(new Intent(PluginActivity.this, WeatherActivity.class));
                        break;
                    case 1:
                        showToast("待续");
//                        startActivity(new Intent(AllUseActivity.this, **.class));
                        break;
                }
            }
        });
    }

    private void addDataToList() {
        mLvDatas.add("天气");
        mLvDatas.add("待续");

        int[] images = {R.mipmap.weather_image, R.mipmap.permissions_image};

        MeLvAdapter meLvAdapter = new MeLvAdapter(this, mLvDatas, images);
        pluginLv.setAdapter(meLvAdapter);
    }
}