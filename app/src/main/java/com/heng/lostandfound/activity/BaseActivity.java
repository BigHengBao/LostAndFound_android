package com.heng.lostandfound.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lombok.SneakyThrows;

/**
 * @author : HengZhang
 * @date : 2022/3/6 10:42
 * activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @SneakyThrows
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();// 隐藏ActionBar
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initLayout());
        initView();
        try {
            initData();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData() throws Exception;

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

}
