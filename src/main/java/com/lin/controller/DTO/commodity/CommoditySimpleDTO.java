package com.lin.controller.DTO.commodity;

import com.lin.controller.DTO.user.UserMiniDTO;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Commodity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 商品模型（简单）（搜索列表展示）用于简单的商品信息查看，如搜索页展示/订单展示商品信息
 * @date 2023/5/22 16:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommoditySimpleDTO {
    private Integer commodityId;
    private String title;
    private Integer price;
    private String game;
    private UserMiniDTO seller;
    private Integer allowBargaining;
    private Integer status;

    public CommoditySimpleDTO(Commodity commodity, UserMapper userMapper) {
        this(commodity);
        this.seller = new UserMiniDTO(userMapper.selectById(commodity.getSellerId()));
    }

    private CommoditySimpleDTO(Commodity commodity) {
        this.commodityId = commodity.getCommodityId();
        this.title = commodity.getTitle();
        this.price = commodity.getPrice();
        this.game = commodity.getGame();
        this.allowBargaining = commodity.getAllowBargaining();
        this.status = commodity.getStatus();
    }
}
