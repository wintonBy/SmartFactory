package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.ProcessNum;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/8/8 21:02
 * @package: com.sf.smartfactory.network.response
 * @project: Factory
 * @mail:
 * @describe: 加工数量返回结果
 */
public class ProcessNumResponse extends BaseResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private List<List<ProcessNum>> list;

        public List<List<ProcessNum>> getList() {
            return list;
        }

        public void setList(List<List<ProcessNum>> list) {
            this.list = list;
        }
    }
}
