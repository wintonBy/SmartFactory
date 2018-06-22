package com.sf.smartfactory.network.subscriber;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.bean.RunTimeSummary;
import com.sf.smartfactory.network.bean.Status;
import com.sf.smartfactory.network.response.QuickTimeResponse;
import com.sf.smartfactory.network.response.TimeResponse;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author: winton
 * @time: 2018/6/22 10:44
 * @package: com.sf.smartfactory.network.subscriber
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public abstract class QuickSubscriber extends BaseSubscriber<QuickTimeResponse> {

    private Gson gson;

    public QuickSubscriber(){
        gson = new Gson();
    }


    @Override
    public void success(QuickTimeResponse quickTimeResponse) {
        if(quickTimeResponse.getData() == null){
            failed(new Exception("数据异常"));
            return;
        }
        onSummary(quickTimeResponse.getData().getSummary());
        onDeviceValues(quickTimeResponse.getData().getDeviceValues());
        onStatus(quickTimeResponse.getData().getList());
    }
    @Override
    abstract public void failed(Throwable e);



    abstract public void onSummary(RunTimeSummary summary);

    abstract public void onStatus(List<Status> list);

    abstract public void onDeviceValues(List<TimeResponse.Device> list);
}
