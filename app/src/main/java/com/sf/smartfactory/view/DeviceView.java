package com.sf.smartfactory.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sf.smartfactory.R;

/**
 * @author: winton
 * @time: 2018/5/3 20:37
 * @package: com.sf.smartfactory.view
 * @project: SmartFactory
 * @mail:
 * @describe: 设备
 */
public class DeviceView extends RelativeLayout {

    private String status = "status";

    private String id = "id";

    private String type = "type";

    private long duration;

    private TextView tvStatus;

    private TextView tvId;

    private TextView tvType;

    private ImageView ivDevice;

    private int deviceSize = 40;


    public DeviceView(Context context) {
        this(context,null);

    }

    public DeviceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DeviceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        initImg();
        initId();
        initType();
        initStatus();

    }

    /**
     * 设备图片
     */
    private void initImg(){
        ivDevice = new ImageView(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(deviceSize,deviceSize);
        layoutParams.addRule(CENTER_IN_PARENT);
        this.addView(ivDevice,layoutParams);
        setImg(R.mipmap.ic_logo);
    }

    private void initId(){
        tvId = new TextView(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_TOP);
        layoutParams.addRule(ALIGN_PARENT_LEFT);
        tvId.setSingleLine(true);
        this.addView(tvId,layoutParams);
        setId(id);
    }

    private void initType(){
        tvType = new TextView(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_TOP);
        layoutParams.addRule(LEFT_OF,tvId.getId());
        tvType.setSingleLine(true);
        this.addView(tvType,layoutParams);
        setType(type);
    }

    private void initStatus(){
        tvStatus = new TextView(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(CENTER_HORIZONTAL);
        this.addView(tvStatus,layoutParams);
        tvStatus.setSingleLine(true);
        setStatus(status);
    }

    public void setImg(int resId){
        if(ivDevice != null){
            ivDevice.setImageResource(resId);
        }
    }

    public void setImg(Drawable drawable){
        if(ivDevice != null){
            ivDevice.setImageDrawable(drawable);
        }
    }

    public void setStatus(String status){
        this.status = status;
        if(tvStatus != null){
            tvStatus.setText(status);
        }
    }

    public void setId(String id){
        this.id = id;
        if(tvId != null){
            tvId.setText(id);
        }
    }
    public void setType(String type){
        this.type = type;
        if(tvType != null){
            tvType.setText(type);
        }
    }

}
