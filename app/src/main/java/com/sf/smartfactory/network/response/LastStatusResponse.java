package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.LastStatus;

/**
 * @author: winton
 * @time: 2018/4/8 20:35
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe:
 */
public class LastStatusResponse extends BaseResponse {

    private LastStatus data;

    public LastStatus getData() {
        return data;
    }

    public void setData(LastStatus data) {
        this.data = data;
    }
}
