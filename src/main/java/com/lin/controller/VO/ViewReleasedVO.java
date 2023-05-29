package com.lin.controller.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 查看发布商品模型
 * @date 2023年05月18日 22:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewReleasedVO {

    private Integer commodityId;
    private String releasedTime;
    private String title;
    private String description;
    private Integer price;
    private String game;
    private Integer allowBargaining;
    private String status;

}
