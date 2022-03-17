package com.heng.lostandfound.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.heng.lostandfound.R;

public class CollectionActivity extends BaseActivity {
    ImageView backIv;

    @Override
    protected int initLayout() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView() {
        backIv = findViewById(R.id.image_collection_back);

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