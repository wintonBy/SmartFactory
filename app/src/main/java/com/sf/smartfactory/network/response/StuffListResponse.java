package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.Stuff;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/5 10:33
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 员工列表响应
 */
public class StuffListResponse extends BaseResponse {

    private StuffList data;

    public StuffList getData() {
        return data;
    }

    public void setData(StuffList data) {
        this.data = data;
    }

    public static class StuffList{
        List<Stuff> list;

        public List<Stuff> getList() {
            return list;
        }

        public void setList(List<Stuff> list) {
            this.list = list;
        }
    }

}
