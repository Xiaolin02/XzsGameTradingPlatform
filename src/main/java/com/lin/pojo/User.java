package com.lin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 用户实体类
 * @date 2023年03月31日 15:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @TableId
    private Integer userId;
    private String username;
    private String password;
    private String phone;
    private Integer balance;
    private String registerAt;
    private Integer transactionsNumber;
    private Integer successNumber;

}
