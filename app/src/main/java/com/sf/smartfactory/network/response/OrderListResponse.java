package com.sf.smartfactory.network.response;

import com.sf.smartfactory.network.bean.Order;

import java.util.List;

/**
 * @author: winton
 * @time: 2018/4/3 16:30
 * @package: com.sf.smartfactory.network.response
 * @project: SmartFactory
 * @mail:
 * @describe: 订单列表响应
 */
public class OrderListResponse extends BaseResponse {

    private OrderList data;

    public OrderList getData() {
        return data;
    }

    public void setData(OrderList data) {
        this.data = data;
    }

    public static class OrderList{
        private List<Order> list;

        public List<Order> getList() {
            return list;
        }

        public void setList(List<Order> list) {
            this.list = list;
        }
    }
}
