package com.lin.controller.DTO;

import lombok.Data;

/**
 * @Author czh
 * @desc TODO 用户的详细信息（自己主页看的）
 * @date 2023/5/21 22:20
 */
@Data
public class UserDetailsDTO {
    private Integer userId;
    private String username;
    private String pictureUrl;
    private String phone;
    private Integer balance;
    private String registerAt;

}
