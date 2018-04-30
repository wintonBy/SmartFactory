package com.sf.smartfactory;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.Utils;
import com.sf.smartfactory.aspectj.annotation.DebugTrace;
import com.sf.smartfactory.ui.activity.LoginActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by winton on 2017/6/25.
 */

public class MyApplication extends Application {

    public static MyApplication INSTANCE;

    private List<WeakReference<Activity>> mActivitys;

    public static PermissionUtils permissionInstance;

    @DebugTrace
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initUtils();
        initPermission();
    }

    /**
     * 初始化工具类
     */
    @DebugTrace
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

    /**
     * 初始化程序需要的权限
     */
    private void initPermission(){
        permissionInstance = PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

}
