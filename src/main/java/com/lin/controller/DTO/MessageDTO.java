package com.lin.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 林炳昌
 * @desc 推送系统消息模型
 * @date 2023年04月22日 11:44
 */
@Data
@AllArgsConstructor
public class MessageDTO {

    private String title;
    private String content;

}
