package com.heng.lostandfound.api;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author : HengZhang
 * @date : 2022/3/5 10:29
 * title：网络请求
 */

public class Api {
    private static OkHttpClient client;
    private static String requestUrl;
    private static HashMap<String, Object> mParams;
    public static Api api = new Api();

    public Api() {

    }

    public static Api config(String url, HashMap<String, Object> params) {
        client = new OkHttpClient.Builder()
                .build();
        requestUrl = ApiConfig.BASE_URl + url;
        mParams = params;
        return api;
    }

    public static Api config(String cityName) {
        client = new OkHttpClient.Builder()
                .build();
        requestUrl = ApiConfig.WEATHER_BASE + "&city=" + cityName;
        return api;
    }

    //get请求
    public void getRequest(Context context, final ApiCallback callback) {
        //创建Request 对象
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure,网络请求失败", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callback.onSuccess(result);
            }
        });

    }

    // post请求
    public void postRequest(Context context, final ApiCallback callback) {
        System.out.println("postRequest:" + mParams.toString());
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();

        Log.e("", " postRequest: jsonStr" + jsonStr);


        RequestBody requestBodyJson =
                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
                        , jsonStr);
        //第三步创建Request
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("contentType", "application/json;charset=UTF-8")
                .post(requestBodyJson)
                .build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure,网络请求失败", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callback.onSuccess(result);
            }
        });
    }
}
