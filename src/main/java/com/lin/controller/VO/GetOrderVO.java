package com.lin.controller.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 林炳昌
 * @desc 查看订单返回模型
 * @date 2023年05月06日 20:12
 */
@Data
@AllArgsConstructor
public class GetOrderVO {

    private String order_id;
    private Integer commodityId;
    private String sellerName;
    private Integer price;
    private String addTime;
    private Integer status;

}
