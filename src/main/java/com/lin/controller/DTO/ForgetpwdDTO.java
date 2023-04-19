package com.lin.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 林炳昌
 * @desc 忘记密码模型
 * @date 2023年04月19日 21:36
 */
@Data
@AllArgsConstructor
public class ForgetpwdDTO {

    private String phone;
    private String code;

}
