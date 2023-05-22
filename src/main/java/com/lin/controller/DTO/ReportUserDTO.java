package com.lin.controller.DTO;

import lombok.Data;

/**
 * @Author czh
 * @desc 举报用户DTO类
 * @date 2023/5/4 8:58
 */
@Data
public class ReportUserDTO {
    private Integer userId;
    private String reason;
}
