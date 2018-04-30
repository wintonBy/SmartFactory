package com.sf.smartfactory.presenter;

import com.sf.smartfactory.contract.DeviceListContract;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.DeviceStatus;
import com.sf.smartfactory.network.response.DeviceListResponse;
import com.sf.smartfactory.ui.activity.DeviceListActivity;
import com.sf.smartfactory.utils.DeviceUtils;
import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/3/29 23:41
 * @package: com.sf.smartfactory.presenter
 * @project: SmartFactory
 * @mail:
 * @describe: 设备列表页P
 */
public class DeviceListPresenter extends BasePresenter<DeviceListActivity> implements DeviceListContract.Presenter {


    @Override
    public void loadDeviceList(final String type) {
        RetrofitClient.getInstance().monitorDeviceList(new BaseSubscriber<DeviceListResponse>(){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onNext(DeviceListResponse deviceListResponse) {
                super.onNext(deviceListResponse);
                if(deviceListResponse == null || deviceListResponse.getData() == null){
                    getView().showError("服务异常");
                    return;
                }
                getView().showDevicesList(filterList(deviceListResponse.getData().getList(),type));
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getView().showError("服务异常");
            }
        });
    }

    /**
     * 本地筛选
     * @param statuses
     * @param type
     * @return
     */
    private List<DeviceStatus> filterList(List<DeviceStatus> statuses,String type){
        if(type.equals(DeviceListActivity.LIST_ALL)){
            return statuses;
        }
        List<DeviceStatus> result = new ArrayList<>();
        if(statuses == null){
            return result;
        }
        for(int i =0;i<statuses.size();i++){
            DeviceStatus item = statuses.get(i);
            if(DeviceUtils.INSTANCE.isTypeRight(item.getStatus(),type)){
                result.add(item);
            }
        }
        return result;
    }
}
