package com.heng.lostandfound;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.ServiceSettings;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.heng.lostandfound.activity.BaseActivity;
import com.heng.lostandfound.adapter.MainPagerAdapter;
import com.heng.lostandfound.entity.TabEntity;
import com.heng.lostandfound.fragment.HomeFragment;
import com.heng.lostandfound.fragment.MeFragment;
import com.heng.lostandfound.fragment.NoticeFragment;

import java.util.ArrayList;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {
    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private CommonTabLayout commonTabLayout;
    private String[] mTitles = {"首页", "招领", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.application_false, R.mipmap.add_false,
            R.mipmap.me_false};
    private int[] mIconSelectIds = {
            R.mipmap.application_true, R.mipmap.add_true,
            R.mipmap.me_true};
    private ViewPager viewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }

    @Override
    protected void initData() {
        //todo:获取地理位置信息
        getCurrentLocationLatLng();

        String userAccount = (String) getIntent().getSerializableExtra("userAccount");
        Log.e("TAG", "initData: userAccount: " + userAccount);
        //todo: 加载数据到内存里
        Bundle bundle = new Bundle();
        bundle.putSerializable("userAccount", userAccount);

        mFragments.add(HomeFragment.newInstance());
        mFragments.add(NoticeFragment.newInstance());
        mFragments.add(MeFragment.newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    }

    /**
     * 初始化定位并设置定位回调监听
     */
    private void getCurrentLocationLatLng() {
        try {
                        ServiceSettings.updatePrivacyShow(this, true, true);
            ServiceSettings.updatePrivacyAgree(this,true);
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();




 /* //设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景） 设置了场景就不用配置定位模式等
    option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
    if(null != locationClient){
        locationClient.setLocationOption(option);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        locationClient.stopLocation();
        locationClient.startLocation();
    }*/
            // 同时使用网络定位和GPS定位,优先返回最高精度的定位结果,以及对应的地址描述信息
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //只会使用网络定位
            /* mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);*/
            //只使用GPS进行定位
            /*mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);*/
            // 设置为单次定位 默认为false
            /*mLocationOption.setOnceLocation(true);*/
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。默认连续定位 切最低时间间隔为1000ms
            mLocationOption.setInterval(1000 * 60);
            //设置是否返回地址信息（默认返回地址信息）
            /*mLocationOption.setNeedAddress(true);*/
            //关闭缓存机制 默认开启 ，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存,不区分单次定位还是连续定位。GPS定位结果不会被缓存。
            /*mLocationOption.setLocationCacheEnable(false);*/
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        double currentLat = amapLocation.getLatitude();//获取纬度
//                        double currentLon = amapLocation.getLongitude();//获取经度
//                        String country = amapLocation.getCountry();//国家信息
//                        String province = amapLocation.getProvince();//省信息
//                        String city = amapLocation.getCity();//城市信息
                        String district = amapLocation.getDistrict();//城区信息
//                        String street = amapLocation.getStreet();//街道信息
//                        String streetNum = amapLocation.getStreetNum();//街道门牌号信息
//                        String cityCode = amapLocation.getCityCode();//城市编码
//                        String adCode = amapLocation.getAdCode();//地区编码
//                        String aoiName = amapLocation.getAoiName();//获取当前定位点的AOI信息
//                        String buildingId = amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
//                        String floor = amapLocation.getFloor();//获取当前室内定位的楼层

                        //详细地址
                        String address = amapLocation.getAddress();

                        //todo:共享存储当前地理位置
                        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                        @SuppressLint("CommitPrefEdits")
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("address");
                        editor.remove("district");
                        editor.apply();
                        editor.putString("address", address);
                        editor.putString("district", district);
                        editor.apply();

                        /*currentLatLng = new LatLng(currentLat, currentLon);*/   //latlng形式的
                        Log.i("currentLocation ->当前位置：", district);
                        amapLocation.getAccuracy();//获取精度信息
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();//销毁定位客户端。
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLocationClient != null) {
            mLocationClient.startLocation(); // 启动定位
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位
        }
    }

}