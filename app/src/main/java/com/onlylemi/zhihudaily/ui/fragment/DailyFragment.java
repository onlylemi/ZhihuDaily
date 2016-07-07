package com.onlylemi.zhihudaily.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.contract.DailyContract;
import com.onlylemi.zhihudaily.data.entity.Daily;
import com.onlylemi.zhihudaily.ui.activity.NewsDetailActivity;
import com.onlylemi.zhihudaily.ui.adapter.DailyAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * DailyFragment
 *
 * @author: onlylemi
 * @time: 2016-06-23 16:27
 */
public class DailyFragment extends Fragment implements DailyContract.View {

    private static final String TAG = DailyFragment.class.getSimpleName();

    private DailyContract.Presenter presenter;
    private DailyAdapter dailyAdapter;

    private TextView dailyHint;

    private SliderLayout banner;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    @Override
    public void setPresenter(DailyContract.Presenter presenter) {
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
        dailyAdapter = new DailyAdapter(new ArrayList<Daily.StoriesBean>(0));
        // 添加menu
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_daily, container, false);

        // 置顶日报
        banner = (SliderLayout) root.findViewById(R.id.daily_top_news_banner);
        banner.setDuration(2000);

        // 日报列表
        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id
                .daily_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewHeader header = (RecyclerViewHeader) root.findViewById(R.id
                .daily_recycler_view_header);
        header.attachTo(recyclerView, true);

        recyclerView.setAdapter(dailyAdapter);

        dailyAdapter.setOnItemClickListener(new DailyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.openNews(dailyAdapter.getItem(position).getId() + "");
            }
        });

        // 选择日期
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab_select_daily);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDateDaily();
            }
        });

        // 日报小提示
        dailyHint = (TextView) root.findViewById(R.id.daily_hint);

        // 下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id
                .daily_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadingLatestDaily();
            }
        });

        return root;
    }

    /**
     * 展示选择日报日期
     */
    private void showSelectDateDaily() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog
                .OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                presenter.loadingDaily(time);
            }
        }, year, month, day);
        dialog.show();
    }


    @Override
    public void setLoadingIndicator(final boolean active) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showDailyNews(List<Daily.StoriesBean> list, String msg) {
        dailyHint.setText(msg);
        dailyAdapter.bindList(list);
        dailyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTopNews(final List<Daily.TopStoriesBean> list) {
        if (banner.getChildCount() != 0) {
            banner.removeAllSliders();
        }
        for (int i = 0; i < list.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(list.get(i).getTitle())
                    .image(list.get(i).getImage());
            final int finalI = i;
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    presenter.openNews(list.get(finalI).getId() + "");
                }
            });
            banner.addSlider(textSliderView);
        }
    }

    @Override
    public void gotoNewsDetails(String newsId) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.NEWS_ID, newsId);
        startActivity(intent);
    }

    @Override
    public void showNoDaily() {

    }

    @Override
    public void onStop() {
        banner.stopAutoCycle();
        super.onStop();
    }
}
