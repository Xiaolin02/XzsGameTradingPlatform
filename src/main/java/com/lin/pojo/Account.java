package com.lin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 账号实体类
 * @date 2023年05月09日 14:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @TableId
    private Integer commodityId;
    private String accountNumber;
    private String accountPassword;

}
