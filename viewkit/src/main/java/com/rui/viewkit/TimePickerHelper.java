package com.rui.viewkit;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;


/**
 * Created by 0200030 on 2017/7/3.
 */

public class TimePickerHelper {

    public static String CURRENT_TIME = "current_time";
    public static String DEFUALT_START_TIME = "1900-01-01";
    public static String DEFUALT_END_TIME = "2100-12-31";

    private static TimePickerView pvTime;

    /**
     * 直接用默认的样式
     *
     * @param activity
     * @param listener
     */
    public static void getTimePickerDialog(Activity activity, OnTimeSelectListener listener) {
        TimePickerView pvTime = new TimePickerBuilder(activity, listener).build();
        pvTime.show();
    }


    /*
     * 时间选择器
     *
     * @param activity
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param selectedDate 当前默认选中时间
     * @param styles       YMDHMS的弹出样式的数组
     * @param view         弹出点击的view
     * @param listener
     */
    public static void getTimePickerDialog(Activity activity
            , Calendar startDate
            , Calendar endDate
            , Calendar selectedDate
            , boolean[] styles
            , View view
            , OnTimeSelectListener listener) {

        pvTime = new TimePickerBuilder(activity, listener)
                .setType(styles)// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("Title")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        if (view != null) {
            pvTime.show(view);
        } else {
            pvTime.show();
        }
        pvTime.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
//                LogUtils.d("TAG", "----->onDismiss");
                pvTime = null;
            }
        });
    }


}
