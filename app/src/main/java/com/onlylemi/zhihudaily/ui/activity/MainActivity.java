package com.onlylemi.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.onlylemi.zhihudaily.App;
import com.onlylemi.zhihudaily.Injection;
import com.onlylemi.zhihudaily.R;
import com.onlylemi.zhihudaily.presenter.DailyPresenter;
import com.onlylemi.zhihudaily.presenter.FavoritesPresenter;
import com.onlylemi.zhihudaily.ui.fragment.DailyFragment;
import com.onlylemi.zhihudaily.ui.fragment.FavoritesFragment;
import com.onlylemi.zhihudaily.ui.fragment.base.BaseFragment;
import com.onlylemi.zhihudaily.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.daily_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 设置侧滑
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.today_daily_item:
                                    openDailyFragment();
                                    break;
                                case R.id.favorites_item:
                                    openFavoritesFragment();
                                    break;
                                default:
                                    break;
                            }
                            drawerLayout.closeDrawers();
                            return true;
                        }
                    });
        }

        // 设置fragment
        DailyFragment dailyFragment = (DailyFragment) getSupportFragmentManager()
                .findFragmentById(R.id.daily_contentFrame);
        if (null == dailyFragment) {
            dailyFragment = DailyFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), dailyFragment, R.id
                    .daily_contentFrame);
        }

        new DailyPresenter(dailyFragment, Injection.provideDataManager(App.getContext()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 打开首页
     */
    public void openDailyFragment() {
        DailyFragment dailyFragment = DailyFragment.newInstance();
        ActivityUtils.replaceFragmentToActivity
                (getSupportFragmentManager(), dailyFragment, R.id
                        .daily_contentFrame);
        new DailyPresenter(dailyFragment, Injection
                .provideDataManager(App.getContext()));

        getSupportActionBar().setTitle(R.string.app_name);
    }

    /**
     * 打开收藏页
     */
    public void openFavoritesFragment() {
        FavoritesFragment favoritesFragment = FavoritesFragment
                .newInstance();
        ActivityUtils.replaceFragmentToActivity
                (getSupportFragmentManager(), favoritesFragment, R.id
                        .daily_contentFrame);
        new FavoritesPresenter(Injection.provideDataManager(App
                .getContext()), favoritesFragment);

        favoritesFragment.setUpdateActionBarListener(new BaseFragment.UpdateActionBarListener() {
            @Override
            public void updateActionBarTitle(String title) {
                getSupportActionBar().setTitle(title);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time > 2000) {
            Toast.makeText(this, "再按一次 退出应用！", Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
