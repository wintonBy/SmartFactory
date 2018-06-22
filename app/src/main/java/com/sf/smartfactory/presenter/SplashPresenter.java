package com.sf.smartfactory.presenter;

import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.contract.SplashContract;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.response.FactoryInfoResponse;

/**
 * @author: winton
 * @time: 2018/3/22 17:07
 * @package: com.sf.smartfactory.presenter
 * @project: SmartFactory
 * @mail:
 * @describe: 启动页
 */
public class SplashPresenter extends BasePresenter implements SplashContract.Presenter {

    @Override
    public void loadFactoryInfo() {
        RetrofitClient.getInstance().getFactory(new BaseSubscriber<FactoryInfoResponse>(){

            @Override
            public void success(FactoryInfoResponse factoryInfoResponse) {
                MyApplication.mFactoryInfo = factoryInfoResponse.getData().getSetting();
            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }
}
