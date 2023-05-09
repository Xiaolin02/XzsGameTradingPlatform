package com.lin.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 林炳昌
 * @desc 手机验证码登录模型
 * @date 2023年05月09日 19:43
 */
@Data
@AllArgsConstructor
public class CodeLoginDTO {

    private String phone;
    private String code;

}
