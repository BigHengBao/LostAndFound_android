package com.heng.lostandfound.api;

/**
 * @author : HengZhang
 * @date : 2022/3/5 10:31
 * title: 网络路径
 */

public class ApiConfig {
    public static final String BASE_URl = "http://192.168.1.106:8080/app";
    public static final String USER_LOGIN = "/user/login"; //登录
    public static final String USER_REGISTER = "/user/register"; //注册
    public static final String GET_USER_INFO = "/user/getUserInfo"; //获取用户信息
    public static final String ADJUST_USER_INFO = "/user/adjustUserInfo"; //修改个人用户信息
    public static final String GET_ALL_ORDER = "/order/getOrderList"; //获得所有的order
    public static final String ADD_ORDER = "/order/addOrder"; //获得所有的order
    public static final String GET_ALL_GOODS_TYPE = "/order/getAllGoodsType"; //获得所有的goodsType
    public static final String GET_GOODS_INFO = "/goods/getGoodsInfo"; //获得所有的goodsInfo
    public static final String GET_GOODS_COMMENTS = "/comment/getCommentListById"; //获得所有的goodsInfo
    public static final String ADD_GOODS_COMMENT = "/comment/addComment"; //新增一条评论
    public static final String OPERATE_COLLECTION = "/collection/operateCollection"; //新增收藏
    public static final String GET_COLLECTIONS = "/collection/getCollectionListById"; //查询收藏
    public static final String GET_USER_ORDER = "/user/getUserOrderList"; //获取当前用户发布的启事
    public static final String TEST_IMAGE = "/testImage/operateImage"; //测试图片上传

    public static final String WEATHER_BASE = "https://v0.yiketianqi.com/api" +
            "?unescape=1&version=v61&appid=59332147&appsecret=3dYykI3x "; //获取本地天气情况
}
