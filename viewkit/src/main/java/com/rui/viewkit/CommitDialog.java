package com.rui.viewkit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


/**
 * 提交时弹出的对话框 2018/7/16
 */
public class CommitDialog extends Dialog implements View.OnClickListener {
    TextView tvAsk;
    TextView tvList;
    TextView BtnLeft;
    TextView BtnRight;

    private DialogClickListener listener;
    private CharSequence list;
    private CharSequence askText;
    private boolean isLeftHide;

    public CommitDialog(Context context) {
        super(context, R.style.IsDelDialog);
    }

    public static CommitDialog createDialog(Context context
            , CharSequence desc
            , CharSequence title
            , DialogClickListener listener
    ) {
        CommitDialog dlg = new CommitDialog(context);
        dlg.listener = listener;
        dlg.list = desc;
        dlg.askText = title;
        dlg.show();
        return dlg;
    }

    public static CommitDialog createTipDialog(Context context
            , CharSequence desc
            , CharSequence title
//            , boolean isLeftHide
//            , DialogClickListener listener
    ) {
        CommitDialog dlg = new CommitDialog(context);
//        dlg.listener = listener;
        dlg.list = desc;
        dlg.askText = title;
        dlg.isLeftHide = true;
        dlg.show();
        return dlg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_upload_dialog);
        setCanceledOnTouchOutside(false);
        tvAsk = findViewById(R.id.tv_ask);
        tvList = findViewById(R.id.tv_list);
        BtnLeft = findViewById(R.id.Btn_left);
        BtnRight = findViewById(R.id.Btn_right);
        setMessage();
    }

    public void setMessage() {
        BtnLeft.setOnClickListener(this);
        BtnRight.setOnClickListener(this);
        tvList.setVisibility(TextUtils.isEmpty(list) ? View.GONE : View.VISIBLE);
        tvAsk.setVisibility(TextUtils.isEmpty(askText) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(list)) {
            tvList.setText(list);
        }
        if (!TextUtils.isEmpty(askText)) {
            tvAsk.setText(askText);
        }

        if (isLeftHide) {
            tvList.setVisibility(View.GONE);
            BtnLeft.setVisibility(View.GONE);
            BtnRight.setBackground(getContext().getResources().getDrawable(R.drawable.information_item_rightc));
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();
        int id = v.getId();
        int pos = 0;
        if (id == R.id.Btn_left) {
            pos = 0;
        } else if (id == R.id.Btn_right) {
            pos = 1;
        }
        if (listener != null) {
            listener.onClick(v, pos);
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
