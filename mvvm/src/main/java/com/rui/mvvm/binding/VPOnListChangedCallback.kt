package com.rui.mvvm.binding

import androidx.databinding.ObservableList
import androidx.viewpager.widget.PagerAdapter
import javax.inject.Inject

/**
 * Created by rui on 2019/9/12
 */
@Deprecated("Will remove")
class VPOnListChangedCallback<T : ObservableList<*>> @Inject constructor() :
    ObservableList.OnListChangedCallback<T>() {

    var adapter: PagerAdapter? = null

    override fun onChanged(sender: T) {
        //            Timber.d("------------>onChanged.sender=" + sender.size());
        adapter?.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(sender: T, positionStart: Int, itemCount: Int) {
        //            Timber.d("------------>onChanged.onItemRangeChanged=" + sender.size());
        adapter?.notifyDataSetChanged()
    }

    override fun onItemRangeInserted(sender: T, positionStart: Int, itemCount: Int) {
        //            Timber.d("------------>onChanged.onItemRangeInserted=" + sender.size());
        adapter?.notifyDataSetChanged()
    }

    override fun onItemRangeMoved(sender: T, fromPosition: Int, toPosition: Int, itemCount: Int) {
        //            Timber.d("------------>onChanged.onItemRangeMoved=" + sender.size());
        adapter?.notifyDataSetChanged()
    }

    override fun onItemRangeRemoved(sender: T, positionStart: Int, itemCount: Int) {
        //            Timber.d("------------>onChanged.onItemRangeRemoved=" + sender.size());
        adapter?.notifyDataSetChanged()
    }
}