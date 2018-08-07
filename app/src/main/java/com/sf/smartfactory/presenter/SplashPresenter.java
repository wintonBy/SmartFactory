package com.sf.smartfactory.presenter;

import com.blankj.utilcode.util.SPUtils;
import com.sf.smartfactory.MyApplication;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.contract.SplashContract;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.FactoryInfo;
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
                saveFactoryInfo(factoryInfoResponse.getData().getSetting());
            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }

    /**
     * 保存工厂信息
     * @param info
     */
    private void saveFactoryInfo(FactoryInfo info){
        SPUtils.getInstance().put(Constant.SP_FACTORY_IMAGE,info.getWorkshopImgApp());
        if(info.getCompany()!= null){
            SPUtils.getInstance().put(Constant.SP_FACTORY_DATE,info.getCompany().getDate());
            SPUtils.getInstance().put(Constant.SP_FACTORY_FOUND,info.getCompany().getFund());
            SPUtils.getInstance().put(Constant.SP_FACTORY_NAME,info.getCompany().getName());
            SPUtils.getInstance().put(Constant.SP_FACTORY_EMP_NUM,info.getCompany().getEmpnums());
            SPUtils.getInstance().put(Constant.SP_FACTORY_LEGAL_PERSON,info.getCompany().getLegalperson());
        }
    }
}
