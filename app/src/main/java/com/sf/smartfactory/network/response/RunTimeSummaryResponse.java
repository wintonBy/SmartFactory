package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.RunTimeSummary;

/**
 * @author: winton
 * @time: 2018/4/23 19:29
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 运行时间概要响应
 */
public class RunTimeSummaryResponse extends BaseResponse{
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private RunTimeSummary summary;

        public RunTimeSummary getSummary() {
            return summary;
        }

        public void setSummary(RunTimeSummary summary) {
            this.summary = summary;
        }

    }
}
