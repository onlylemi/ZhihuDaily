package com.onlylemi.zhihudaily.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.data.entity.NewsComments;
import com.onlylemi.zhihudaily.ui.widget.PinnedSectionListView;
import com.onlylemi.zhihudaily.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * CommentsAdapter
 *
 * @author: onlylemi
 * @time: 2016-06-26 12:06
 */
public class CommentsAdapter extends BaseAdapter implements PinnedSectionListView
        .PinnedSectionListAdapter {

    private static final String TAG = CommentsAdapter.class.getSimpleName();

    private Context context;
    private List<Comment> list;

    public CommentsAdapter() {
        list = new ArrayList<>();
    }

    public void clear() {
        list.clear();
    }

    public void addList(String title, List<NewsComments.CommentsBean> comments) {
        list.add(new Comment(null, title, Type.GROUP));

        for (NewsComments.CommentsBean comment : comments) {
            list.add(new Comment(comment, null, Type.CHILD));
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Comment getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        switch (getItemViewType(position)) {
            case Type.GROUP:
                TextView text;
                if (null == convertView) {
                    convertView = LayoutInflater.from(context).inflate(android.R.layout
                            .simple_list_item_1, parent, false);
                    text = (TextView) convertView.findViewById(android.R.id.text1);
                    convertView.setTag(text);
                } else {
                    text = (TextView) convertView.getTag();
                }
                text.setText(list.get(position).title);
                text.setBackgroundResource(R.color.cardview_light_background);
                break;
            default:
                ViewHolder holder = null;
                if (null == convertView) {
                    holder = new ViewHolder();
                    convertView = LayoutInflater.from(context).inflate(R.layout
                            .news_comments_item, parent, false);

                    holder.author = (TextView) convertView.findViewById(R.id
                            .news_comments_item_author);
                    holder.content = (TextView) convertView.findViewById(R.id
                            .news_comments_item_content);
                    holder.avatar = (CircleImageView) convertView.findViewById(R.id
                            .news_comments_item_avatar);
                    holder.time = (TextView) convertView.findViewById(R.id.news_comments_item_time);
                    holder.likes = (TextView) convertView.findViewById(R.id
                            .news_comments_item_likes);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.author.setText(list.get(position).commentsBean.getAuthor());
                holder.content.setText(list.get(position).commentsBean.getContent());
                Picasso.with(context)
                        .load(list.get(position).commentsBean.getAvatar())
                        .into(holder.avatar);
                holder.time.setText(TimeUtils.timestampToDate(list.get(position).commentsBean
                                .getTime(), "MM-dd HH:mm"));
                holder.likes.setText(list.get(position).commentsBean.getLikes() + "");

                break;
        }
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == Type.GROUP;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    static class ViewHolder {
        TextView author;
        TextView content;
        CircleImageView avatar;
        TextView time;
        TextView likes;
    }

    static class Type {
        final static int GROUP = 0;
        final static int CHILD = 1;
    }

    class Comment {
        int type;
        NewsComments.CommentsBean commentsBean;
        String title;

        public Comment(NewsComments.CommentsBean commentsBean, String title, int type) {
            this.commentsBean = commentsBean;
            this.title = title;
            this.type = type;
        }
    }
}
