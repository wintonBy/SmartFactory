package com.sf.smartfactory.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sf.smartfactory.R;

/**
 * Created by winton on 2017/7/13.
 * 加载对话框
 */

public class LoadingDialog extends Dialog {
    private Context mContext;
    private TextView mTextView;
    private static LoadingDialog mLoadingDialog;

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_load_dialog,null);
        mTextView = (TextView)view.findViewById(R.id.text);
        setContentView(view);
    }

    /**
     *
     * @param tip
     */
    public void setTip(String tip){
        if(TextUtils.isEmpty(tip)){
            return;
        }
        if(mTextView == null){
            return;
        }
        mTextView.setText(tip);
    }

}
