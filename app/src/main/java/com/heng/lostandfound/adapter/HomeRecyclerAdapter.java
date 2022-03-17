package com.heng.lostandfound.adapter;

import static android.content.Intent.getIntent;
import static androidx.core.content.ContextCompat.startActivity;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heng.lostandfound.R;
import com.heng.lostandfound.activity.GoodsInfoActivity;
import com.heng.lostandfound.entity.RecyclerItem;
import com.heng.lostandfound.utils.Constant;

import java.util.List;

/**
 * @author : HengZhang
 * @date : 2022/3/10 13:18
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {
    private List<RecyclerItem> mList;
    private Context mContext;

    public HomeRecyclerAdapter(List<RecyclerItem> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_home_recycler, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.goodNameTv.setText(mList.get(position).getGoodsName());

        holder.authorNameTv.setText(mList.get(position).getAuthorName());
        holder.timeTv.setText(mList.get(position).getOrderTime());
        holder.goodsTypeTv.setText(mList.get(position).getGoodsType());
        holder.recyclerImage.setImageResource(R.mipmap.books);
        if (mList.get(position).getOrderType() == Constant.ORDER_TYPE_GET) {
            holder.orderTypeTv.setText("招领启事");
        } else {
            holder.orderTypeTv.setText("寻物启事");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView goodNameTv, orderTypeTv, authorNameTv, timeTv, goodsTypeTv;
        ImageView recyclerImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            goodNameTv = itemView.findViewById(R.id.good_name);
            orderTypeTv = itemView.findViewById(R.id.order_type);
            authorNameTv = itemView.findViewById(R.id.author_name);
            timeTv = itemView.findViewById(R.id.order_time);
            goodsTypeTv = itemView.findViewById(R.id.goods_type);
            recyclerImage = itemView.findViewById(R.id.home_recycler_item_image);

            //todo：设置recycler的点击监听事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(itemView.getContext(), goodNameTv.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(itemView.getContext(), GoodsInfoActivity.class);
                    intent.putExtra("goodsName", goodNameTv.getText().toString());
                    intent.putExtra("authorName", authorNameTv.getText().toString());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
