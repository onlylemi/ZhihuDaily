package com.onlylemi.zhihudaily.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.onlylemi.zhihudaily.App;
import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.contract.FavoritesContract;
import com.onlylemi.zhihudaily.data.entity.StoriesLocal;
import com.onlylemi.zhihudaily.ui.activity.NewsDetailActivity;
import com.onlylemi.zhihudaily.ui.adapter.NewsAdapter;
import com.onlylemi.zhihudaily.ui.fragment.base.BaseFragment;
import com.onlylemi.zhihudaily.utils.AppUtils;

import java.util.List;

/**
 * FavoritesFragment
 *
 * @author: onlylemi
 * @time: 2016-06-25 17:29
 */
public class FavoritesFragment extends BaseFragment implements FavoritesContract.View {

    private static final String TAG = FavoritesFragment.class.getSimpleName();

    private FavoritesContract.Presenter presenter;

    private NewsAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void setPresenter(FavoritesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new NewsAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        // listview
        SwipeMenuListView listView = (SwipeMenuListView) root.findViewById(R.id.favorites_listview);
        listView.setAdapter(adapter);

        // 为listview 添加 header
        View headView = inflater.inflate(R.layout.search, container, false);
        listView.addHeaderView(headView);
        EditText search = (EditText) headView.findViewById(R.id.favorites_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.searchNews(s.toString());
            }
        });

        // litview 滑动菜单
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(R.color.red);
                deleteItem.setIcon(R.drawable.ic_delete);
                deleteItem.setWidth(AppUtils.dp2px(App.getContext(), 80));
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        // listview 侧滑删除菜单监听事件
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if (presenter.deleteNews(adapter.getItem(position).getId() + "")) {
                            showSnack("成功删除该收藏！");
                            presenter.loadingFavoriteNews();
                        } else {
                            showSnack("该收藏无法被删除");
                        }
                        break;
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoriesLocal news = (StoriesLocal) parent.getAdapter().getItem(position);
                presenter.openNews(news.getId() + "");
            }
        });

        // 下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id
                .favorites_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadingFavoriteNews();
            }
        });

        return root;
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
    public void showFavorites(List<StoriesLocal> list) {
        adapter.bindList(list);
        adapter.notifyDataSetChanged();

        showLoadingIndicator(false);
        showToolbarTitle(list.size() + " 条收藏");
    }

    @Override
    public void gotoNewsDetail(String newsId) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.NEWS_ID, newsId);
        startActivity(intent);
    }

    @Override
    public void showToolbarTitle(String title) {
        if (null != listener) {
            listener.updateActionBarTitle(title);
        }
    }

    @Override
    public void showNoFavorites() {

    }

    private void showSnack(String msg) {
        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
    }
}
