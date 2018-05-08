package com.sf.smartfactory.presenter;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.sf.smartfactory.contract.DeviceDetailContract;
import com.sf.smartfactory.event.DeviceOeeEvent;
import com.sf.smartfactory.event.DeviceRateEvent;
import com.sf.smartfactory.event.DeviceTimeEvent;
import com.sf.smartfactory.event.DeviceTimeStatusEvent;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.OEE;
import com.sf.smartfactory.network.bean.RunTimeSummary;
import com.sf.smartfactory.network.bean.Status;
import com.sf.smartfactory.network.response.DeviceRateResponse;
import com.sf.smartfactory.network.response.LastStatusResponse;
import com.sf.smartfactory.network.response.OEEResponse;
import com.sf.smartfactory.network.response.RunTimeSummaryResponse;
import com.sf.smartfactory.network.response.StatusListResponse;
import com.sf.smartfactory.network.response.TimeResponse;
import com.sf.smartfactory.ui.activity.DeviceDetailActivity;
import com.sf.smartfactory.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
    public void loadRates(final String deviceId) {
        long start = DateUtils.INSTANCE.getWorkStart();
        long end = System.currentTimeMillis();
        RetrofitClient.getInstance().rate(deviceId,start,end,new BaseSubscriber<DeviceRateResponse>(){
            @Override
            public void onNext(DeviceRateResponse rateResponse) {
                super.onNext(rateResponse);
                if(rateResponse.isSuccess() && rateResponse.getData() != null){
                    DeviceRateEvent rateEvent = new DeviceRateEvent(deviceId,false,rateResponse.getData().getDeviceValues());
                    EventBus.getDefault().post(rateEvent);
                    return;
                }
                getView().showError("数据异常");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                DeviceRateEvent event = new DeviceRateEvent(deviceId,true,null);
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public void loadOEE(final String deviceId) {
        RetrofitClient.getInstance().oee(deviceId,new BaseSubscriber<OEEResponse>(){
            @Override
            public void onNext(OEEResponse oeeResponse) {
                super.onNext(oeeResponse);
                if(oeeResponse.isSuccess() && !ObjectUtils.isEmpty(oeeResponse.getData())){
                    OEE oee = oeeResponse.getData().getOee();
                    if(oee == null){
                        oee = new OEE();
                    }
                    DeviceOeeEvent event = new DeviceOeeEvent(deviceId,false,oee);
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

        long start = DateUtils.INSTANCE.getWorkStart();
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
                EventBus.getDefault().post(event);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void loadTimeStatus(final String deviceId) {
        long start = DateUtils.INSTANCE.getWorkStart();
        long end = System.currentTimeMillis();
        RetrofitClient.getInstance().statusList(deviceId,start,end,new BaseSubscriber<StatusListResponse>(){
            @Override
            public void onNext(StatusListResponse statusListResponse) {
                super.onNext(statusListResponse);
                if(!statusListResponse.isSuccess()){
                    getView().showError(statusListResponse.getMessage());
                    return;
                }
                List<Status> statusList = statusListResponse.getData().getList();
                DeviceTimeStatusEvent event = new DeviceTimeStatusEvent(deviceId,false,statusList);
                EventBus.getDefault().post(event);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                DeviceTimeStatusEvent event = new DeviceTimeStatusEvent(deviceId,true,null);
                EventBus.getDefault().post(event);
            }
        });
    }
}
