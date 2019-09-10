package com.rui.viewkit;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class GridSpacingItemDecoration extends ItemDecoration {
    /**
     * grid的列数
     */
    private int spanCount;
    /**
     * 间距
     */
    private int spacing;
    /**
     * 是否包括两边
     */
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        int position = parent.getChildAdapterPosition(view);
        //计算出position位于第几列，从0开始
        int column = position % this.spanCount;
        if (this.includeEdge) {
            outRect.left = this.spacing - column * this.spacing / this.spanCount;
            outRect.right = (column + 1) * this.spacing / this.spanCount;
            if (position < this.spanCount) {
                outRect.top = 0;
            } else {
                outRect.top = this.spacing;
            }

//            outRect.bottom = this.spacing;
        } else {
            //第二列左边paddin的占spacing中的1/spanCount，其他以此类推
            outRect.left = column * this.spacing / this.spanCount;
            //第一列右边边paddin的占spacing中的2/spanCount，其他以此类推
            outRect.right = this.spacing - (column + 1) * this.spacing / this.spanCount;
            if (position < this.spanCount) {
                //第一行不需要paddingtop
                outRect.top = 0;
            } else {
                outRect.top = this.spacing;
            }

//            outRect.bottom = this.spacing;
        }

    }
}
