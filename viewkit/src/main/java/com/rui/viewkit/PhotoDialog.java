package com.rui.viewkit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class PhotoDialog extends Dialog implements View.OnClickListener {

    private Button takePhoto;
    private Button choosePhoto;
    private Button chooseVideo;
    private Button cancel;
    private boolean hideVideo;

    private PhotoDialog.DialogClickListener listener;

    public PhotoDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
    }


    public static PhotoDialog createDialog(Context context,boolean hideVideo,
                                           PhotoDialog.DialogClickListener listener) {
        PhotoDialog dlg = new PhotoDialog(context);
        dlg.setCancelable(true);
        dlg.listener = listener;
        dlg.hideVideo = hideVideo;
        dlg.show();
        return dlg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        takePhoto = findViewById(R.id.takePhoto);
        choosePhoto = findViewById(R.id.choosePhoto);
        chooseVideo = findViewById(R.id.chooseVideo);
        chooseVideo.setVisibility(hideVideo ? View.GONE : View.VISIBLE);
        cancel = findViewById(R.id.btn_cancel);
        takePhoto.setOnClickListener(this);
        choosePhoto.setOnClickListener(this);
        chooseVideo.setOnClickListener(this);
        cancel.setOnClickListener(this);
        Window dialogWindow = getWindow();
        if (dialogWindow == null) {
            return;
        }
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(true);

    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (listener != null) {
            listener.onClick(v, 0);
        }
    }

    public interface DialogClickListener {
        /**
         * @param v        点击的view
         * @param position left按键返回0(例如取消)，right按键返回1(例如确认),暂时不用
         */
        void onClick(View v, int position);
    }


}
