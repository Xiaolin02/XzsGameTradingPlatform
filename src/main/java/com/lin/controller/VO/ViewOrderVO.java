package com.lin.controller.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 卖家查看订单模型
 * @date 2023年05月29日 22:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewOrderVO {

    Integer commodityId;
    String buyerName;
    String buyerPictureUrl;
    Integer price;
    String addAt;
    Integer status;

}
