package com.onlylemi.zhihudaily.presenter;

import com.google.gson.Gson;
import com.onlylemi.zhihudaily.contract.NewsCommentsContract;
import com.onlylemi.zhihudaily.data.ZhihuDailyAPI;
import com.onlylemi.zhihudaily.data.entity.NewsComments;
import com.onlylemi.zhihudaily.data.source.DataManager;
import com.onlylemi.zhihudaily.data.source.DataSource;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * NewsCommentsPresenter
 *
 * @author: onlylemi
 * @time: 2016-06-25 19:17
 */
public class NewsCommentsPresenter implements NewsCommentsContract.Presenter {

    private NewsCommentsContract.View mView;

    private DataManager dataManager;

    private String newsId;

    public NewsCommentsPresenter(DataManager dataManager, NewsCommentsContract.View mView, String
            newsId) {
        this.dataManager = dataManager;
        this.mView = mView;
        this.newsId = newsId;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadingLongComments(newsId);
        loadingShortComments(newsId);
    }

    @Override
    public void loadingLongComments(String newsId) {
        loadingComments(newsId, ZhihuDailyAPI.CommentType.LONG);
    }

    @Override
    public void loadingShortComments(String newsId) {
        loadingComments(newsId, ZhihuDailyAPI.CommentType.SHORT);
    }

    private void loadingComments(final String newsId, final ZhihuDailyAPI.CommentType type) {
        Observable.create(new Observable.OnSubscribe<NewsComments>() {
            @Override
            public void call(final Subscriber<? super NewsComments> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    // 获取文章评论json数据
                    dataManager.getEntity(ZhihuDailyAPI.comments(newsId, type), new DataSource
                            .LoadingCallback<String>() {
                        @Override
                        public void onLoaded(String s) {
                            // 将json数据转化为实体类
                            NewsComments newsComments = new Gson().fromJson(s, NewsComments.class);
                            if (null != newsComments) {
                                subscriber.onNext(newsComments);
                            }
                        }

                        @Override
                        public void onDataNotAvailable() {

                        }
                    });
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsComments>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(NewsComments newsComments) {
                                   if (null != newsComments.getComments()) {
                                       if (type == ZhihuDailyAPI.CommentType.SHORT) {
                                           mView.showShortComments(newsComments.getComments());
                                       } else {
                                           mView.showLongComments(newsComments.getComments());
                                       }
                                   }
                               }
                           }

                );
    }

    @Override
    public void setToolbarTitle(String title) {
        mView.showToolbarTitle(title);
    }

    @Override
    public void addComment(NewsComments.CommentsBean comment) {

    }

}
