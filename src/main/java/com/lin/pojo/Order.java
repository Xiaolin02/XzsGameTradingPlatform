package com.lin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 订单类
 * @date 2023年05月05日 22:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`order`")
public class Order {

    @TableId
    private String id;
    private Integer commodityId;
    private Integer sellerId;
    private Integer buyerId;
    private Integer price;
    private String addAt;
    @TableField(value = "`status`")
    private Integer status;

}
