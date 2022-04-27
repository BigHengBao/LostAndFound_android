package com.heng.lostandfound.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heng.lostandfound.R;
import com.heng.lostandfound.api.Api;
import com.heng.lostandfound.api.ApiCallback;
import com.heng.lostandfound.entity.WeatherInfo;

public class WeatherActivity extends BaseActivity {
    ImageView backIv;
    TextView cityText, dateText, weekText, tempText, tem1Text, weatherText, airLevelText, airTipsText,
            pm2Text, airText, kouZhaoText, outText, sportsText, windowText, alarmTitleText, alarmInfoText;
    ImageView weatherIv;

    WeatherInfo weatherInfo;

    @Override
    protected int initLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void initView() {
        backIv = findViewById(R.id.image_weather_back);
        weatherIv = findViewById(R.id.iv_weather_img);
        cityText = findViewById(R.id.tv_weather_city);
//        countryText = findViewById(R.id.tv_weather_country);
        dateText = findViewById(R.id.tv_weather_date);
        weekText = findViewById(R.id.tv_weather_week);
        tempText = findViewById(R.id.tv_weather_tem);
        tem1Text = findViewById(R.id.tv_weather_tem1);
//        tem2Text = findViewById(R.id.tv_weather_tem2);
        weatherText = findViewById(R.id.tv_weather_wea);
        airLevelText = findViewById(R.id.tv_weather_airlevel);
        airTipsText = findViewById(R.id.tv_weather_airtips);
        pm2Text = findViewById(R.id.tv_weather_pm2);
        airText = findViewById(R.id.tv_weather_air);
        kouZhaoText = findViewById(R.id.tv_weather_kouzhao);
        outText = findViewById(R.id.tv_weather_out);
        sportsText = findViewById(R.id.tv_weather_sports);
        windowText = findViewById(R.id.tv_weather_window);
        alarmTitleText = findViewById(R.id.tv_weather_alarm_title);
        alarmInfoText = findViewById(R.id.tv_weather_alarm_content);
    }

    @Override
    protected void initData() throws Exception {
        //todo:展示数据
        setWeatherInfo();

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setWeatherInfo() {
        getWeatherInfo();
        try {
            Thread.sleep(800);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("TAG", "setWeatherInfo: " + weatherInfo);
        cityText.setText(getSharedPreferences("data", Context.MODE_PRIVATE).getString("district", ""));
//        countryText.setText(weatherInfo.getCountry());
        dateText.setText(weatherInfo.getDate());
        weekText.setText(weatherInfo.getWeek());
        tempText.setText(weatherInfo.getTem() + "℃");
        tem1Text.setText(weatherInfo.getTem1() + " / " + weatherInfo.getTem2() + "℃");
//        tem2Text.setText();
        weatherText.setText(weatherInfo.getWea());
        airLevelText.setText(weatherInfo.getAirLevel());
        airTipsText.setText(weatherInfo.getAirTips());
        pm2Text.setText(weatherInfo.getAirPm25());
        airText.setText(weatherInfo.getAir());
        kouZhaoText.setText(weatherInfo.getAqi().getKouzhao());
        outText.setText(weatherInfo.getAqi().getWaichu());
        sportsText.setText(weatherInfo.getAqi().getYundong());
        windowText.setText(weatherInfo.getAqi().getKaichuang());
        alarmTitleText.setText(weatherInfo.getAlarm().getAlarmTitle());
        alarmInfoText.setText(weatherInfo.getAlarm().getAlarmContent());
        if (weatherInfo.getWea().equals("多云")) {
            weatherIv.setImageResource(R.mipmap.weather_cloudy_img);
        } else if (weatherInfo.getWea().equals("晴")) {
            weatherIv.setImageResource(R.mipmap.weather_sun_img);
        } else if( weatherInfo.getWea().contains("雨")){
            weatherIv.setImageResource(R.mipmap.weather_rain_img);
        }else if( weatherInfo.getWea().contains("雪")){
            weatherIv.setImageResource(R.mipmap.weather_snow_img);
        }
        else {
            weatherIv.setImageResource(R.mipmap.weather_sun_img);
        }
    }

    private void getWeatherInfo() {
        String cityName = getSharedPreferences("data", Context.MODE_PRIVATE).getString("district", "");
        Api.config(cityName).postRequest(WeatherActivity.this, new ApiCallback() {
            @Override
            public void onSuccess(String res) {
//                Log.e("TAG", "setWeatherInfo onSuccess: " + res);
                weatherInfo = new Gson().fromJson(res, WeatherInfo.class);
                Log.e("TAG", "WeatherActivity onSuccess: " + weatherInfo.getWea());

            }

            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", "WeatherActivity onFailure: 失败！");
            }
        });
    }
}