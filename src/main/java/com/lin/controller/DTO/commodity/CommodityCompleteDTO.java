package com.lin.controller.DTO.commodity;

import com.lin.controller.DTO.user.UserMiniDTO;
import com.lin.mapper.AccountMapper;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Account;
import com.lin.pojo.Commodity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author czh
 * @desc TODO 商品模型（完全）用于自己看自己发布的商品，或者管理员审核
 * @date 2023/5/31 16:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityCompleteDTO {
    private Integer commodityId;
    private String title;
    private Integer price;
    private String game;
    private UserMiniDTO seller;
    private Integer allowBargaining;
    private Integer status;
    private String description;
    private List<String> pictureUrlList;
    private Account account;
    private String releaseAt;

    public CommodityCompleteDTO(Commodity commodity, UserMapper userMapper, CommodityMapper commodityMapper, AccountMapper accountMapper) {
        this(commodity);
        this.setSeller(new UserMiniDTO(userMapper.selectById(commodity.getSellerId())));
        this.setPictureUrlList(commodityMapper.selectPictureUrl(commodity.getCommodityId()));
        this.setAccount(accountMapper.selectById(this.getCommodityId()));
    }

    private CommodityCompleteDTO(Commodity commodity) {
        this.commodityId = commodity.getCommodityId();
        this.title = commodity.getTitle();
        this.price = commodity.getPrice();
        this.game = commodity.getGame();
        this.allowBargaining = commodity.getAllowBargaining();
        this.status = commodity.getStatus();
        this.description = commodity.getDescription();
        this.releaseAt = commodity.getReleaseAt();
    }
}