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

    /**
     * 服务端获取到的数据
     */
    private List<DeviceStatus> allData;


    @Override
    public void loadDeviceList(final String type) {

        DeviceListResponse.loadList(new BaseSubscriber<DeviceListResponse>(){

            @Override
            public void success(DeviceListResponse deviceListResponse) {
                if(deviceListResponse.getData() == null){
                    getView().showError("服务异常");
                    return;
                }
                allData = deviceListResponse.getData().getList();
                filterList(type);
            }

            @Override
            public void failed(Throwable e) {
                getView().showError(e.getMessage());
            }
        });


    }

    /**
     * 本地筛选
     * @param type
     * @return
     */
    @Override
    public void filterList(String type){
        if(type.equals(DeviceListActivity.LIST_ALL)){
            getView().showDevicesList(allData);
            return;
        }
        List<DeviceStatus> result = new ArrayList<>();
        if(allData == null){
            getView().showDevicesList(result);
            return;
        }
        for(int i =0;i<allData.size();i++){
            DeviceStatus item = allData.get(i);
            if(DeviceUtils.INSTANCE.isTypeRight(item.getStatus(),type)){
                result.add(item);
            }
        }
        getView().showDevicesList(result);
    }
}
