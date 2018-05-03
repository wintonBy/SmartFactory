package com.sf.smartfactory.view;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.sf.smartfactory.R;

public class DialogEx {
    private Context mContext = null;
    private AlertDialog mDialogTip = null;
    public TextView mTextTitle = null;
    private Button mBtnConfirm = null;
    private Button mBtnCancel = null;

    public boolean isShow = false;

    public DialogEx(Context context, String titleRes, int confirmRes, int cancelRes) {
        mContext = context;
        mDialogTip = new AlertDialog.Builder(context).create();
        mDialogTip.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogTip.setCanceledOnTouchOutside(true);
        mDialogTip.show();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ex, null);

        mTextTitle = (TextView) view.findViewById(R.id.dialog_title);
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);

        mTextTitle.setText(titleRes);
        mBtnConfirm.setText(confirmRes);
        String cancelStr = context.getString(cancelRes);
        if (TextUtils.isEmpty(cancelStr)) {
            mBtnCancel.setVisibility(View.GONE);
        } else {
            mBtnCancel.setText(cancelStr);
        }

        mBtnConfirm.setOnClickListener(confirmListener);
        mBtnCancel.setOnClickListener(cancelListener);

        Window window = mDialogTip.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setContentView(view);
        window.setLayout(ConvertUtils.dp2px( 270), ViewGroup.LayoutParams.WRAP_CONTENT);
        isShow = true;
    }

    private OnClickListener confirmListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            confirm();
        }
    };

    private OnClickListener cancelListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            cancel();
        }
    };

    public void show() {
        isShow = true;
        mDialogTip.show();
    }

    public void hide() {
        isShow = false;
        mDialogTip.dismiss();
    }

    public void confirm() {
        hide();
    }

    public void cancel() {
        hide();
    }
}
