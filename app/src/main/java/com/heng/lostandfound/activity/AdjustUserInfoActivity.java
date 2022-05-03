package com.heng.lostandfound.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.heng.lostandfound.R;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.entity.User;
import com.heng.lostandfound.utils.Constant;
import com.heng.lostandfound.utils.StringUtils;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : HengZhang
 * @date : 2022/4/27 19:53
 * 修改用户个人信息
 */

public class AdjustUserInfoActivity extends BaseActivity {
    ImageView backIv;
    TextView uAccountTv;
    EditText rNameEd, uPhoneEd, addressEd, uWriteEd;
    RadioButton manBtn, womanBtn;
    CircleImageView adjustUserInfoIv;
    Button adjustUserInfoBtn;
    AlertDialog dialog;  //对话框
    User user;
    String image;

    @Override
    protected int initLayout() {
        return R.layout.activity_adjust_user_info;
    }

    @Override
    protected void initView() {
        backIv = findViewById(R.id.image_adjust_userinfo_back);
        uAccountTv = findViewById(R.id.tv_adjust_userinfo_account);
        rNameEd = findViewById(R.id.tv_adjust_userinfo_rname);
        uPhoneEd = findViewById(R.id.tv_adjust_userinfo_phone);
        addressEd = findViewById(R.id.tv_adjust_userinfo_address);
        uWriteEd = findViewById(R.id.tv_adjust_userinfo_write);
        manBtn = findViewById(R.id.rb_adjust_userinfo_man);
        womanBtn = findViewById(R.id.rb_adjust_userinfo_woman);
        adjustUserInfoIv = findViewById(R.id.adjust_userinfo_iv);
        adjustUserInfoBtn = findViewById(R.id.adjust_user_info_btn);
    }

    @Override
    protected void initData() throws Exception {
        setButtons();
        getUserInfo();
        setUserInfo();
        adjustUserInfoImage();
    }

    //设置所有按钮的触发事件
    private void setButtons() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adjustUserInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setuAccount(uAccountTv.getText().toString().trim());
                user.setrName(rNameEd.getText().toString());
                user.setuPhone(uPhoneEd.getText().toString());
                user.setuAddress(addressEd.getText().toString());
                user.setuWrite(uWriteEd.getText().toString());
                user.setUserImage(image);
                if (manBtn.isChecked()) {
                    user.setuSex(0);
                } else {
                    user.setuSex(1);
                }

                dialog = new AlertDialog.Builder(AdjustUserInfoActivity.this)
                        .setIcon(R.mipmap.dialog_img)//设置标题的图片
                        .setTitle("\t 提醒")//设置对话框的标题
                        .setMessage("\n 是否确认此修改？")//设置对话框的内容
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
                                adjustUserInfo(user);
                                Log.e("TAG", "adjustUserInfo: " + user);
//                                    Toast.makeText(MainActivity.this, "点击了确定的按钮", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });
    }

    public void adjustUserInfo(User user) {
        Log.e("TAG", "adjustUserInfo: " + user);
        Gson gson = new Gson();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "adjustUser");
        params.put("user", gson.toJson(user));

        Log.d("TAG", "adjustUserInfo: " + params.toString());
        Api.config(ApiConfig.ADJUST_USER_INFO, params).postRequest(this, new ApiCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("adjustUser onSuccess", res);
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
//                    Log.e("", "onSuccess: " + myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("个人信息修改成功!");
                            startActivity(new Intent(AdjustUserInfoActivity.this, UserInfoActivity.class));
                            finish();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("个人信息修改失败。");
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }


    private void getUserInfo() {
        Gson gson = new Gson();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "getUserInfo");
        params.put("userAccount", getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", ""));

        Api.config(ApiConfig.GET_USER_INFO, params).postRequest(AdjustUserInfoActivity.this, new ApiCallback() {


            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(final String res) {
                Log.e("adjust getUserInfo onSuccess", res);
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                Log.e("adjust getUserInfo", myResponse.getRequestId() + myResponse.isResult() + "");
                if (myResponse.isResult()) {

                    user = gson.fromJson(myResponse.getMsg(), User.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            Toast.makeText(UserInfoActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AdjustUserInfoActivity.this, "adjust 获取用户信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void setUserInfo() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        uAccountTv.setText(user.getuAccount());
        rNameEd.setText(user.getrName());
        uPhoneEd.setText(user.getuPhone());
        addressEd.setText(user.getuAddress());
        uWriteEd.setText(user.getuWrite());
        adjustUserInfoIv.setImageBitmap(new StringUtils().stringToBitmap(user.getUserImage()));

        if (user.getuSex() == Constant.USER_MAN) {
            manBtn.setChecked(true);
            womanBtn.setChecked(false);
        } else {
            manBtn.setChecked(false);
            womanBtn.setChecked(true);
        }
    }

    private void adjustUserInfoImage() {

        //这一两行代码主要是向用户请求图片权限
        if (ActivityCompat.checkSelfPermission(AdjustUserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AdjustUserInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //todo:设置监听器，选择相册图片当头像
        adjustUserInfoIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                showToast("设置图片");

                openSysAlbum();
            }
        });
    }


    public static int ALBUM_RESULT_CODE = 0x999;

    /**
     * 打开系统相册
     * 定义Intent跳转到特定图库的Uri下挑选，然后将挑选结果返回给Activity
     */
    private void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, ALBUM_RESULT_CODE);
    }

    //重载onActivityResult方法，获取相应数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleImageOnKitKat(data);
    }

    //这部分的代码目前没有理解，只知道作用是根据条件的不同去获取相册中图片的url
    //这一部分是从其他博客中查询的
    @TargetApi(value = 19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        // 根据图片路径显示图片
        displayImage(imagePath);
        System.out.println(imagePath);
    }

    /**
     * 获取图片的路径
     */
    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {

            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 展示图片
     */
    private void displayImage(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        adjustUserInfoIv.setImageBitmap(bitmap);

        image = new StringUtils().bitmapToString(bitmap);
    }
}