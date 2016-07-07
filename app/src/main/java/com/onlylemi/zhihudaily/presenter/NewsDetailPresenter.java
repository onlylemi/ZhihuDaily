package com.onlylemi.zhihudaily.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.onlylemi.zhihudaily.contract.NewsDetailContract;
import com.onlylemi.zhihudaily.data.ZhihuDailyAPI;
import com.onlylemi.zhihudaily.data.entity.NewsDetail;
import com.onlylemi.zhihudaily.data.entity.NewsExtra;
import com.onlylemi.zhihudaily.data.entity.StoriesLocal;
import com.onlylemi.zhihudaily.data.source.DataManager;
import com.onlylemi.zhihudaily.data.source.DataSource;
import com.onlylemi.zhihudaily.utils.TimeUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * NewsDetailPresenter
 *
 * @author: onlylemi
 * @time: 2016-06-25 17:33
 */
public class NewsDetailPresenter implements NewsDetailContract.Presenter {

    private static final String TAG = NewsDetailPresenter.class.getSimpleName();

    private NewsDetailContract.View mView;

    private DataManager dataManager;

    private String newsId;

    public NewsDetailPresenter(String newsId, DataManager dataManager, NewsDetailContract.View
            mView) {
        this.newsId = newsId;
        this.dataManager = dataManager;
        this.mView = mView;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadingNewsContent(newsId);
        loadingNewsExtra(newsId);
    }

    @Override
    public void loadingNewsContent(final String newsId) {
        Observable.create(new Observable.OnSubscribe<NewsDetail>() {
            @Override
            public void call(final Subscriber<? super NewsDetail> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    // 获取文章详细内容json数据
                    dataManager.getEntity(ZhihuDailyAPI.news(newsId), new DataSource
                            .LoadingCallback<String>() {

                        @Override
                        public void onLoaded(String s) {
                            // 将json数据转化为实体类
                            NewsDetail newsDetail = new Gson().fromJson(s, NewsDetail.class);
                            if (null != newsDetail) {
                                subscriber.onNext(newsDetail);
                            }
                        }

                        @Override
                        public void onDataNotAvailable() {
                            mView.showNoContent();
                        }
                    });
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        mView.showNewsContent(newsDetail);
                    }
                });
    }

    @Override
    public boolean addFavorites(NewsDetail newsDetail) {
        StoriesLocal storiesLocal = new StoriesLocal();
        storiesLocal.setTitle(newsDetail.getTitle());
        storiesLocal.setId(newsDetail.getId());
        storiesLocal.setImages(newsDetail.getImages());
        storiesLocal.setDate(TimeUtils.currentTimestamp());

        return dataManager.addFavorite(storiesLocal);
    }

    @Override
    public void openComments() {
        mView.gotoComments(newsId);
    }

    @Override
    public void share() {

    }

    @Override
    public void addLike() {

    }

    @Override
    public void loadingNewsExtra(final String newsId) {
        Observable.create(new Observable.OnSubscribe<NewsExtra>() {
            @Override
            public void call(final Subscriber<? super NewsExtra> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    // 获取json数据
                    dataManager.getEntity(ZhihuDailyAPI.storyExtra(newsId), new DataSource
                            .LoadingCallback<String>() {

                        @Override
                        public void onLoaded(String s) {
                            // 将json数据转成实体类
                            NewsExtra newsExtra = new Gson().fromJson(s, NewsExtra.class);
                            if (null != newsExtra) {
                                subscriber.onNext(newsExtra);
                            }
                        }

                        @Override
                        public void onDataNotAvailable() {
                            mView.showNoContent();
                        }
                    });
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsExtra>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsExtra newsExtra) {
                        Log.i(TAG, "onNext: " + newsExtra.getComments());
                        mView.showCommentsNum(newsExtra.getComments() + "");
                        mView.showLikesNum(newsExtra.getPopularity() + "");
                    }
                });
    }
}
