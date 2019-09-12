/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rui.common.binding

import android.databinding.BindingAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rui.common.ImageLoader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import timber.log.Timber


/**
 * ViewPager绑定
 *
 * @param viewPager
 * @param fgPagerAdapter
 */
@BindingAdapter(value = ["app:vpAdapter"], requireAll = false)
fun setVpAdapter(viewPager: ViewPager, fgPagerAdapter: PagerAdapter) {
    val oldAdapter = viewPager.adapter
    if (oldAdapter !== fgPagerAdapter) {
        viewPager.adapter = fgPagerAdapter
    }
}

/**
 * ViewPager绑定当前的页面
 *
 * @param viewPager
 */
@BindingAdapter(value = ["app:vpCurrentPos"])
fun setVpCurrentPos(viewPager: ViewPager, vpCurrentPos: Int) {
    Timber.d("---------->setVpCurrentPos.vpCurrentPos=>$vpCurrentPos")
    viewPager.currentItem = vpCurrentPos
}

/**
 * RecyclerView绑定,这个绑定方式已经被放弃使用
 *
 * @param recyclerView
 * @param items
 * @param adapter
 * @param layoutManager
 */
@BindingAdapter(
    value = ["app:rvItems", "app:rvAdapter", "app:rvLayoutManager", "app:rvDecoration"],
    requireAll = false
)
fun setRvItems(
    recyclerView: RecyclerView,
    items: List<Any?>?,
    adapter: BaseQuickAdapter<Any?, *>?,
    layoutManager: RecyclerView.LayoutManager?,
    decoration: RecyclerView.ItemDecoration?
) {
    val oldAdapter = recyclerView.adapter
    if (oldAdapter !== adapter) {
        //            Timber.d("---------->setRvItems=>");
        if (adapter != null && items != null) adapter.setNewData(items)
        if (decoration != null) recyclerView.addItemDecoration(decoration)
        if (layoutManager != null) recyclerView.layoutManager = layoutManager
        if (adapter != null) recyclerView.adapter = adapter
    }
}

/**
 * Glide加载图片
 *
 * @param imageView
 * @param url
 * @param timeStamp
 */
@BindingAdapter(value = ["app:ivUrl", "app:ivTimeStamp"], requireAll = false)
fun setImageUri(imageView: ImageView, url: String, timeStamp: Long) {
    ImageLoader.displayImage(
        imageView.context,
        if (!TextUtils.isEmpty(url)) url else null,
        imageView,
        timeStamp
    )
}

/**
 * Glide加载图片
 *
 * @param imageView
 * @param url
 * @param timeStamp
 */
@BindingAdapter(value = ["app:ivHeadUrl", "app:ivTimeStamp"], requireAll = false)
fun setImageHeadUri(imageView: ImageView, url: String, timeStamp: Long) {
    ImageLoader.displayHeadImage(imageView.context, url, imageView)
}

/**
 * View类的OnKeyListener绑定，
 * 因为在ViewBindingAdapter这个databinding类库中没有去实现这个方法
 *
 * @param view
 * @param listener
 */
@BindingAdapter("android:onKey")
fun setOnKeyListener(view: View, listener: View.OnKeyListener) {
    view.setOnKeyListener(listener)
}

/**
 * 监听check状态的变化
 */
@BindingAdapter("android:onCheckedChange")
fun setOnCheckedChangeListener(
    view: CompoundButton,
    listener: CompoundButton.OnCheckedChangeListener
) {
    view.setOnCheckedChangeListener(listener)
}


@BindingAdapter("android:src")
fun setSrc(view: ImageView, resId: Int) {
    view.setImageResource(resId)
}

@BindingAdapter("android:text")
fun setText(view: TextView, text: Int) {
    view.text = text.toString() + ""
}

//    @BindingAdapter("android:text")
//    public static void setText(EditText view, double text) {
//        String str = String.format("%.02f", text);
//        view.setText(str);
//        view.setSelection(str.length());
//    }

@BindingAdapter("app:etselection")
fun setText(view: EditText, selection: Int) {
    Timber.d("----------->selection=$selection")
    view.requestFocus()
    view.setSelection(selection)
}

//    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
//    public static double getTextString(TextView view) {
//        try {
//            double aDouble = Double.parseDouble(view.getText().toString());
//            return aDouble;
//        } catch (Exception e) {
//            return 0.00;
//        }
//    }

/**
 * 下拉 上拉 加载效果完成
 *
 * @param smartRefreshLayout
 * @param finishRefreshing
 * @param finishLoadmore
 */
@BindingAdapter(
    value = ["app:finishRefreshing", "app:finishLoadmore", "app:loadNoMoreData"],
    requireAll = false
)
fun setFinishRefreshing(
    smartRefreshLayout: SmartRefreshLayout,
    finishRefreshing: Boolean,
    finishLoadmore: Boolean,
    loadNoMoreData: Boolean
) {
    if (finishLoadmore) {
        smartRefreshLayout.finishLoadMore()
    }
    if (finishRefreshing) {
        smartRefreshLayout.finishRefresh()
    }
    if (loadNoMoreData) {
        smartRefreshLayout.finishLoadMoreWithNoMoreData()
    }
}
