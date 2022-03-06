package com.rui.kotlin_mvvm.ui.main.activity

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import javax.inject.Inject

/**
 *Created by rui on 2019/9/11
 */
class FgPagerAdapter @Inject constructor(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    var fragmentList: List<Fragment> = ArrayList()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemPosition(any: Any): Int {
        super.getItemPosition(any)
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getItem(position).arguments?.getString("title") ?: ""
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        super.destroyItem(container, position, any)
    }

}