package com.rui.viewkit;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by rui on 2018/9/6
 */
public class PopupListWindow {

    private OnItemClickListener onItemClickListener;
    private FragmentActivity activity;
    private BaseQuickAdapter adapter;
    private PopupWindow popupWindow;

    public PopupListWindow(FragmentActivity activity, BaseQuickAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
        initView();
    }

    private void initView() {
        View popView = activity.getLayoutInflater().inflate(R.layout.menu_popup, null);
        RecyclerView rvData = popView.findViewById(R.id.rv_data);
        rvData.setLayoutManager(new LinearLayoutManager(activity));
        rvData.setAdapter(adapter);
        // popView即popupWindow布局
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 必须设计BackgroundDrawable后setOutsideTouchable(true)才会有效。这里在XML中定义背景，所以这里为null
        popupWindow.setBackgroundDrawable(new ColorDrawable(0000000000));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true); // 点击外部关闭
//        popupWindow.setAnimationStyle(R.style.popmenu_animation);
        popupWindow.setAnimationStyle(R.style.popmenu_animation);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                popupWindow.dismiss();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(adapter, view, position);
                }
            }
        });
    }

    public void showPopup(View view) {
        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        // 状态栏高度 frame.top
        int yOffset = frame.top + view.getHeight() + dp2px(activity, -1f); // 减去阴影宽度，适配UI
        int xOffset = dp2px(activity, 0f); // 设置x方向offset为5dp

        // 设置Gravity,让它显示在右上角
        popupWindow.showAtLocation(view, Gravity.RIGHT | Gravity.TOP, xOffset, yOffset);

    }

    /**
     * Value of dp to value of px.
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    public int dp2px(Context context, final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public interface OnItemClickListener {
        void onItemClick(BaseQuickAdapter adapter, View view, int position);
    }

}
