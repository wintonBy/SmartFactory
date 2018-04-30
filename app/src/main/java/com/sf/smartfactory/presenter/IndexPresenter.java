package com.sf.smartfactory.presenter;

import com.sf.smartfactory.BuildConfig;
import com.sf.smartfactory.constant.Constant;
import com.sf.smartfactory.contract.IndexContract;
import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.bean.UpdateInfo;
import com.sf.smartfactory.network.response.UpdateInfoResponse;
import com.sf.smartfactory.ui.activity.IndexActivity;
import com.blankj.utilcode.util.LogUtils;

/**
 * @author: winton
 * @time: 2018/3/22 20:36
 * @package: com.sf.smartfactory.presenter
 * @project: SmartFactory
 * @mail:
 * @describe: 首页Presenter
 */
public class IndexPresenter extends BasePresenter<IndexActivity> implements IndexContract.Presenter {


    @Override
    public void checkVersion() {
        UpdateInfoResponse.loadUpdateInfo(new BaseSubscriber<UpdateInfoResponse>(){
            @Override
            public void onNext(UpdateInfoResponse updateInfoResponse) {
                super.onNext(updateInfoResponse);
                if(!updateInfoResponse.isSuccess()){
                    LogUtils.eTag(TAG,updateInfoResponse.getMessage());
                    return;
                }
                if(hasNewVersion(updateInfoResponse.getData())){
                    getView().showNewVersion(updateInfoResponse.getData());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    /**
     * 判断是否有新版本
     * @param info
     * @return
     */
    private boolean hasNewVersion(UpdateInfo info){
        boolean result = false;
        if(info == null){
            return result;
        }
        int newVersion = info.getId();
        if(newVersion > Constant.VERSION_CODE){
            result = true;
        }
        return result;
    }
}
