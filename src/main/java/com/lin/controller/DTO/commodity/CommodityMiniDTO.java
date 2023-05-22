package com.lin.controller.DTO.commodity;

import com.lin.pojo.Commodity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 商品模型（迷你）（只有标题）用于最少信息的查看，如收藏夹显示的商品
 * @date 2023/5/22 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityMiniDTO {
    private Integer commodityId;
    private String title;

    public CommodityMiniDTO(Commodity commodity) {
        this.commodityId = commodity.getCommodityId();
        this.title = commodity.getTitle();
    }

}
