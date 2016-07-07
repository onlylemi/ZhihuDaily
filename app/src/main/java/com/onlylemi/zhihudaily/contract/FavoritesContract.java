package com.onlylemi.zhihudaily.contract;

import com.onlylemi.zhihudaily.contract.base.BasePresenter;
import com.onlylemi.zhihudaily.contract.base.BaseView;
import com.onlylemi.zhihudaily.data.entity.StoriesLocal;

import java.util.List;

/**
 * FavoritesContract
 *
 * @author: onlylemi
 * @time: 2016-06-25 19:08
 */
public interface FavoritesContract {

    interface Presenter extends BasePresenter {

        /**
         * 加载收藏文章
         */
        void loadingFavoriteNews();

        /**
         * 删除文章
         *
         * @param newsId
         * @return
         */
        boolean deleteNews(String newsId);

        /**
         * 搜索文章
         *
         * @param str
         */
        void searchNews(String str);

        /**
         * 打开文章
         *
         * @param newsId
         */
        void openNews(String newsId);
    }

    interface View extends BaseView<Presenter> {

        /**
         * 显示加载指示器
         *
         * @param active
         */
        void showLoadingIndicator(boolean active);

        /**
         * 显示收藏文章列表
         *
         * @param list
         */
        void showFavorites(List<StoriesLocal> list);

        /**
         * 显示Toolbar标题栏
         *
         * @param title
         */
        void showToolbarTitle(String title);

        /**
         * 没有收藏文章、网络不可用时，显示
         */
        void showNoFavorites();

        /**
         * 跳转到文章详细页面
         *
         * @param newsId
         */
        void gotoNewsDetail(String newsId);
    }
}
