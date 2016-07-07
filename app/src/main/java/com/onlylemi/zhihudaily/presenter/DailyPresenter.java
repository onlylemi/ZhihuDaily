package com.onlylemi.zhihudaily.presenter;

import com.google.gson.Gson;
import com.onlylemi.zhihudaily.contract.DailyContract;
import com.onlylemi.zhihudaily.data.ZhihuDailyAPI;
import com.onlylemi.zhihudaily.data.entity.Daily;
import com.onlylemi.zhihudaily.data.source.DataManager;
import com.onlylemi.zhihudaily.data.source.DataSource;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * DailyPresenter
 *
 * @author: onlylemi
 * @time: 2016-06-24 15:53
 */
public class DailyPresenter implements DailyContract.Presenter {

    private DailyContract.View mView;

    private DataManager dataManager;

    public DailyPresenter(DailyContract.View mView, DataManager dataManager) {
        this.mView = mView;
        this.dataManager = dataManager;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadingLatestDaily();
    }

    @Override
    public void loadingLatestDaily() {
        loadingDateDaily(ZhihuDailyAPI.latest(), "今日推荐");
    }

    @Override
    public void loadingDaily(String time) {
        loadingDateDaily(ZhihuDailyAPI.daily(time), time);
    }

    private void loadingDateDaily(final String url, final String title) {
        Observable.create(new Observable.OnSubscribe<Daily>() {

            @Override
            public void call(final Subscriber<? super Daily> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    // 获取推荐文章json数据
                    dataManager.getEntity(url, new DataSource
                            .LoadingCallback<String>() {

                        @Override
                        public void onLoaded(String s) {
                            // 将json数据转化为实体类
                            Daily daily = new Gson().fromJson(s, Daily.class);
                            if (null != daily) {
                                subscriber.onNext(daily);
                            }
                        }

                        @Override
                        public void onDataNotAvailable() {
                            mView.showNoDaily();
                        }
                    });
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Daily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Daily daily) {
                        if (null != daily.getTop_stories()) {
                            mView.showTopNews(daily.getTop_stories());
                        }
                        if (null != daily.getStories()) {
                            mView.showDailyNews(daily.getStories(), title);
                        }
                        mView.setLoadingIndicator(false);
                    }
                });
    }


    @Override
    public void openNews(String newsId) {
        mView.gotoNewsDetails(newsId);
    }

}
