package com.onlylemi.zhihudaily.data.source.local;

import android.provider.BaseColumns;

/**
 * DBParameter
 *
 * @author: onlylemi
 * @time: 2016-06-27 9:36
 */
public class DBParameter {

    public static final int DATABASE_VERSION = 1; // 数据库的版本
    public static final String DATABASE_NAME = "zhihudaily.db"; // 数据库名

    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_STORIES_TABLE =
            "CREATE TABLE " + StoriesTable.TABLE_NAME + "(" +
                    StoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    StoriesTable.COLUMNS_TITLE + " TEXT" + COMMA_SEP +
                    StoriesTable.COLUMNS_GA_PREFIX + " INTEGER" + COMMA_SEP +
                    StoriesTable.COLUMNS_IMAGES + " TEXT" + COMMA_SEP +
                    StoriesTable.COLUMNS_TYPE + " INTEGER" + COMMA_SEP +
                    StoriesTable.COLUMNS_ID + " INTEGER" + COMMA_SEP +
                    StoriesTable.COLUMNS_DATE + " INTEGER" +
                    ")";

    public static abstract class StoriesTable implements BaseColumns {
        public static final String TABLE_NAME = "stories";

        public static final String COLUMNS_TITLE = "title";
        public static final String COLUMNS_GA_PREFIX = "ga_prefix";
        public static final String COLUMNS_IMAGES = "images";
        public static final String COLUMNS_TYPE = "type";
        public static final String COLUMNS_ID = "id";
        public static final String COLUMNS_DATE = "date";
    }
}
