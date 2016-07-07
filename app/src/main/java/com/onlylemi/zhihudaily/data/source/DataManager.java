package com.onlylemi.zhihudaily.data.source;

import com.onlylemi.zhihudaily.data.entity.StoriesLocal;

import java.util.List;

/**
 * DataManager
 *
 * @author: onlylemi
 * @time: 2016-06-27 23:14
 */
public class DataManager implements DataSource.Local, DataSource.Remote {

    private static final String TAG = DataManager.class.getSimpleName();

    private static DataManager instance = null;

    private DataSource.Local local;

    private DataSource.Remote remote;

    public DataManager(DataSource.Local local, DataSource.Remote remote) {
        this.local = local;
        this.remote = remote;
    }

    public static DataManager getInstance(DataSource.Local local, DataSource.Remote remote) {
        if (null == instance) {
            instance = new DataManager(local, remote);
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    @Override
    public void getFavorites(final DataSource.LoadingListCallback<StoriesLocal> callback) {
        local.getFavorites(new DataSource.LoadingListCallback<StoriesLocal>() {
            @Override
            public void onLoaded(List<StoriesLocal> list) {
                callback.onLoaded(list);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getFavorite(String newsId, final DataSource.LoadingCallback<StoriesLocal>
            callback) {
        local.getFavorite(newsId, new DataSource.LoadingCallback<StoriesLocal>() {
            @Override
            public void onLoaded(StoriesLocal storiesLocal) {
                callback.onLoaded(storiesLocal);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void searchFavorites(String title, final DataSource.LoadingListCallback<StoriesLocal>
            callback) {
        local.searchFavorites(title, new DataSource.LoadingListCallback<StoriesLocal>() {
            @Override
            public void onLoaded(List<StoriesLocal> list) {
                callback.onLoaded(list);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public boolean addFavorite(final StoriesLocal news) {
        final boolean[] flag = {false};
        local.getFavorite(news.getId() + "", new DataSource.LoadingCallback<StoriesLocal>() {
            @Override
            public void onLoaded(StoriesLocal storiesLocal) {
                flag[0] = false;
            }

            @Override
            public void onDataNotAvailable() {
                flag[0] = local.addFavorite(news);

            }
        });

        return flag[0];
    }

    @Override
    public boolean deleteAllFavorite() {
        return local.deleteAllFavorite();
    }

    @Override
    public boolean deleteFavorite(String newsId) {
        return local.deleteFavorite(newsId);
    }


    @Override
    public void getEntity(String url, final DataSource.LoadingCallback<String> callback) {
        remote.getEntity(url, new DataSource.LoadingCallback<String>() {
            @Override
            public void onLoaded(String s) {
                callback.onLoaded(s);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
}
