package com.onlylemi.zhihudaily.data.source.remote;

import android.util.Log;

import com.onlylemi.zhihudaily.data.source.DataManager;
import com.onlylemi.zhihudaily.data.source.DataSource;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * RemoteManager
 *
 * @author: onlylemi
 * @time: 2016-06-27 23:11
 */
public class RemoteManager implements DataSource.Remote {

    private static final String TAG = DataManager.class.getSimpleName();

    private static RemoteManager instance;

    private OkHttpClient client;

    public RemoteManager() {
        client = new OkHttpClient.Builder().build();
    }

    public static RemoteManager getInstance() {
        if (null == instance) {
            instance = new RemoteManager();
        }
        return instance;
    }

    @Override
    public void getEntity(String url, final DataSource.LoadingCallback<String> callback) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse: ");
                if (response.isSuccessful()) {
                    String json_value = response.body().string();
                    Log.i(TAG, "onResponse: " + json_value);
                    callback.onLoaded(json_value);
                }
            }
        });
    }

}
