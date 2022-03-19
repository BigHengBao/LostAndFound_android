package com.heng.lostandfound.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity {

    Button registerBtn;
    CircleImageView userIv;
    EditText accountEd, passwordEd, passwordIsEd, rNameEd, phoneEd, addressEd, writerEd;
    RadioButton boyBtn, girlBtn;
    String image;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        accountEd = findViewById(R.id.ed_register_account);
        passwordEd = findViewById(R.id.ed_register_password);
        passwordIsEd = findViewById(R.id.ed_register_is_password);
        rNameEd = findViewById(R.id.ed_register_rname);
        phoneEd = findViewById(R.id.ed_register_phone);
        addressEd = findViewById(R.id.ed_register_address);
        writerEd = findViewById(R.id.ed_register_write);
        boyBtn = findViewById(R.id.register_boy);
        girlBtn = findViewById(R.id.register_girl);
        registerBtn = findViewById(R.id.btn_register);
        userIv = findViewById(R.id.iv_register_image);
    }

    @Override
    protected void initData() {

        //这一两行代码主要是向用户请求图片权限
        if (ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //todo:设置监听器，选择相册图片当头像
        userIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showToast("设置图片");

                openSysAlbum();
            }
        });

        //todo:注册
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountEd.getText().toString().trim();
                String password = passwordEd.getText().toString().trim();
                String isPassword = passwordIsEd.getText().toString().trim();
                String rName = rNameEd.getText().toString().trim();
                String phone = phoneEd.getText().toString().trim();
                String address = addressEd.getText().toString().trim();
                String write = writerEd.getText().toString().trim();
//                String image = imageEd.getText().toString().trim();
                Integer sex;

                if (boyBtn.isChecked()) {
                    sex = Constant.USER_MAN;
                } else {
                    sex = Constant.USER_WOMAN;
                }

                if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(password) && !StringUtils.isEmpty(isPassword)
                        && !StringUtils.isEmpty(rName) && !StringUtils.isEmpty(phone)) {
                    if (!isPassword.equals(password)) {
                        showToast("两次输入密码不同！！");
                    }
                    User user = new User();
                    user.setuAccount(account);
                    user.setuPwd(password);
                    user.setrName(rName);
                    user.setuSex(sex);
                    user.setuPhone(phone);
                    user.setuAddress(address);
                    user.setuWrite(write);
                    user.setUserImage(image);
                    user.setuLevel(Constant.USER_ORDINARY);
                    user.setActive(Constant.ACTIVE_TRUE);
                    register(user);
                } else {
                    showToast("请确保所有带 * 号的项完整");
                }
            }
        });
    }

    public void register(User user) {
        System.out.println("register:" + user);
        Gson gson = new Gson();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "register");
        params.put("user", gson.toJson(user));

        System.out.println("register:" + params.toString());
        Api.config(ApiConfig.USER_REGISTER, params).postRequest(this, new ApiCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("Register onSuccess", res);
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    Log.e("", "onSuccess: " + myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册成功!");
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册失败。用户已经存在");
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {

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
        userIv.setImageBitmap(bitmap);

        image = new StringUtils().bitmapToString(bitmap);
    }
}