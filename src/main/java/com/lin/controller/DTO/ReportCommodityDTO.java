package com.lin.controller.DTO;

import lombok.Data;

/**
 * @Author czh
 * @desc 举报商品DTO类
 * @date 2023/5/4 8:58
 */
@Data
public class ReportCommodityDTO {
    private Integer commodityId;
    private String reason;
}
