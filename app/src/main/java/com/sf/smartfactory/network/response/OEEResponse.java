package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.OEE;

/**
 * @author: winton
 * @time: 2018/4/8 20:46
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: OEE接口响应体
 */
public class OEEResponse extends BaseResponse{
    private OEEBean data;

    public OEEBean getData() {
        return data;
    }

    public void setData(OEEBean data) {
        this.data = data;
    }

    public static class OEEBean{
        OEE oee;

        public OEE getOee() {
            return oee;
        }

        public void setOee(OEE oee) {
            this.oee = oee;
        }
    }

}
