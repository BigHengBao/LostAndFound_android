package com.heng.lostandfound.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heng.lostandfound.MainActivity;
import com.heng.lostandfound.R;
import com.heng.lostandfound.activity.MapActivity;
import com.heng.lostandfound.activity.PluginActivity;
import com.heng.lostandfound.activity.WeatherActivity;
import com.heng.lostandfound.adapter.HomeGVAdapter;
import com.heng.lostandfound.adapter.HomePicVPAdapter;
import com.heng.lostandfound.adapter.HomeRecyclerAdapter;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.api.ApiConfig;
import com.heng.lostandfound.entity.HomeGVItem;
import com.heng.lostandfound.entity.MyResponse;
import com.heng.lostandfound.entity.RecyclerItem;
import com.heng.lostandfound.entity.WeatherInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/6 17:12
 * title： home界面
 */

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    ViewPager homeVp;
    GridView defaultGv;
    LinearLayout pointLayout;
    ImageView weatherIv;
    Button testMapBtn;


    private RecyclerView mRecyclerView;
    private HomeRecyclerAdapter mHomeAdapter;
    private List<RecyclerItem> mList = new ArrayList<>();

    List<HomeGVItem> mDatas = new ArrayList<>();

    private HomeGVAdapter adapter;

    //todo: 声明图片数组
    int[] imgIds = {R.mipmap.pic_lostandfound, R.mipmap.pic_guanggao, R.mipmap.pic_lostandfound2};

    //todo: 声明ViewPager的数据源
    List<ImageView> ivList;

    //todo: 声明管理指示器小圆点的集合
    List<ImageView> pointList;
    private HomePicVPAdapter vPAdapterDefault;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        homeVp = mRootView.findViewById(R.id.home_img_vp);
        pointLayout = mRootView.findViewById(R.id.home_point);
        defaultGv = mRootView.findViewById(R.id.home_gv);
        mRecyclerView = mRootView.findViewById(R.id.home_recycler_view);
        weatherIv = mRootView.findViewById(R.id.iv_home_weather);
//        testMapBtn = mRootView.findViewById(R.id.test_map);
//        civ = mRootView.findViewById(R.id.iv_home_image);
    }

    @Override
    protected void initData() {
//        testMapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MapActivity.class);
//                startActivity(intent);
//            }
//        });
        //todo: 初始化图片
        initPager();

        initGV();

        //todo: 设置小圆点的监听
        setVPListener();
        //todo: 延迟5秒钟发送一条消息，通知可以切换viewpager的图片了
        handler.sendEmptyMessageDelayed(1, 5000);

        setGVListener();

        //装配所有的启事信息
        getAllOrder();

        initRecycler();

        //todo:设置天气信息
        setWeatherInfo();
    }

    // 设置天气信息
    private void setWeatherInfo() {

        String cityName = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getString("district", "");
        Api.config(cityName).postRequest(getActivity(), new ApiCallback() {
            @Override
            public void onSuccess(String res) {
//                Log.e("TAG", "setWeatherInfo onSuccess: " + res);
                WeatherInfo weatherInfo = new Gson().fromJson(res, WeatherInfo.class);
                Log.e("TAG", "setWeatherInfo onSuccess: " + weatherInfo.getWea());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weatherInfo.getWea().equals("多云")) {
                            weatherIv.setImageResource(R.mipmap.weather_cloudy_img);
                        } else if (weatherInfo.getWea().equals("晴")) {
                            weatherIv.setImageResource(R.mipmap.weather_sun_img);
                        } else if (weatherInfo.getWea().contains("雨")) {
                            weatherIv.setImageResource(R.mipmap.weather_rain_img);
                        } else if (weatherInfo.getWea().contains("雪")) {
                            weatherIv.setImageResource(R.mipmap.weather_snow_img);
                        } else {
                            weatherIv.setImageResource(R.mipmap.weather_sun_img);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "setWeatherInfo onFailure: 失败！");
            }
        });

        weatherIv.setImageBitmap(null);
        weatherIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WeatherActivity.class));
            }
        });
    }

    //装配所有的启事信息
    private void getAllOrder() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "getAllOrder");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Api.config(ApiConfig.GET_ALL_ORDER, params).postRequest(getActivity(), new ApiCallback() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onSuccess(final String res) {
                        Log.e("HomeFragment getAllOrder onSuccess", res);
                        Gson gson = new Gson();
                        MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                        if (myResponse.isResult()) {
                            Log.e("", "onSuccess: " + myResponse);

                            /**
                             * 解析赋值
                             * 把Json字符串 解析成List<RecyclerItem>
                             * 相当于走了两部，json->list,list<object>-->list<RecyclerItem>
                             */

                            List<RecyclerItem> list = gson.fromJson(myResponse.getMsg(), new TypeToken<List<RecyclerItem>>() {
                            }.getType());
                            mList.clear();
                            for (RecyclerItem recyclerItem : list) {
                                if (!mList.contains(recyclerItem)) {
                                    mList.add(recyclerItem);
                                }
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                            Toast.makeText(getActivity(), "数据更新成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "数据更新失败", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });

            }
        }).start();

    }

    //todo: 完成定时装置，实现自动滑动的效果
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                // todo: 获取当前ViewPager显示的页面
                int currentItem = homeVp.getCurrentItem();

                // todo: 判断是否为最后一张，如果是最后一张回到第一张，否则显示最后一张
                if (currentItem == ivList.size() - 1) {
                    homeVp.setCurrentItem(0);
                } else {
                    currentItem++;
                    homeVp.setCurrentItem(currentItem);
                }
                //todo: 形成循环发送--接受消息的效果，在接受消息的同时，也要进行消息发送
                handler.sendEmptyMessageDelayed(1, 3000);
            }
        }
    };

    private void setVPListener() {

        homeVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pointList.size(); i++) {
                    pointList.get(i).setImageResource(R.mipmap.point_normal);
                }
                pointList.get(position).setImageResource(R.mipmap.point_focus);
            }
        });
    }


    private void initPager() {
        ivList = new ArrayList<>();
        pointList = new ArrayList<>();

        for (int i = 0; i < imgIds.length; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(imgIds[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            // todo: 设置图片view的宽高
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);

            // todo: 将图片view加载到集合当中
            ivList.add(iv);

            //todo:  创建图片对应的指示器小圆点
            ImageView piv = new ImageView(getContext());
            piv.setImageResource(R.mipmap.point_normal);
            LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            plp.setMargins(10, 0, 10, 0);
            piv.setLayoutParams(plp);

//            //todo:  将小圆点添加到布局当中
//            pointLayout.addView(piv);

            //todo: 为了便于操作，将小圆点添加到统一管理的集合中
            pointList.add(piv);
            //todo:  将小圆点添加到布局当中
            pointLayout.addView(piv);
        }

        Log.e(TAG, "initPager: pointList  " + pointList.size());
        //todo: 默认第一个小圆点是获取焦点的状态
        pointList.get(0).setImageResource(R.mipmap.point_focus);
        vPAdapterDefault = new HomePicVPAdapter(getContext(), ivList);
        homeVp.setAdapter(vPAdapterDefault);
    }


    /**
     * 设置GridView的监听事件函数
     */
    private void setGVListener() {

        defaultGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                DefaultGVItem defaultGVItem = mDatas.get(position);
                Intent intent;
                switch (position) {
                    case 0:
//                        intent = new Intent(getContext(), InCodeActivity.class);
//                        startActivity(intent);
                        initGV();
                        //装配所有的启事信息
                        getAllOrder();
                        initRecycler();
                        showToast("更新成功");
                        break;
                    case 1:
//                        intent = new Intent(getContext(), CodePartyActivity.class);
//                        startActivity(intent);
//                        showToast("这是第" + position + "个");
                        break;
                    case 2:
//                        intent = new Intent(getContext(), CodePartyActivity.class);
//                        startActivity(intent);
//                        showToast("这是第" + position + "个");
                        break;

                }
            }
        });
    }

    private void initGV() {
        if (mDatas.size() > 7) {
            return;
        }
        HomeGVItem allItems = new HomeGVItem("全部物品", null, "", "");
        mDatas.add(allItems);

        //获取网络数据
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("front", "android");
        params.put("requestId", "getGoodsTypes");

        Api.config(ApiConfig.GET_ALL_GOODS_TYPE, params).postRequest(getActivity(), new ApiCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(final String res) {
                Log.e("HomeFragment getGoodsTypes onSuccess", res);
                Gson gson = new Gson();
                MyResponse myResponse = gson.fromJson(res, MyResponse.class);
                if (myResponse.isResult()) {
                    Log.e("", "onSuccess: " + myResponse);

                    /**
                     * 解析赋值
                     * 把Json字符串 解析成List<RecyclerItem>
                     * 相当于走了两部，json->list,list<object>-->list<RecyclerItem>
                     */

                    List<HomeGVItem> goodsTypeList = gson.fromJson(myResponse.getMsg(),
                            new TypeToken<List<HomeGVItem>>() {
                            }.getType());

                    mDatas.addAll(goodsTypeList);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getActivity(), "物品类别数据更新成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "物品类别数据更新失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });


//        HomeGVItem electronic = new HomeGVItem("电子产品", null, "", "");
//        HomeGVItem books = new HomeGVItem("书籍文具", null, "", "");
//        HomeGVItem financialDocs = new HomeGVItem("财务证件", null, "", "");
//        HomeGVItem foods = new HomeGVItem("食品相关", null, "", "");
//        HomeGVItem house = new HomeGVItem("家居用品", null, "", "");
//        HomeGVItem others = new HomeGVItem("杂七杂八", null, "", "");
        HomeGVItem waiting = new HomeGVItem("待续", null, "", "");


//        mDatas.add(electronic);
//        mDatas.add(books);
//        mDatas.add(financialDocs);
//        mDatas.add(foods);
//        mDatas.add(house);
//        mDatas.add(others);

        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mDatas.add(waiting);

        //todo: 创建适配器对象
        adapter = new HomeGVAdapter(getContext(), mDatas);
        //todo: 设置适配器
        defaultGv.setAdapter(adapter);
    }

    // 加载显示界面
    private void initRecycler() {
        // 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

//        // todo:临时测试文件，后期用后台数据
//        RecyclerItem item1 = new RecyclerItem("笔记本", Constant.ORDER_TYPE_GET,
//                "划水", "书籍文具", new Timestamp(System.currentTimeMillis()).toString(), null);
//        RecyclerItem item2 = new RecyclerItem("水杯", Constant.ORDER_TYPE_LOOKING,
//                "摸鱼", "家居用品", new Timestamp(System.currentTimeMillis()).toString(), null);
//        RecyclerItem item3 = new RecyclerItem("充电器", Constant.ORDER_TYPE_LOOKING,
//                "起飞", "电子产品", new Timestamp(System.currentTimeMillis()).toString(), null);
//        RecyclerItem item4 = new RecyclerItem("快递", Constant.ORDER_TYPE_GET,
//                "王菲非", "杂七杂八", new Timestamp(System.currentTimeMillis()).toString(), null);
//        mList.add(item1);
//        mList.add(item2);
//        mList.add(item3);
//        mList.add(item4);

        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置 item 增加和删除时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeRecyclerAdapter(mList, getContext());
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setWeatherInfo();
    }
}
