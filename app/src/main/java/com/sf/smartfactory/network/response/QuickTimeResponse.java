package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.BaseSubscriber;
import com.sf.smartfactory.network.RetrofitClient;
import com.sf.smartfactory.network.bean.RunTimeSummary;
import com.sf.smartfactory.network.bean.Status;
import com.sf.smartfactory.network.subscriber.QuickSubscriber;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/6/22 10:28
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class QuickTimeResponse extends BaseResponse{


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private RunTimeSummary summary;
        private List<TimeResponse.Device> deviceValues;
        private List<Status> list;

        public RunTimeSummary getSummary() {
            return summary;
        }

        public void setSummary(RunTimeSummary summary) {

            this.summary = summary;
        }

        public List<TimeResponse.Device> getDeviceValues() {
            return deviceValues;
        }

        public void setDeviceValues(List<TimeResponse.Device> deviceValues) {
            this.deviceValues = deviceValues;
        }

        public List<Status> getList() {
            return list;
        }

        public void setList(List<Status> list) {
            this.list = list;
        }
    }

}
