package com.heng.lostandfound.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heng.lostandfound.R;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.utils.StringUtils;

import java.util.HashMap;

/**
 * 登陆界面
 */

public class LoginActivity extends BaseActivity {
    EditText accountEd, pwdEd;
    Button loginBtn, toRegisterBtn;

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
    }

    @Override
    protected void initData() {
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String account = accountEd.getText().toString().trim();
                String pwd = pwdEd.getText().toString().trim();
                login(account, pwd);
            }
        });

        toRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(RegisterActivity.class);
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


        Api.config(ApiConfig.LOGIN, params).postRequest(this, new ApiCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    Log.e("", "onSuccess: " + myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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