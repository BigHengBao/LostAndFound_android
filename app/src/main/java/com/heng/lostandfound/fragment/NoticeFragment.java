package com.heng.lostandfound.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.heng.lostandfound.R;
import com.heng.lostandfound.activity.RegisterActivity;
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
import java.util.Objects;

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
    ImageView noticeIv, locationIv;
    List<String> goodsTypes;
    String gType;  //物品类型
    Integer orderType;
    String userAccount, imageStr;
    AlertDialog dialog;  //对话框

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
        noticeIv = mRootView.findViewById(R.id.notice_submit_image);
        locationIv = mRootView.findViewById(R.id.iv_notice_location);

        // todo: 测试数据，后期得删除
        gNameEd.setText("");
        addressEd.setText("二工大13号楼408门口");
        contentEd.setText("电话:13488889999");
    }

    @Override
    protected void initData() {

        setSpinner();

        locationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressEd.setText(
                        getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).getString("address", ""));
            }
        });

        //这一两行代码主要是向用户请求图片权限
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //todo:设置监听器，选择相册图片当头像
        noticeIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showToast("设置图片");

                openSysAlbum();
            }
        });

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
                    goods.setgImage(imageStr);

                    if (getTypeBtn.isChecked()) {
                        orderType = Constant.ORDER_TYPE_GET;
                        goods.setGetTime(new Timestamp(System.currentTimeMillis()).toString());
                        goods.setLoseTime("**");
                    } else {
                        orderType = Constant.ORDER_TYPE_LOOKING;
                        goods.setLoseTime(new Timestamp(System.currentTimeMillis()).toString());
                        goods.setGetTime("**");
                    }


                    dialog = new AlertDialog.Builder(getContext())
                            .setIcon(R.mipmap.dialog_img)//设置标题的图片
                            .setTitle("\t 提醒")//设置对话框的标题
                            .setMessage("\n 是否确认提交启事？")//设置对话框的内容
                            //设置对话框的按钮
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                   showToast();
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //发送post
                                    submitOrder(goods);
//
//                                    Toast.makeText(MainActivity.this, "点击了确定的按钮", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }).create();
                    dialog.show();


                } else {
                    showToast("请确保所有带 * 号的项完整");
                }
            }
        });

    }

    //获取本地信息
    private void getLocation() {
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

    //todo：发送post
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
                    clearTexts();
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


    //清空输入框
    public void clearTexts(){
        gNameEd.setText("");
        addressEd.setText("");
        contentEd.setText("");
        noticeIv.setImageBitmap(null);
    }

    public static int ALBUM_RESULT_CODE = 0x999;

    /**
     * 打开系统相册
     * 定义Intent跳转到特定图库的Uri下挑选，然后将挑选结果返回给Activity
     */
    private void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, ALBUM_RESULT_CODE);
    }

    //重载onActivityResult方法，获取相应数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleImageOnKitKat(data);
    }

    //这部分的代码目前没有理解，只知道作用是根据条件的不同去获取相册中图片的url
    //这一部分是从其他博客中查询的
    @TargetApi(value = 19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getContext(), uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        // 根据图片路径显示图片
        displayImage(imagePath);
        System.out.println(imagePath);
    }

    /**
     * 获取图片的路径
     */
    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {

            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 展示图片
     */
    private void displayImage(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        noticeIv.setImageBitmap(bitmap);

        imageStr = new StringUtils().bitmapToString(bitmap);
    }
}
