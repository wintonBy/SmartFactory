package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.UpdateInfo;

/**
 * @author: winton
 * @time: 2018/4/24 23:42
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 升级响应
 */
public class UpdateInfoResponse extends BaseResponse {
    private UpdateInfo data;


    public static void loadUpdateInfo(BaseSubscriber<UpdateInfoResponse> subscriber){
        RetrofitClient.getInstance().updateInfo(subscriber);
    }

    public UpdateInfo getData() {
        return data;
    }

    public void setData(UpdateInfo data) {
        this.data = data;
    }
}
