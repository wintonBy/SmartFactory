package com.sf.smartfactory.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.event.UpdateDataEvent;
import com.wasu.iutils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @author: winton
 * @time: 2018/4/9 15:46
 * @package: com.sf.smartfactory
 * @project: SmartFactory
 * @mail:
 * @describe: 后台刷新服务
 */
public class UpdateDataService extends IntentService {
    private final String TAG = UpdateDataService.class.getSimpleName();
    private boolean isRunning = false;

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        isRunning = true;
        LogUtils.dTag(TAG,"onStart");
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        LogUtils.dTag(TAG,"onDestroy");
        super.onDestroy();
    }
    public UpdateDataService() {
        super("UpdateDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogUtils.dTag(TAG,"onHandleIntent");
        while (isRunning()){
            EventBus.getDefault().post(new UpdateDataEvent());
            try {
                Thread.sleep(Constant.REFRESH_SPACE);
            }catch (InterruptedException e){
                LogUtils.eTag(TAG,"异常",e);
            }
        }
        LogUtils.dTag(TAG,"onHandleIntent finish");
    }

    private synchronized boolean isRunning(){
        return isRunning;
    }

}
