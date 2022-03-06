package com.rui.viewkit;

import android.view.View;

import androidx.databinding.BindingAdapter;

/**
 * Created by rui on 2018/11/15
 */
public class ViewKitBinding {

    @BindingAdapter("app:tvRightOnclickListener")
    public static void setTvRightOnclickListener(HeadBar headBar, View.OnClickListener listener) {
        if (listener != null) {
            headBar.setTvRightOnclickListener(listener);
        }
    }

    @BindingAdapter("app:tvRightVisible")
    public static void setTvRightVisible(HeadBar headBar, boolean isVisible) {
        headBar.setTvRightVisible(isVisible);
    }
}


