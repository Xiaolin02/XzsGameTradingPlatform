package com.lin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 商品实体类
 * @date 2023年04月20日 21:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {

    @TableId
    private Integer commodityId;
    private String releaseTime;
    private String title;
    @TableField(value = "`description`")
    private String description;
    private Integer price;
    private Integer sellerId;
    @ApiModelProperty("游戏名")
    private String game;
    private Integer allowBargaining;
    @TableField(value = "`status`")
    private Integer status;

    public Commodity(Commodity commodity) {
        this.commodityId = commodity.commodityId;
        this.releaseTime = commodity.releaseTime;
        this.title = commodity.title;
        this.description = commodity.description;
        this.price = commodity.price;
        this.sellerId = commodity.sellerId;
        this.game = commodity.game;
        this.allowBargaining = commodity.allowBargaining;
        this.status = commodity.status;
    }
}
