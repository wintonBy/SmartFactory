package com.sf.smartfactory.ui.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sf.smartfactory.R;

import butterknife.ButterKnife;

/**
 * @author: winton
 * @time: 2018/5/8 18:55
 * @package: com.sf.smartfactory.ui.fragment
 * @project: SmartFactory
 * @mail:
 * @describe: 更新频率选择框
 */
public class UpdateFrequencyDialog extends DialogFragment{

    public static UpdateFrequencyDialog createInstance(){
        UpdateFrequencyDialog instance = new UpdateFrequencyDialog();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme =android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style,theme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frequency_choose_dialog,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public interface OnItemChooseListener{
        /**
         *
         * @param index
         * @param title
         */
        void choose(int index,String title);
    }
}
