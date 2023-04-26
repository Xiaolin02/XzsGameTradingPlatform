package com.lin.controller.DTO;

import lombok.Data;

/**
 * @Author czh
 * @desc 举报商品
 * @date 2023/4/26 21:32
 */
@Data
public class    ReportCommodityDTO {
    private Integer commodityId;
    private String reason;
}
