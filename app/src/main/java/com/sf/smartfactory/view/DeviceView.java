package com.sf.smartfactory.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author: winton
 * @time: 2018/5/3 20:37
 * @package: com.sf.smartfactory.view
 * @project: SmartFactory
 * @mail:
 * @describe: 设备
 */
public class DeviceView extends RelativeLayout {
    public DeviceView(Context context) {
        this(context,null);

    }

    public DeviceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DeviceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){

    }
}
