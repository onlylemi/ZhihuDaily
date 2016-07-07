package com.onlylemi.zhihudaily.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.data.entity.StoriesLocal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * NewsAdapter
 *
 * @author: onlylemi
 * @time: 2016-06-28 9:50
 */
public class NewsAdapter extends BaseAdapter {

    private Context context;
    private List<StoriesLocal> list;

    public NewsAdapter() {
        list = new ArrayList<>();
    }

    public void bindList(List<StoriesLocal> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StoriesLocal getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.news_item, parent,
                    false);
            holder.image = (ImageView) convertView.findViewById(R.id.news_img);
            holder.title = (TextView) convertView.findViewById(R.id.news_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String titleStr = list.get(position).getTitle();
        String imgStr = "";

        if (null != list.get(position).getImages()) {
            imgStr = list.get(position).getImages().get(0);
        }

        if (!titleStr.isEmpty()) {
            holder.title.setText(titleStr);
        }

        if (!imgStr.isEmpty()) {
            Picasso.with(context)
                    .load(imgStr)
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .resize(150, 150)
                    .into(holder.image);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView title;
    }
}
