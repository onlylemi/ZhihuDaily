package com.onlylemi.zhihudaily;

import android.content.Context;

import com.onlylemi.zhihudaily.data.source.DataManager;
import com.onlylemi.zhihudaily.data.source.local.DBManager;
import com.onlylemi.zhihudaily.data.source.remote.RemoteManager;

/**
 * Injection
 *
 * @author: onlylemi
 * @time: 2016-06-24 16:27
 */
public class Injection {

    public static DataManager provideDataManager(Context context) {
        return DataManager.getInstance(DBManager.getInstance(context), RemoteManager.getInstance());
    }

}
