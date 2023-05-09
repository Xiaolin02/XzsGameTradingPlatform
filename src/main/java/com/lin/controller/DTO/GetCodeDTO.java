package com.lin.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 林炳昌
 * @desc 获取验证码模型
 * @date 2023年05月09日 19:49
 */
@Data
@AllArgsConstructor
public class GetCodeDTO {

    private String phone;
    private String type;

}
