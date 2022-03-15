package com.heng.lostandfound.api;

/**
 * @author : HengZhang
 * @date : 2022/3/5 10:31
 * title: 网络路径
 */

public class ApiConfig {
    public static final String BASE_URl = "http://192.168.1.151:8080/app";
    public static final String LOGIN = "/user/login"; //登录
    public static final String REGISTER = "/user/register"; //注册
    public static final String GET_ALL_ORDER = "/order/getOrderList"; //获得所有的order
    public static final String ADD_ORDER = "/order/addOrder"; //获得所有的order
    public static final String GET_ALL_GOODS_TYPE = "/order/getAllGoodsType"; //获得所有的goodsType
    public static final String GET_GOODS_INFO = "/goods/getGoodsInfo"; //获得所有的goodsInfo
    public static final String GET_GOODS_COMMENTS = "/comment/getCommentListById"; //获得所有的goodsInfo
    public static final String ADD_GOODS_COMMENT = "/comment/addComment"; //新增一条评论
}
