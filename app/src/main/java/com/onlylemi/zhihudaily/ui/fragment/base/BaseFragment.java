package com.onlylemi.zhihudaily.ui.fragment.base;

import android.support.v4.app.Fragment;

/**
 * BaseFragment
 *
 * @author: onlylemi
 * @time: 2016-06-28 14:46
 */
public class BaseFragment extends Fragment {

    protected UpdateActionBarListener listener;

    public void setUpdateActionBarListener(UpdateActionBarListener listener) {
        this.listener = listener;
    }

    public interface UpdateActionBarListener {
        void updateActionBarTitle(String title);
    }

    @Override
    public void onStop() {
        super.onStop();
        listener = null;
    }
}
