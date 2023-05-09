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
    private boolean allowBargaining;
    @TableField(value = "`status`")
    private Integer status;
}
