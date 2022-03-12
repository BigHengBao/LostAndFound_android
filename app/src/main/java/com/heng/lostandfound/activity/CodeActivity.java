package com.heng.lostandfound.activity;

import android.view.View;
import android.widget.ImageView;

import com.heng.lostandfound.R;

/**
 * @author : HengZhang
 * @date : 2022/3/12 19:32
 * 二维码界面
 */
public class CodeActivity extends BaseActivity {
    ImageView backIv;

    @Override
    protected int initLayout() {
        return R.layout.activity_code;
    }

    @Override
    protected void initView() {
        backIv = findViewById(R.id.image_code_back);
    }

    @Override
    protected void initData() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}