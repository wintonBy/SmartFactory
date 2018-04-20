package com.sf.smartfactory.network.bean;

import java.io.Serializable;

/**
 * @author: winton
 * @time: 2018/4/1 16:30
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 一句话描述
 */
public class ParamsBean implements Serializable {
    private int axis_rate = 0;
    private int fast_rate = 0;
    private int feed_rate = 0;

    public int getAxis_rate() {
        return axis_rate;
    }

    public void setAxis_rate(int axis_rate) {
        this.axis_rate = axis_rate;
    }

    public int getFeed_rate() {
        return feed_rate;
    }

    public void setFeed_rate(int feed_rate) {
        this.feed_rate = feed_rate;
    }

    public int getFast_rate() {
        return fast_rate;
    }

    public void setFast_rate(int fast_rate) {
        this.fast_rate = fast_rate;
    }
}
