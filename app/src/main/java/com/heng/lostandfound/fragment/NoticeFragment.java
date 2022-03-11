package com.heng.lostandfound.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.heng.lostandfound.R;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.Goods;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.utils.Constant;
import com.heng.lostandfound.utils.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/6 17:17
 * 发布招领
 */

public class NoticeFragment extends BaseFragment {
    EditText gNameEd, addressEd, contentEd;
    RadioButton getTypeBtn, lookingTypeBtn;
    Spinner gTypeSpinner;  //下拉框
    Button submitBtn;
    List<String> goodsTypes;
    String gType;  //物品类型
    Integer orderType;
    String userAccount;

    public NoticeFragment() {
    }

    public static NoticeFragment newInstance() {
        NoticeFragment fragment = new NoticeFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_notice;
    }

    @Override
    protected void initView() {
        gNameEd = mRootView.findViewById(R.id.ed_goods_name);
        addressEd = mRootView.findViewById(R.id.ed_goods_address);
        contentEd = mRootView.findViewById(R.id.ed_goods_content);
        getTypeBtn = mRootView.findViewById(R.id.get_order);
        lookingTypeBtn = mRootView.findViewById(R.id.looking_order);
        gTypeSpinner = mRootView.findViewById(R.id.goods_spinner);
        submitBtn = mRootView.findViewById(R.id.btn_order_submit);

        // todo: 测试数据，后期得删除
        gNameEd.setText("");
        addressEd.setText("二工大13号楼408门口");
        contentEd.setText("电话:13488889999");
    }

    @Override
    protected void initData() {

        setSpinner();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: 获取数据
                userAccount = (String) getActivity().getIntent().getSerializableExtra("userAccount");

                String gName = gNameEd.getText().toString().trim();
                String address = addressEd.getText().toString().trim();
                String content = contentEd.getText().toString().trim();


                if (!StringUtils.isEmpty(gName) && !StringUtils.isEmpty(address) && !StringUtils.isEmpty(gType)
                        && !StringUtils.isEmpty(content) && !StringUtils.isEmpty(userAccount)) {
                    Goods goods = new Goods();
                    goods.setgName(gName);
                    goods.setContent(content);
                    goods.setAddress(address);
                    goods.setType(gType);
                    goods.setuAccount(userAccount);

                    if (getTypeBtn.isChecked()) {
                        orderType = Constant.ORDER_TYPE_GET;
                        goods.setGetTime(new Timestamp(System.currentTimeMillis()).toString());
                        goods.setLoseTime("**");
                    } else {
                        orderType = Constant.ORDER_TYPE_LOOKING;
                        goods.setLoseTime(new Timestamp(System.currentTimeMillis()).toString());
                        goods.setGetTime("**");
                    }

                    submitOrder(goods);
                } else {
                    showToast("请确保所有带 * 号的项完整");
                }
            }
        });

    }

    private void setSpinner() {
        goodsTypes = new ArrayList<>();
        goodsTypes.add("杂七杂八");
        goodsTypes.add("电子产品");
        goodsTypes.add("书籍文具");
        goodsTypes.add("财务证件");
        goodsTypes.add("食品相关");
        goodsTypes.add("家居产品");
        //第二步：为下拉列表定义一个适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, goodsTypes);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        gTypeSpinner.setAdapter(adapter);


        gTypeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                gType = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void submitOrder(Goods goods) {
        System.out.println("submitOrder:" + goods);

        Gson gson = new Gson();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "submitOrder");
        params.put("goods", gson.toJson(goods));
        params.put("orderType", orderType);

        System.out.println("submitOrder param:" + params.toString());
        Api.config(ApiConfig.ADD_ORDER, params).postRequest(getActivity(), new ApiCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("submitOrder onSuccess", res);
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    Log.e("submitOrder", "onSuccess: " + myResponse);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("启事提交成功");
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("启事提交失败");
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
