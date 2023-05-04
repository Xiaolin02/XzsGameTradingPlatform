package com.lin.common;

/**
 * @Author czh
 * @desc 商品状态常量
 * @date 2023/5/4 14:07
 */
public interface CommodityStatusConstants {
    int STATUS_INSPECTING = 0;      // 商品等待审核
    int STATUS_SELLING = 1;         // 商品正常售卖中
    int STATUS_SOLD = 2;            // 商品已售出
    int STATUS_CANCEL = 3;          // 商品已下架
}
