package com.onlylemi.zhihudaily.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.onlylemi.zhihudaily.data.entity.StoriesLocal;
import com.onlylemi.zhihudaily.data.source.DataSource;
import com.onlylemi.zhihudaily.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * DBManager
 *
 * @author: onlylemi
 * @time: 2016-06-28 0:31
 */
public class DBManager implements DataSource.Local {

    private static final String TAG = DBManager.class.getSimpleName();

    private static DBManager instance;

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
    }

    public static DBManager getInstance(Context context) {
        if (null == instance) {
            instance = new DBManager(context);
        }
        return instance;
    }

    @Override
    public void getFavorites(DataSource.LoadingListCallback<StoriesLocal> callback) {
        db = helper.getReadableDatabase();

        List<StoriesLocal> list = new ArrayList<>();

        String orderBy = DBParameter.StoriesTable.COLUMNS_DATE + " DESC";
        Cursor cursor = db.query(DBParameter.StoriesTable.TABLE_NAME, null, null, null,
                null, null, orderBy);

        StoriesLocal news = null;
        if (null != cursor && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                news = new StoriesLocal();

                news.setImages(AppUtils.string2List(cursor.getString(cursor.getColumnIndexOrThrow
                        (DBParameter.StoriesTable.COLUMNS_IMAGES)), ","));
                news.setGa_prefix(cursor.getString(cursor.getColumnIndexOrThrow
                        (DBParameter.StoriesTable.COLUMNS_GA_PREFIX)));
                news.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBParameter
                        .StoriesTable.COLUMNS_ID)));
                news.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DBParameter
                        .StoriesTable.COLUMNS_TITLE)));
                news.setType(cursor.getInt(cursor.getColumnIndexOrThrow(DBParameter
                        .StoriesTable.COLUMNS_TYPE)));
                news.setDate(cursor.getInt(cursor.getColumnIndexOrThrow(DBParameter
                        .StoriesTable.COLUMNS_DATE)));

                list.add(news);
            }
        }

        if (null != cursor) {
            cursor.close();
        }
        db.close();

        if (!list.isEmpty()) {
            callback.onLoaded(list);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void getFavorite(String newsId, DataSource.LoadingCallback<StoriesLocal> callback) {
        db = helper.getReadableDatabase();

        String selection = DBParameter.StoriesTable.COLUMNS_ID + " LIKE ?";
        String[] selectionArgs = {newsId};
        String orderBy = DBParameter.StoriesTable.COLUMNS_DATE + " DESC";

        Cursor cursor = db.query(DBParameter.StoriesTable.TABLE_NAME, null, selection,
                selectionArgs, null, null, orderBy);

        StoriesLocal news = null;
        if (null != cursor && cursor.getCount() > 0) {
            cursor.moveToFirst();

            news = new StoriesLocal();
            news.setImages(AppUtils.string2List(cursor.getString(cursor.getColumnIndexOrThrow
                    (DBParameter.StoriesTable.COLUMNS_IMAGES)), ","));
            news.setGa_prefix(cursor.getString(cursor.getColumnIndexOrThrow
                    (DBParameter.StoriesTable.COLUMNS_GA_PREFIX)));
            news.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBParameter
                    .StoriesTable.COLUMNS_ID)));
            news.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DBParameter
                    .StoriesTable.COLUMNS_TITLE)));
            news.setType(cursor.getInt(cursor.getColumnIndexOrThrow(DBParameter
                    .StoriesTable.COLUMNS_TYPE)));
            news.setDate(cursor.getInt(cursor.getColumnIndexOrThrow(DBParameter
                    .StoriesTable.COLUMNS_DATE)));
        }

        if (null != cursor) {
            cursor.close();
        }

        db.close();

        if (null != news) {
            callback.onLoaded(news);
        } else {
            callback.onDataNotAvailable();
        }

    }

    @Override
    public void searchFavorites(String title, DataSource.LoadingListCallback<StoriesLocal>
            callback) {
        db = helper.getReadableDatabase();

        Log.i(TAG, "searchFavorites: " + title);

        List<StoriesLocal> list = new ArrayList<>();

        String selection = DBParameter.StoriesTable.COLUMNS_TITLE + " LIKE ?";
        String[] selectionArgs = {"%" + title + "%"};
        String orderBy = DBParameter.StoriesTable.COLUMNS_DATE + " DESC";
        Cursor cursor = db.query(DBParameter.StoriesTable.TABLE_NAME, null, selection,
                selectionArgs, null, null, orderBy);

        StoriesLocal news = null;
        if (null != cursor && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                news = new StoriesLocal();

                news.setImages(AppUtils.string2List(cursor.getString(cursor.getColumnIndexOrThrow
                        (DBParameter.StoriesTable.COLUMNS_IMAGES)), ","));
                news.setGa_prefix(cursor.getString(cursor.getColumnIndexOrThrow
                        (DBParameter.StoriesTable.COLUMNS_GA_PREFIX)));
                news.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBParameter
                        .StoriesTable.COLUMNS_ID)));
                news.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DBParameter
                        .StoriesTable.COLUMNS_TITLE)));
                news.setType(cursor.getInt(cursor.getColumnIndexOrThrow(DBParameter
                        .StoriesTable.COLUMNS_TYPE)));
                news.setDate(cursor.getInt(cursor.getColumnIndexOrThrow(DBParameter
                        .StoriesTable.COLUMNS_DATE)));

                list.add(news);
            }
        }

        if (null != cursor) {
            cursor.close();
        }
        db.close();

        if (!list.isEmpty()) {
            callback.onLoaded(list);
        } else {
            callback.onDataNotAvailable();
        }
    }


    @Override
    public boolean addFavorite(StoriesLocal news) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBParameter.StoriesTable.COLUMNS_GA_PREFIX, news.getGa_prefix());
        values.put(DBParameter.StoriesTable.COLUMNS_ID, news.getId());
        values.put(DBParameter.StoriesTable.COLUMNS_IMAGES, AppUtils.list2String(
                news.getImages(), ","));
        values.put(DBParameter.StoriesTable.COLUMNS_TITLE, news.getTitle());
        values.put(DBParameter.StoriesTable.COLUMNS_TYPE, news.getType());
        values.put(DBParameter.StoriesTable.COLUMNS_DATE, news.getDate());

        long result = db.insert(DBParameter.StoriesTable.TABLE_NAME, null, values);
        db.close();

        return result != -1;
    }

    @Override
    public boolean deleteAllFavorite() {
        db = helper.getWritableDatabase();

        int result = db.delete(DBParameter.StoriesTable.TABLE_NAME, null, null);
        db.close();

        return result != -1;
    }

    @Override
    public boolean deleteFavorite(String newsId) {
        db = helper.getWritableDatabase();

        String selection = DBParameter.StoriesTable.COLUMNS_ID + " LIKE ?";
        String[] selectionArgs = {newsId};
        int result = db.delete(DBParameter.StoriesTable.TABLE_NAME, selection,
                selectionArgs);
        db.close();

        return result != -1;
    }
}
