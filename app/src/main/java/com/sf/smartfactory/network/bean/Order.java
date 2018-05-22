package com.sf.smartfactory.network.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/3 16:31
 * @package: com.sf.smartfactory.network.bean
 * @project: SmartFactory
 * @mail:
 * @describe: 订单
 */
public class Order implements Serializable {
    private int id;
    private String no;
    private String name;
    private String content;
    private String startDt;
    private String endDt;
    private int status;
    private TargetBean target;
    private ExtendBean extend;
    private String createDt;
    private int off;
    private List<OrderRelation> relations;


    public static class TargetBean implements Serializable{
        private int quantity;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public static class ExtendBean implements Serializable{
        private int quantity;
        private String clientName;
        private int percent;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {

            this.clientName = clientName;
        }

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TargetBean getTarget() {
        return target;
    }

    public void setTarget(TargetBean target) {
        this.target = target;
    }

    public ExtendBean getExtend() {
        return extend;
    }

    public void setExtend(ExtendBean extend) {
        this.extend = extend;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public int getOff() {
        return off;
    }

    public void setOff(int off) {
        this.off = off;
    }

    public List<OrderRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<OrderRelation> relations) {
        this.relations = relations;
    }
}
