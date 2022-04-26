package com.heng.lostandfound.activity;

import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.heng.lostandfound.R;
/**
 * @author : HengZhang
 * @date : 2022/3/12 19:32
 * 高德地图定位
 */
public class MapActivity extends BaseActivity {

    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private TextView position;

    @Override
    protected int initLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView() {
        position = findViewById(R.id.position_text);
    }

    @Override
    protected void initData(){
            getCurrentLocationLatLng();
    }

    ;

    /**
     * 初始化定位并设置定位回调监听
     */
    private void getCurrentLocationLatLng() {
        try {
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
            mLocationOption.setInterval(1000*60);
            //设置是否返回地址信息（默认返回地址信息）
            /*mLocationOption.setNeedAddress(true);*/
            //关闭缓存机制 默认开启 ，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存,不区分单次定位还是连续定位。GPS定位结果不会被缓存。
            /*mLocationOption.setLocationCacheEnable(false);*/
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        }catch (Exception e){
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
                        position.setText(district);
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