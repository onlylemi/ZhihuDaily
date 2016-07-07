package com.onlylemi.zhihudaily.contract;

import com.onlylemi.zhihudaily.contract.base.BasePresenter;
import com.onlylemi.zhihudaily.contract.base.BaseView;
import com.onlylemi.zhihudaily.data.entity.Daily;

import java.util.List;

/**
 * DailyContract
 *
 * @author: onlylemi
 * @time: 2016-06-23 13:28
 */
public interface DailyContract {

    interface Presenter extends BasePresenter {

        /**
         * 加载最新日报
         */
        void loadingLatestDaily();

        /**
         * 加载某天的日报
         *
         * @param time 格式：2016-06-23
         */
        void loadingDaily(String time);


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
        void setLoadingIndicator(boolean active);

        /**
         * 展示日报列表
         *
         * @param list
         */
        void showDailyNews(List<Daily.StoriesBean> list, String title);

        /**
         * 显示置顶文章
         *
         * @param list
         */
        void showTopNews(List<Daily.TopStoriesBean> list);

        /**
         * 没有日报文章、网络不可用时，显示
         */
        void showNoDaily();

        /**
         * 跳转到下一个界面
         *
         * @param newsId
         */
        void gotoNewsDetails(String newsId);
    }
}
