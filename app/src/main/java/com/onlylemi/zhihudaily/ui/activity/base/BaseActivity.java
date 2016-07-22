package com.onlylemi.zhihudaily.ui.activity.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.onlylemi.zhihudaily.utils.AppUtils;

/**
 * 滑动返回
 *
 * @author: onlylemi
 * @time: 2016-07-22 20:04
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private View decorView;

    private float startTouchX;
    private int screenW;

    private boolean isSliding = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decorView = getWindow().getDecorView();
        screenW = AppUtils.screeWidth(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startTouchX = event.getX();
            if (startTouchX < 50) {
                isSliding = true;
                return onTouchEvent(event);
            }
        }
        if (isSliding) {
            return onTouchEvent(event);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() == startTouchX) {
                isSliding = false;
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: x=" + event.getX());

        if (isSliding) {
            float moveDistX = event.getX() - startTouchX;

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    if (moveDistX > 0) {
                        decorView.setX(moveDistX);
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    if (moveDistX > screenW / 2) {
                        finish(moveDistX);
                    } else {
                        ObjectAnimator.ofFloat(decorView, "x", 0).start();
                    }
                    isSliding = false;
                    break;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void finish(float dist) {
        ValueAnimator animator = ValueAnimator.ofFloat(dist, screenW);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue();
                decorView.setX(x);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }
        });
    }
}
