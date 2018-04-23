package com.sf.smartfactory.presenter;

import com.sf.smartfactory.contract.DeviceDetailContract;
import com.sf.smartfactory.event.DeviceOeeEvent;
import com.sf.smartfactory.event.DeviceRateEvent;
import com.sf.smartfactory.event.DeviceTimeEvent;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.RunTimeSummary;
import com.sf.smartfactory.network.response.LastStatusResponse;
import com.sf.smartfactory.network.response.OEEResponse;
import com.sf.smartfactory.network.response.RunTimeSummaryResponse;
import com.sf.smartfactory.network.response.TimeResponse;
import com.sf.smartfactory.ui.activity.DeviceDetailActivity;
import com.wasu.iutils.ObjectUtils;
import com.wasu.iutils.StringUtils;
import com.wasu.iutils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @author: winton
 * @time: 2018/4/7 23:27
 * @package: com.sf.smartfactory.presenter
 * @project: SmartFactory
 * @mail:
 * @describe: 设备详情Presenter
 */
public class DeviceDetailPresenter extends BasePresenter<DeviceDetailActivity> implements DeviceDetailContract.Presenter {


    @Override
    public void loadLastStatus(String deviceId) {
        if(StringUtils.isTrimEmpty(deviceId)){
            return;
        }
        RetrofitClient.getInstance().lastStatusOne(deviceId,new BaseSubscriber<LastStatusResponse>(){
            @Override
            public void onNext(LastStatusResponse lastStatusResponse) {
                super.onNext(lastStatusResponse);
                if(lastStatusResponse.isSuccess()){
                    getView().setDeviceInfo(lastStatusResponse.getData());
                    return;
                }
                getView().showError("数据异常");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void loadTimes(final String deviceId, long start, long end) {
        RetrofitClient.getInstance().time(deviceId,start,end,new BaseSubscriber<TimeResponse>(){
            @Override
            public void onNext(TimeResponse timeResponse) {
                super.onNext(timeResponse);
                if(timeResponse.isSuccess() && timeResponse.getData() != null){
                    DeviceRateEvent rateEvent = new DeviceRateEvent(deviceId,false,timeResponse.getData().getDeviceValues());
                    EventBus.getDefault().post(rateEvent);
                    return;
                }
                getView().showError("数据异常");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                DeviceTimeEvent event = new DeviceTimeEvent(deviceId,true,null);
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public void loadOEE(final String deviceId, long start, long end) {
        RetrofitClient.getInstance().oee(deviceId,start,end,new BaseSubscriber<OEEResponse>(){
            @Override
            public void onNext(OEEResponse oeeResponse) {
                super.onNext(oeeResponse);
                if(oeeResponse.isSuccess() && !ObjectUtils.isEmpty(oeeResponse.getData())){
                    DeviceOeeEvent event = new DeviceOeeEvent(deviceId,false,oeeResponse.getData().getOee());
                    EventBus.getDefault().post(event);
                    return;
                }
                getView().showError("数据异常");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                DeviceOeeEvent event = new DeviceOeeEvent(deviceId,true,null);
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public void loadTimeSummary(final String deviceId) {
        long start = 0;
        long end = System.currentTimeMillis();
        RetrofitClient.getInstance().timeSummary(deviceId,start,end,new BaseSubscriber<RunTimeSummaryResponse>(){
            @Override
            public void onNext(RunTimeSummaryResponse runTimeSummaryResponse) {
                super.onNext(runTimeSummaryResponse);
                if(!runTimeSummaryResponse.isSuccess()){
                    getView().showError(runTimeSummaryResponse.getMessage());
                }
                RunTimeSummary summary = runTimeSummaryResponse.getData().getSummary();
                DeviceTimeEvent event = new DeviceTimeEvent(deviceId,false,summary);
                EventBus.getDefault().post(summary);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
