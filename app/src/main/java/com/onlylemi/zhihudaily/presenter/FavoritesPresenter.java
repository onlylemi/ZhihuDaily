package com.onlylemi.zhihudaily.presenter;

import com.onlylemi.zhihudaily.contract.FavoritesContract;
import com.onlylemi.zhihudaily.data.entity.StoriesLocal;
import com.onlylemi.zhihudaily.data.source.DataManager;
import com.onlylemi.zhihudaily.data.source.DataSource;

import java.util.List;

/**
 * FavoritesPresenter
 *
 * @author: onlylemi
 * @time: 2016-06-25 19:15
 */
public class FavoritesPresenter implements FavoritesContract.Presenter {

    private static final String TAG = FavoritesPresenter.class.getSimpleName();

    private FavoritesContract.View mView;

    private DataManager dataManager;

    public FavoritesPresenter(DataManager dataManager, FavoritesContract.View mView) {
        this.dataManager = dataManager;
        this.mView = mView;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadingFavoriteNews();
    }

    @Override
    public void loadingFavoriteNews() {
        dataManager.getFavorites(new DataSource.LoadingListCallback<StoriesLocal>() {
            @Override
            public void onLoaded(List<StoriesLocal> list) {
                mView.showFavorites(list);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public boolean deleteNews(String newsId) {
        return dataManager.deleteFavorite(newsId);
    }

    @Override
    public void searchNews(String str) {
        dataManager.searchFavorites(str, new DataSource.LoadingListCallback<StoriesLocal>() {
            @Override
            public void onLoaded(List<StoriesLocal> list) {
                mView.showFavorites(list);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void openNews(String newsId) {
        mView.gotoNewsDetail(newsId);
    }
}
