package com.lin.common;

import java.util.Objects;

/**
 * @Author czh
 * @desc 商品状态常量
 * @date 2023/5/4 14:07
 */
public interface CommodityStatusConstants {

    int STATUS_INSPECTING = 0;      // 审核中
    int STATUS_SELLING = 1;         // 售卖中
    int STATUS_SOLD = 2;            // 已售出
    int STATUS_CANCEL = 3;          // 已下架
    int STATUS_FAILED = 4;          // 审核失败

}
