package com.lin.controller.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 林炳昌
 * @desc 获取系统消息模型
 * @date 2023年06月05日 21:50
 */

@Data
@AllArgsConstructor
public class GetSystemMessageVO {

    String title;
    String content;

}
