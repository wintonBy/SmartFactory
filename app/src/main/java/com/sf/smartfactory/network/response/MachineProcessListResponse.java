package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.MachineProcess;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/4 10:09
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 加工信息响应
 */
public class MachineProcessListResponse extends BaseResponse {

    private MachineProcessList data;

    public MachineProcessList getData() {
        return data;
    }

    public void setData(MachineProcessList data) {
        this.data = data;
    }

    public static class MachineProcessList{
        private List<MachineProcess> list;

        public List<MachineProcess> getList() {
            return list;
        }

        public void setList(List<MachineProcess> list) {
            this.list = list;
        }
    }
}
