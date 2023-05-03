package com.lin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
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
    private String description;
    private Integer price;
    private Integer sellerId;
    private boolean allowBargaining;
    private Integer status;
    static public final int STATUS_INSPECTING = 0;
    static public final int STATUS_SELLING = 1;
    static public final int STATUS_SOLD = 2;
    static public final int STATUS_CANCEL = 3;
}