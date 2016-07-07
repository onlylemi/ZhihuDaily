package com.onlylemi.zhihudaily.contract;

import com.onlylemi.zhihudaily.contract.base.BasePresenter;
import com.onlylemi.zhihudaily.contract.base.BaseView;
import com.onlylemi.zhihudaily.data.entity.NewsComments;

import java.util.List;

/**
 * NewsCommentsContract
 *
 * @author: onlylemi
 * @time: 2016-06-25 19:03
 */
public interface NewsCommentsContract {

    interface Presenter extends BasePresenter {

        /**
         * 加载长评论
         *
         * @param newsId
         */
        void loadingLongComments(String newsId);

        /**
         * 加载短评论
         *
         * @param newsId
         */
        void loadingShortComments(String newsId);

        /**
         * 设置标题
         *
         * @param title
         */
        void setToolbarTitle(String title);

        /**
         * 添加评论
         */
        void addComment(NewsComments.CommentsBean comment);
    }

    interface View extends BaseView<Presenter> {

        /**
         * 显示刷新
         *
         * @param active
         */
        void showLoadingIndicator(boolean active);

        /**
         * 显示长评论
         *
         * @param comments
         */
        void showLongComments(List<NewsComments.CommentsBean> comments);

        /**
         * 显示短评论
         *
         * @param comments
         */
        void showShortComments(List<NewsComments.CommentsBean> comments);

        /**
         * 显示标题
         *
         * @param title
         */
        void showToolbarTitle(String title);

        /**
         * 没有评论、网络不可用时，显示
         */
        void showNoComments();
    }
}
