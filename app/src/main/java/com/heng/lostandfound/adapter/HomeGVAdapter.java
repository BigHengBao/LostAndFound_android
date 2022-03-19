package com.heng.lostandfound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heng.lostandfound.R;
import com.heng.lostandfound.entity.HomeGVItem;

import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/10 11:10
 * title:home的GV分类适配器
 */

public class HomeGVAdapter extends BaseAdapter {
    Context context;
    List<HomeGVItem> mDatas;

    List<ImageView> ivList;

    int[] imgIds = {R.mipmap.zawu, R.mipmap.others, R.mipmap.electronic, R.mipmap.books, R.mipmap.financialdocs,
            R.mipmap.foods, R.mipmap.house, R.mipmap.daixu};

    public HomeGVAdapter(Context context, List<HomeGVItem> mDatas, List<ImageView> ivList) {
        this.context = context;
        this.mDatas = mDatas;
        this.ivList = ivList;
    }

    public HomeGVAdapter(Context context, List<HomeGVItem> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    public HomeGVAdapter() {
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;


        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_gv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取指定位置的数据
        HomeGVItem bean = mDatas.get(position);
        holder.itemTv.setText(bean.getItemName());

        //通过名称，获取存储在内存当中的图片
        holder.imageIv.setImageResource(imgIds[position]);

        return convertView;
    }

    class ViewHolder {
        ImageView imageIv;
        TextView itemTv;

        public ViewHolder(View view) {
            imageIv = view.findViewById(R.id.gv_item_image);
            itemTv = view.findViewById(R.id.gv_item_tv);
        }
    }
}
