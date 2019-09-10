package com.rui.viewkit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 提交时弹出的对话框 2018/7/16
 */
public class InputDialog extends Dialog implements View.OnClickListener {

    private EditText etInput;
    private TextView tvCancel;
    private TextView tvEnsure;
    private DialogClickListener listener;


    public InputDialog(Context context) {
        super(context, R.style.IsDelDialog);
    }

    public static InputDialog createDialog(Context context, DialogClickListener listener) {
        InputDialog dlg = new InputDialog(context);
        dlg.listener = listener;
        dlg.show();
        return dlg;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);
        setCanceledOnTouchOutside(false);
        etInput = findViewById(R.id.et_input);
        tvCancel = findViewById(R.id.tv_cancel);
        tvEnsure = findViewById(R.id.tv_ensure);
        tvCancel.setOnClickListener(this);
        tvEnsure.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel) {
            closeKeyboard();
        } else if (id == R.id.tv_ensure) {
            if (listener != null) {
                String trim = etInput.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Toast.makeText(getContext(), "请输入会员手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                closeKeyboard();
                listener.onClick(v, trim);
            }
        }
    }

    private void closeKeyboard() {
        dismiss();
//        View view = getWindow().peekDecorView();
//        if (view != null) {
//            InputMethodManager inputmanger = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
    }


    @Override
    public void dismiss() {
        View view = getCurrentFocus();
        if(view instanceof TextView){
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.dismiss();
    }


    public interface DialogClickListener {
        /**
         * @param v
         * @param inputStr
         */
        void onClick(View v, String inputStr);
    }

}
