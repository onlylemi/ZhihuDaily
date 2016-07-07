package com.onlylemi.zhihudaily;

import android.app.Application;
import android.content.Context;

/**
 * App
 *
 * @author: onlylemi
 * @time: 2016-06-23 13:29
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
