package com.onlylemi.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.onlylemi.zhihudaily.App;
import com.onlylemi.zhihudaily.Injection;
import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.presenter.NewsDetailPresenter;
import com.onlylemi.zhihudaily.ui.fragment.NewsDetailFragment;
import com.onlylemi.zhihudaily.utils.ActivityUtils;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String NEWS_ID = "NEWS_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        String newsId = getIntent().getStringExtra(NEWS_ID);

        // 设置toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.daily_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.news_contentFrame);
        if (null == fragment) {
            fragment = NewsDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment,
                    R.id.news_contentFrame);
        }
        new NewsDetailPresenter(newsId, Injection.provideDataManager(App.getContext()),
                (NewsDetailFragment) fragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
