package com.rui.viewkit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * 提交时弹出的对话框 2018/7/16
 */
public class ChooseDialog extends Dialog implements View.OnClickListener {
    private TextView tvSearch;
    private TextView tvScan;

    private DialogClickListener listener;

    public ChooseDialog(Context context) {
        super(context, R.style.IsDelDialog);
    }

    public static ChooseDialog createDialog(Context context, DialogClickListener listener) {
        ChooseDialog dlg = new ChooseDialog(context);
        dlg.listener = listener;
        dlg.show();
        return dlg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_dialog);
        setCanceledOnTouchOutside(true);
        tvScan = findViewById(R.id.tv_scan);
        tvSearch = findViewById(R.id.tv_search);
        tvScan.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (listener != null) {
            int id = v.getId();
            if (id == R.id.tv_scan) {
                listener.onClick(v, 0);
            } else if (id == R.id.tv_search) {
                listener.onClick(v, 1);
            }
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
