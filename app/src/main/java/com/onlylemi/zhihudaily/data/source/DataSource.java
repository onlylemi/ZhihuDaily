package com.onlylemi.zhihudaily.data.source;

import com.onlylemi.zhihudaily.data.entity.StoriesLocal;

import java.util.List;

/**
 * DataSource
 *
 * @author: onlylemi
 * @time: 2016-06-27 22:59
 */
public interface DataSource {

    interface LoadingListCallback<T> {

        void onLoaded(List<T> list);

        void onDataNotAvailable();
    }

    interface LoadingCallback<T> {

        void onLoaded(T t);

        void onDataNotAvailable();
    }

    interface Local {

        void getFavorites(LoadingListCallback<StoriesLocal> callback);

        void getFavorite(String newsId, LoadingCallback<StoriesLocal> callback);

        void searchFavorites(String title, LoadingListCallback<StoriesLocal> callback);

        boolean addFavorite(StoriesLocal news);

        boolean deleteAllFavorite();

        boolean deleteFavorite(String newsId);
    }

    interface Remote {

        void getEntity(String url, LoadingCallback<String> callback);
    }

}
