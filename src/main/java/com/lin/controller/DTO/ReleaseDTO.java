package com.lin.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 林炳昌
 * @desc 发布商品模型
 * @date 2023年05月07日 16:33
 */
@Data
@AllArgsConstructor
public class ReleaseDTO {

    private String title;
    private String description;
    private Integer price;
    private String game;
    private String accountNumber;
    private String accountPassword;
    private Integer allowBargaining;

}
