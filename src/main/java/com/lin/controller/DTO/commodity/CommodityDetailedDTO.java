package com.lin.controller.DTO.commodity;

import com.lin.controller.DTO.user.UserMiniDTO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Commodity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author czh
 * @desc 商品模型（详情）（商品详情页）用于详细的商品信息显示，如商品详情页的展示
 * @date 2023/5/22 16:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityDetailedDTO {
    private Integer commodityId;
    private String title;
    private Integer price;
    private String game;
    private UserMiniDTO seller;
    private Integer allowBargaining;
    private Integer status;
    private String description;
    private List<String> pictureUrlList;
    private String releaseTime;

    public CommodityDetailedDTO(Commodity commodity, UserMapper userMapper, CommodityMapper commodityMapper) {
        this(commodity);
        this.setSeller(new UserMiniDTO(userMapper.selectById(commodity.getSellerId())));
        this.pictureUrlList = commodityMapper.selectPictureUrl(commodity.getCommodityId());
    }

    private CommodityDetailedDTO(Commodity commodity) {
        this.commodityId = commodity.getCommodityId();
        this.title = commodity.getTitle();
        this.price = commodity.getPrice();
        this.game = commodity.getGame();
        this.allowBargaining = commodity.getAllowBargaining();
        this.status = commodity.getStatus();
        this.description = commodity.getDescription();
        this.releaseTime = commodity.getReleaseAt();
    }
}
