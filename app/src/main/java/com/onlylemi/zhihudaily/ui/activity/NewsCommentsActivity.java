package com.onlylemi.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.onlylemi.zhihudaily.App;
import com.onlylemi.zhihudaily.Injection;
import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.presenter.NewsCommentsPresenter;
import com.onlylemi.zhihudaily.ui.activity.base.BaseActivity;
import com.onlylemi.zhihudaily.ui.fragment.NewsCommentsFragment;
import com.onlylemi.zhihudaily.utils.ActivityUtils;

public class NewsCommentsActivity extends BaseActivity {

    public static final String NEWS_ID = "NEWS_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_comments);

        String newsId = getIntent().getStringExtra(NEWS_ID);

        // 设置toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.daily_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        NewsCommentsFragment fragment = (NewsCommentsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.comments_contentFrame);
        if (null == fragment) {
            fragment = NewsCommentsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment,
                    R.id.comments_contentFrame);
        }
        new NewsCommentsPresenter(Injection.provideDataManager(App.getContext()), fragment, newsId);

        fragment.setUpdateActionBarListener(new NewsCommentsFragment.UpdateActionBarListener() {
            @Override
            public void updateActionBarTitle(String title) {
                getSupportActionBar().setTitle(title);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
