package com.heng.lostandfound.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heng.lostandfound.MainActivity;
import com.heng.lostandfound.R;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.utils.StringUtils;

import java.util.HashMap;

/**
 * @author : HengZhang
 * @date : 2022/3/6 14:49
 * 登陆界面
 */

public class LoginActivity extends BaseActivity {
    EditText accountEd, pwdEd;
    Button loginBtn, toRegisterBtn;
    ProgressDialog dialog ;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        accountEd = findViewById(R.id.ed_login_account);
        pwdEd = findViewById(R.id.ed_login_pwd);
        loginBtn = findViewById(R.id.btn_login);
        toRegisterBtn = findViewById(R.id.btn_to_register);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载中");


        //todo: 方便测试，后期删除
        accountEd.setText("20191110000");
        pwdEd.setText("123");
    }

    @Override
    protected void initData() {


        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String account = accountEd.getText().toString().trim();
                String pwd = pwdEd.getText().toString().trim();
                login(account, pwd);
                dialog.show();
            }
        });

        toRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(String account, String pwd) {
        if (StringUtils.isEmpty(account)) {
            Toast.makeText(getApplicationContext(), "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "login");
        params.put("account", account);
        params.put("password", pwd);


        Api.config(ApiConfig.USER_LOGIN, params).postRequest(this, new ApiCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("LoginActivity onSuccess", res);
                Gson gson = new Gson();
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    Log.e("", "onSuccess: " + myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //共享存储用户名
                            SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                            @SuppressLint("CommitPrefEdits")
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            editor.putString("username", account);
                            editor.apply();



                            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //todo: 添加用户数据
                            intent.putExtra("userAccount", account);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}