package com.sf.smartfactory.network.response;

/**
 * @author: winton
 * @time: 2018/4/2 14:22
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 设备简况
 */
public class DeviceSummaryResponse extends BaseResponse{

    private Summary data;

    public Summary getData() {
        return data;
    }

    public void setData(Summary data) {
        this.data = data;
    }

    public static class Summary{
        private int offline;
        private int normal;
        private int abnormal;

        public int getOffline() {
            return offline;
        }

        public void setOffline(int offline) {
            this.offline = offline;
        }

        public int getNormal() {
            return normal;
        }

        public void setNormal(int normal) {
            this.normal = normal;
        }

        public int getAbnormal() {
            return abnormal;
        }

        public void setAbnormal(int abnormal) {
            this.abnormal = abnormal;
        }
    }
}
