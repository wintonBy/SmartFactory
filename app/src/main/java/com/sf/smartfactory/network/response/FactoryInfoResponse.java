package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.FactoryInfo;

/**
 * @author: winton
 * @time: 2018/6/19 13:54
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 工厂信息
 */
public class FactoryInfoResponse extends BaseResponse{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        FactoryInfo setting;


        public FactoryInfo getSetting() {
            return setting;
        }

        public void setSetting(FactoryInfo setting) {
            this.setting = setting;
        }
    }
}
