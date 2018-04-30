package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.Status;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/24 23:26
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 设备状态列表
 */
public class StatusListResponse extends BaseResponse{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        List<Status> list;

        public List<Status> getList() {
            return list;
        }

        public void setList(List<Status> list) {
            this.list = list;
        }
    }
}
