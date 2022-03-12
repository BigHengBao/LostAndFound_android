package com.heng.lostandfound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heng.lostandfound.R;

import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/12 18:51
 * me页面listview的适配器
 */

public class LvAdapter extends BaseAdapter {
    Context context;
    List<String> mDatas;
    int[] imgIds;

    public LvAdapter(Context context, List<String> mDatas, int[] imgIds) {
        this.context = context;
        this.mDatas = mDatas;
        this.imgIds = imgIds;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
      holder.titleTv.setText(mDatas.get(position));
      holder.imageView.setImageResource(imgIds[position]);
      holder.enterIv.setImageResource(R.mipmap.me_right_enter);

        return convertView;
    }

    static class ViewHolder {
        TextView titleTv;
        ImageView imageView, enterIv;

        public ViewHolder(View view) {
            titleTv = view.findViewById(R.id.tv_me_list);
            imageView = view.findViewById(R.id.iv_me_list_image);
            enterIv = view.findViewById(R.id.iv_me_list_enter);
        }
    }
}
