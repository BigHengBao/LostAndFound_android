package com.heng.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class UserInfoActivity extends BaseActivity {
    ImageView backIv;
    TextView uAccountTv, rNameTv, uPhoneTv, addressTv, uWriteTv;
    RadioButton sexBtn;
    CircleImageView userInfoIv;
    User user;

    @Override
    protected int initLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        backIv = findViewById(R.id.image_userinfo_back);
        uAccountTv = findViewById(R.id.tv_userinfo_account);
        rNameTv = findViewById(R.id.tv_userinfo_rname);
        uPhoneTv = findViewById(R.id.tv_userinfo_phone);
        addressTv = findViewById(R.id.tv_userinfo_address);
        uWriteTv = findViewById(R.id.tv_userinfo_write);
        sexBtn = findViewById(R.id.tv_userinfo_sex);
        userInfoIv = findViewById(R.id.userinfo_iv);
    }

    @Override
    protected void initData() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getUserInfo();
        setUserInfo();
    }

    private void getUserInfo() {
        Gson gson = new Gson();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "getUserInfo");
        params.put("userAccount", getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", ""));

        Api.config(ApiConfig.GET_USER_INFO, params).postRequest(UserInfoActivity.this, new ApiCallback() {


            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(final String res) {
                Log.e("getUserInfo onSuccess", res);
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                Log.e("getUserInfo", myResponse.getRequestId() + myResponse.isResult() + "");
                if (myResponse.isResult()) {

                    user = gson.fromJson(myResponse.getMsg(), User.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(UserInfoActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UserInfoActivity.this, "失败", Toast.LENGTH_SHORT).show();
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
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        uAccountTv.setText(user.getuAccount());
        rNameTv.setText(user.getrName());
        uPhoneTv.setText(user.getuPhone());
        addressTv.setText(user.getuAddress());
        uWriteTv.setText(user.getuWrite());
        userInfoIv.setImageBitmap(new StringUtils().stringToBitmap(user.getUserImage()));
        sexBtn.setChecked(true);

        if (user.getuSex() == Constant.USER_MAN) {
          sexBtn.setText("男");
        } else {
            sexBtn.setText("女");
        }
    }
}
