package com.heng.lostandfound.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.heng.lostandfound.R;
import com.heng.lostandfound.adapter.MeLvAdapter;

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
    AlertDialog dialog;
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

                        dialog = new AlertDialog.Builder(SettingActivity.this)
                                .setIcon(R.mipmap.dialog_img)//设置标题的图片
                                .setTitle("\t 提醒")//设置对话框的标题
                                .setMessage("\n 是否退出登陆？")//设置对话框的内容
                                //设置对话框的按钮
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                   showToast();
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                                        finish();
//                                    Toast.makeText(MainActivity.this, "点击了确定的按钮", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }).create();
                        dialog.show();

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

        MeLvAdapter meLvAdapter = new MeLvAdapter(this, mLvDatas, images);
        settingLv.setAdapter(meLvAdapter);
    }
}