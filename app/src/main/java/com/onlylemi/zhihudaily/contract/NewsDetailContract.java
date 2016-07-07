package com.onlylemi.zhihudaily.contract;

import com.onlylemi.zhihudaily.contract.base.BasePresenter;
import com.onlylemi.zhihudaily.contract.base.BaseView;
import com.onlylemi.zhihudaily.data.entity.NewsDetail;

/**
 * NewsDetailContract
 *
 * @author: onlylemi
 * @time: 2016-06-25 12:28
 */
public interface NewsDetailContract {

    interface Presenter extends BasePresenter {

        /**
         * 加载正文内容
         *
         * @param newsId
         */
        void loadingNewsContent(String newsId);

        /**
         * 添加收藏
         */
        boolean addFavorites(NewsDetail newsDetail);

        /**
         * 打开评论
         */
        void openComments();

        /**
         * 分享
         */
        void share();

        /**
         * 点赞
         */
        void addLike();

        /**
         * 加载额外信息
         *
         * @param newsId
         */
        void loadingNewsExtra(String newsId);
    }

    interface View extends BaseView<Presenter> {

        /**
         * 显示正文内容
         *
         * @param news
         */
        void showNewsContent(NewsDetail news);

        /**
         * 显示评论数
         *
         * @param num
         */
        void showCommentsNum(String num);

        /**
         * 显示点赞数
         *
         * @param num
         */
        void showLikesNum(String num);

        /**
         * 没有内容、网络不可用时，显示
         */
        void showNoContent();

        /**
         * 跳转到评论页面
         *
         * @param newsId
         */
        void gotoComments(String newsId);
    }
}
