package com.onlylemi.zhihudaily.contract.base;

/**
 * BaseView
 *
 * @author: onlylemi
 * @time: 2016-06-23 11:19
 */
public interface BaseView<T> {

    /**
     * 为 view 设置一个对应的 presenter
     *
     * @param presenter
     */
    void setPresenter(T presenter);
}
