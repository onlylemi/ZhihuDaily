package com.onlylemi.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.contract.NewsCommentsContract;
import com.onlylemi.zhihudaily.data.entity.NewsComments;
import com.onlylemi.zhihudaily.ui.adapter.CommentsAdapter;
import com.onlylemi.zhihudaily.ui.fragment.base.BaseFragment;
import com.onlylemi.zhihudaily.ui.widget.PinnedSectionListView;

import java.util.List;

/**
 * NewsCommentsFragment
 *
 * @author: onlylemi
 * @time: 2016-06-25 19:18
 */
public class NewsCommentsFragment extends BaseFragment implements NewsCommentsContract.View {

    private NewsCommentsContract.Presenter presenter;

    private CommentsAdapter adapter;

    private String newsId;

    private SwipeRefreshLayout swipeRefreshLayout;

    public static NewsCommentsFragment newInstance() {
        return new NewsCommentsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != adapter) {
            adapter.clear();
        }
        presenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CommentsAdapter();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news_comments, container, false);

        // listview 评论列表
        PinnedSectionListView listView = (PinnedSectionListView) root.findViewById(R.id
                .news_comments_list_view);
        listView.setAdapter(adapter);

        // 下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id
                .swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                presenter.start();
            }
        });

        return root;
    }

    @Override
    public void setPresenter(NewsCommentsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingIndicator(final boolean active) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showLongComments(List<NewsComments.CommentsBean> comments) {
        adapter.addList(comments.size() + " 条长评", comments);
        adapter.notifyDataSetChanged();

        showLoadingIndicator(false);
        presenter.setToolbarTitle((adapter.getCount() - 2) + " 条评论");
    }

    @Override
    public void showShortComments(List<NewsComments.CommentsBean> comments) {
        adapter.addList(comments.size() + " 条短评", comments);
        adapter.notifyDataSetChanged();

        showLoadingIndicator(false);
        presenter.setToolbarTitle((adapter.getCount() - 2) + " 条评论");
    }

    @Override
    public void showToolbarTitle(String title) {
        if (null != listener) {
            listener.updateActionBarTitle(title);
        }
    }

    @Override
    public void showNoComments() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_comments_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                presenter.addComment(null);
                Snackbar.make(getView(), "添加评论", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
