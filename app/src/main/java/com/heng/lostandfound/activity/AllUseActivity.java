package com.heng.lostandfound.activity;

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
 * @date : 2022/3/12 19:53
 * 通用界面
 */
public class AllUseActivity extends BaseActivity {
    ListView allUseLv;
    ImageView backIv;
    List<String> mLvDatas = new ArrayList<>();


    @Override
    protected int initLayout() {
        return R.layout.activity_all_use;
    }

    @Override
    protected void initView() {
        allUseLv = findViewById(R.id.lv_alluse);
        backIv = findViewById(R.id.image_alluse_back);
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
        allUseLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showToast("帮助中心");
//                        startActivity(new Intent(AllUseActivity.this, **.class));
                        break;
                    case 1:
                        showToast("关于");
                        break;
                    case 2:
                        showToast("更换背景");
                        break;
                }
            }
        });
    }

    private void addDataToList() {
        mLvDatas.add("帮助中心");
        mLvDatas.add("关于");
        mLvDatas.add("更换背景");

        int[] images = {R.mipmap.helper_image, R.mipmap.about_image, R.mipmap.exchangebg};

        MeLvAdapter meLvAdapter = new MeLvAdapter(this, mLvDatas, images);
        allUseLv.setAdapter(meLvAdapter);
    }
}