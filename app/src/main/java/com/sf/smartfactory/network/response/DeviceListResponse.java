package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.DeviceStatusList;

/**
 * @author: winton
 * @time: 2018/3/30 15:39
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 设备列表响应
 */
public class DeviceListResponse extends BaseResponse {

    private static DeviceListResponse response;

    private static long lastRequestTime = -1;

    private DeviceStatusList data;

    public DeviceStatusList getData() {
        return data;
    }

    public static void loadList(final BaseSubscriber<DeviceListResponse> subscriber){
        if(lastRequestTime == -1 || System.currentTimeMillis() - lastRequestTime >1501){
            RetrofitClient.getInstance().monitorDeviceList(new BaseSubscriber<DeviceListResponse>() {
                @Override
                public void success(DeviceListResponse deviceListResponse) {
                    subscriber.success(deviceListResponse);
                    response = deviceListResponse;
                }

                @Override
                public void failed(Throwable e) {
                    subscriber.failed(e);
                }
            });
            lastRequestTime = System.currentTimeMillis();
        }else {
            subscriber.success(response);
        }


    }
}
