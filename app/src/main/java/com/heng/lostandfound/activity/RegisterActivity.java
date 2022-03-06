package com.heng.lostandfound.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

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

public class RegisterActivity extends BaseActivity {

    Button registerBtn;
    EditText accountEd, passwordEd, passwordIsEd, rNameEd, phoneEd, addressEd, writerEd, imageEd;
    RadioButton boyBtn, girlBtn;

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
        imageEd = findViewById(R.id.ed_register_image);
        boyBtn = findViewById(R.id.register_boy);
        girlBtn = findViewById(R.id.register_girl);
        registerBtn = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
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
                String image = imageEd.getText().toString().trim();
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
                    user.setUid(account);
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
        Api.config(ApiConfig.REGISTER, params).postRequest(this, new ApiCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("onSuccess", res);
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    Log.e("", "onSuccess: " + myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册成功!");
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
}