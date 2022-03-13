package com.heng.lostandfound.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.heng.lostandfound.R;
import com.heng.lostandfound.entity.Goods;
import com.heng.lostandfound.entity.GoodsInfoItem;
import com.heng.lostandfound.entity.Order;
import com.heng.lostandfound.utils.Constant;

import java.io.Serializable;

/**
 * @author : HengZhang
 * @date : 2022/3/13 11:22
 */

public class GoodsIntroductionFragment extends BaseFragment {
    GoodsInfoItem goodsInfoItem = null;

    TextView goodsNameTv, authorNameTv, orderTypeTv, goodsTypeTv, goodsTimeTv,
            addressTv, contentTv;

    public GoodsIntroductionFragment() {
    }

    public static GoodsIntroductionFragment newInstance() {
        GoodsIntroductionFragment fragment = new GoodsIntroductionFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_goods_introduction;
    }

    @Override
    protected void initView() {
        goodsNameTv = mRootView.findViewById(R.id.tv_goodsInfo_goods_name);
        authorNameTv = mRootView.findViewById(R.id.tv_goodsInfo_author);
        orderTypeTv = mRootView.findViewById(R.id.tv_goodsInfo_order_type);
        goodsTypeTv = mRootView.findViewById(R.id.tv_goodsInfo_goods_type);
        goodsTimeTv = mRootView.findViewById(R.id.tv_goodsInfo_goodsTime);
        addressTv = mRootView.findViewById(R.id.tv_goodsInfo_address);
        contentTv = mRootView.findViewById(R.id.tv_goodsInfo_content);
    }

    @Override
    protected void initData() {
        setGoodsIntroduction();
    }

    @Override
    public void onResume() {
        super.onResume();
//        setGoodsIntroduction();
    }

    private void setGoodsInfos() {
        if (goodsInfoItem != null) {
            Goods goods = goodsInfoItem.getGoods();
            Order order = goodsInfoItem.getOrder();

            goodsNameTv.setText(goods.getgName());
            authorNameTv.setText(goodsInfoItem.getAuthorName());
            addressTv.setText(goods.getAddress());
            contentTv.setText(goods.getContent());
            goodsTypeTv.setText(goods.getType());
            if (order.getType() == Constant.ORDER_TYPE_GET) {
                orderTypeTv.setText("招领启事");
                goodsTimeTv.setText(goods.getGetTime());
            } else {
                orderTypeTv.setText("寻物启事");
                goodsTimeTv.setText(goods.getLoseTime());
            }
        }

    }

    public void setGoodsIntroduction() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle goodsInfoBundle = getActivity().getIntent().getExtras();
        goodsInfoItem = (GoodsInfoItem) goodsInfoBundle.getSerializable("goodsInfoItem");
        setGoodsInfos();

        Log.e("TAG", "GoodsIntroduct: " + goodsInfoItem);

    }
}
