package com.onlylemi.zhihudaily.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.data.entity.Daily;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * DailyAdapter
 *
 * @author: onlylemi
 * @time: 2016-06-24 15:23
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private static final String TAG = DailyAdapter.class.getSimpleName();

    private Context context;
    private List<Daily.StoriesBean> list;
    private OnItemClickListener listener;

    public DailyAdapter(List<Daily.StoriesBean> list) {
        this.list = list;
    }

    public void bindList(List<Daily.StoriesBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.daily_news_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String titleStr = list.get(position).getTitle();
        String imgStr = "";

        if (null != list.get(position).getImages()) {
            imgStr = list.get(position).getImages().get(0);
        }

        if (!titleStr.isEmpty()) {
            holder.dailyTitle.setText(list.get(position).getTitle());
        }
        if (!imgStr.isEmpty()) {
            Picasso.with(context)
                    .load(imgStr)
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .resize(200, 200)
                    .into(holder.dailyImg);
        }

        if (null != listener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Daily.StoriesBean getItem(int position) {
        return list.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dailyTitle;
        ImageView dailyImg;

        public ViewHolder(View itemView) {
            super(itemView);
            dailyTitle = (TextView) itemView.findViewById(R.id.daily_news_title);
            dailyImg = (ImageView) itemView.findViewById(R.id.daily_news_img);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * item 点击监听事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
