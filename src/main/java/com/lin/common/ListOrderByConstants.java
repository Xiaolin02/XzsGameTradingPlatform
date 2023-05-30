package com.lin.common;

/**
 * @Author czh
 * @desc 特定的数字，用于返回list时指定顺序
 * @date 2023/5/30 12:34
 */
public interface ListOrderByConstants {
    int COMMODITY_ORDER_BY_RELEASE_AT_ASC = 1;
    int COMMODITY_ORDER_BY_RELEASE_AT_DESC = -1;
    int COMMODITY_ORDER_BY_PRICE_ASC = 2;
    int COMMODITY_ORDER_BY_PRICE_DESC = -2;
}
