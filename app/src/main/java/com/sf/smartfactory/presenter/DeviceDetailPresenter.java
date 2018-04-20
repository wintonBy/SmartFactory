package com.sf.smartfactory.presenter;

import com.sf.smartfactory.contract.DeviceDetailContract;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.response.LastStatusResponse;
import com.sf.smartfactory.network.response.OEEResponse;
import com.sf.smartfactory.network.response.TimeResponse;
import com.sf.smartfactory.ui.activity.DeviceDetailActivity;
import com.wasu.iutils.ObjectUtils;
import com.wasu.iutils.StringUtils;
import com.wasu.iutils.ToastUtils;

/**
 * @author: winton
 * @time: 2018/4/7 23:27
 * @package: com.sf.smartfactory.presenter
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
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
    public void loadTimes(String deviceId, long start, long end) {
        RetrofitClient.getInstance().time(deviceId,start,end,new BaseSubscriber<TimeResponse>(){
            @Override
            public void onNext(TimeResponse timeResponse) {
                super.onNext(timeResponse);
                if(timeResponse.isSuccess() && timeResponse.getData() != null){
                    getView().setRunSummary(timeResponse.getData().getSummary());
                    getView().setDeviceRate(timeResponse.getData().getDeviceValues());
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
    public void loadOEE(String deviceId, long start, long end) {
        RetrofitClient.getInstance().oee(deviceId,start,end,new BaseSubscriber<OEEResponse>(){
            @Override
            public void onNext(OEEResponse oeeResponse) {
                super.onNext(oeeResponse);
                if(oeeResponse.isSuccess() && !ObjectUtils.isEmpty(oeeResponse.getData())){
                    getView().setOEEInfo(oeeResponse.getData().getOee());
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
}
