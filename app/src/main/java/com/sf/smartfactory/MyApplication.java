package com.sf.smartfactory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.ui.activity.LoginActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by winton on 2017/6/25.
 */

public class MyApplication extends Application {

    public static MyApplication INSTANCE;

    private List<WeakReference<Activity>> mActivitys;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initUtils();
        Constant.REFRESH_SPACE = SPUtils.getInstance().getInt(Constant.SP_UPDATE_FREQUENCY_VALUE,Constant.REFRESH_SPACE);
    }

    /**
     * 初始化工具类
     */
    private void initUtils(){

        Utils.init(this);
        /*全局异常捕获*/
        CrashUtils.init();
    }

    public void addActivity(@NonNull WeakReference<Activity> activity){
        if(mActivitys == null){
            mActivitys = new ArrayList<>();
        }
        mActivitys.add(activity);
    }

    public void removeActivity(@NonNull WeakReference<Activity> activity){
        if(mActivitys == null){
            return;
        }
        if(mActivitys.contains(activity)){
            mActivitys.remove(activity);
        }
    }

    /**
     * 安全退出应用
     */
    public void exit(){
        if(mActivitys != null){
            for (WeakReference<Activity> activityWeakReference : mActivitys){
                activityWeakReference.get().finish();
            }
        }
    }

    /**
     * 跳转到登录页
     */
    public void toLogin(){
        Activity topActivity = ActivityUtils.getTopActivity();
        if(mActivitys != null){
            mActivitys.remove(topActivity);
        }
        exit();
        Context context = topActivity == null? this : topActivity;
        LoginActivity.start(context,null);
        if(context instanceof Activity){
            ((Activity) context).finish();
        }
    }
}
