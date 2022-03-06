package com.rui.mvvm.binding


import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 *Created by rui on 2019/8/8
 */
class RvOnListChangedCallback<T : ObservableList<*>> : ObservableList.OnListChangedCallback<T>() {

    var adapter: RecyclerView.Adapter<*>? = null

    override fun onChanged(sender: T) {
        Timber.d("------------>onChanged.sender=")
        adapter?.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(sender: T, positionStart: Int, itemCount: Int) {
        Timber.d("------------>onItemRangeChanged.sender=")
        adapter?.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onItemRangeInserted(sender: T, positionStart: Int, itemCount: Int) {
        Timber.d("------------>onItemRangeInserted.sender=")
        adapter?.notifyItemRangeInserted(positionStart, itemCount)
        //需要添加这个通知立即刷新，否则会出现列表闪动
        adapter?.notifyDataSetChanged()
    }

    override fun onItemRangeMoved(sender: T, fromPosition: Int, toPosition: Int, itemCount: Int) {
        Timber.d("------------>onItemRangeMoved.sender=")
        for (i in 0 until itemCount) {
            adapter?.notifyItemMoved(fromPosition + i, toPosition + i)
        }
    }

    override fun onItemRangeRemoved(sender: T, positionStart: Int, itemCount: Int) {
        Timber.d("------------>onItemRangeRemoved.sender=")
        adapter?.notifyItemRangeRemoved(positionStart, itemCount)
    }

}