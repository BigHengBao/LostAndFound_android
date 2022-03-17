package com.heng.lostandfound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heng.lostandfound.R;
import com.heng.lostandfound.entity.CommentItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : HengZhang
 * @date : 2022/3/16 11:08
 * title: 物品评论listview的适配器
 */

public class CommentsLvAdapter extends BaseAdapter {
    Context context;
    List<CommentItem> mDatas;

    public CommentsLvAdapter(Context context, List<CommentItem> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_listview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageResource(R.mipmap.woman);
        holder.usernameTv.setText(mDatas.get(position).getUsername());
        holder.contentTv.setText(mDatas.get(position).getContent());
        holder.addTimeTv.setText(mDatas.get(position).getAddTime());

        return convertView;
    }

    static class ViewHolder {
        TextView usernameTv, contentTv, addTimeTv;
        CircleImageView imageView;

        public ViewHolder(View view) {
            usernameTv = view.findViewById(R.id.tv_comment_username);
            contentTv = view.findViewById(R.id.tv_comment_content);
            addTimeTv = view.findViewById(R.id.tv_comment_time);
            imageView = view.findViewById(R.id.iv_user_comment);

        }
    }
}
