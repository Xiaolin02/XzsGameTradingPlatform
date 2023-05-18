package com.lin.common;

import java.util.Objects;

/**
 * @Author czh
 * @desc 商品状态常量
 * @date 2023/5/4 14:07
 */
public interface CommodityStatusConstants {

    int STATUS_INSPECTING = 0;      // 等待审核
    int STATUS_SELLING = 1;         // 售卖中
    int STATUS_SOLD = 2;            // 已售出
    int STATUS_CANCEL = 3;          // 已下架
    int STATUS_FAILED = 4;          // 审核失败

    default String getStatusName(int statusId) {

        if(Objects.equals(statusId, 0))
            return "审核中";
        else if (Objects.equals(statusId, 1))
            return "售卖中";
        else if (Objects.equals(statusId, 2))
            return "已售出";
        else if (Objects.equals(statusId, 3))
            return "已下架";
        else
            return "审核失败";

    }

}
