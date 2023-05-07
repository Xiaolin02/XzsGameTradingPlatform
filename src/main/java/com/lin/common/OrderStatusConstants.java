package com.lin.common;

/**
 * @author 林炳昌
 * @desc 订单状态常量
 * @date 2023年05月05日 19:53
 */
public interface OrderStatusConstants {

    int STATUS_UNPAID = 0;          //未支付,提交订单一瞬间的默认状态
    int STATUS_PAID = 1;            //已支付
    int STATUS_BUYER_CANCEL = 2;    //买家取消订单
    int STATUS_SELLER_CANCEL = 3;   //卖家取消订单
    int STATUS_ABNORMAL = 4;        //异常订单(系统或管理员发现异常后冻结订单)
    int STATUS_COMPLETED = 5;       //已完成订单

}
