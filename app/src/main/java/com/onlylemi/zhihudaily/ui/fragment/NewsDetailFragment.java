package com.onlylemi.zhihudaily.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.contract.NewsDetailContract;
import com.onlylemi.zhihudaily.data.entity.NewsDetail;
import com.onlylemi.zhihudaily.ui.activity.NewsCommentsActivity;
import com.onlylemi.zhihudaily.utils.AppUtils;
import com.onlylemi.zhihudaily.utils.HtmlUtils;
import com.squareup.picasso.Picasso;

/**
 * NewsDetailFragment
 *
 * @author: onlylemi
 * @time: 2016-06-25 17:28
 */
public class NewsDetailFragment extends Fragment implements NewsDetailContract.View {

    private static final String TAG = NewsDetailFragment.class.getSimpleName();

    private NewsDetailContract.Presenter presenter;

    private ImageView newsImage;
    private TextView newsTitle;
    private WebView mWebView;

    private NewsDetail newsDetail;

    private MenuItem menuCommentsNum;
    private MenuItem menuLikesNum;

    public static NewsDetailFragment newInstance() {
        return new NewsDetailFragment();
    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news_detail, container, false);

        newsTitle = (TextView) root.findViewById(R.id.news_detail_title);
        newsImage = (ImageView) root.findViewById(R.id.news_detail_img);
        mWebView = (WebView) root.findViewById(R.id.news_detail_content);

        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        // 设置缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setDomStorageEnabled(true);

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                presenter.share();
                AppUtils.share(getActivity(), newsTitle.getText().toString());
                break;
            case R.id.menu_favorite:
                if (null != newsDetail) {
                    showSnack(presenter.addFavorites(newsDetail) ? "应成功添加到收藏夹！" : "您已收藏过了！");
                }
                break;
            case R.id.menu_comments:
                presenter.openComments();
                break;
            case R.id.menu_likes:
                presenter.addLike();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_detail_fragment_menu, menu);
        menuCommentsNum = menu.findItem(R.id.menu_comments_num);
        menuLikesNum = menu.findItem(R.id.menu_likes_nums);
    }

    @Override
    public void showNewsContent(NewsDetail newsDetail) {
        this.newsDetail = newsDetail;

        newsTitle.setText(newsDetail.getTitle());
        Picasso.with(getActivity())
                .load(newsDetail.getImage())
                .placeholder(R.drawable.test)
                .fit()
                .into(newsImage);
        mWebView.loadData(HtmlUtils.createHtmlData(newsDetail), HtmlUtils.MIME_TYPE,
                HtmlUtils.ENCODING);
    }

    @Override
    public void gotoComments(String newsId) {
        Intent intent = new Intent(getActivity(), NewsCommentsActivity.class);
        intent.putExtra(NewsCommentsActivity.NEWS_ID, newsId);
        startActivity(intent);
    }

    @Override
    public void showCommentsNum(String num) {
        menuCommentsNum.setTitle(num);
    }

    @Override
    public void showLikesNum(String num) {
        menuLikesNum.setTitle(num);
    }

    @Override
    public void showNoContent() {

    }

    public void showSnack(String msg) {
        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
    }
}
