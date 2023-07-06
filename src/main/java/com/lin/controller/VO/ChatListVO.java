package com.lin.controller.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 获取聊天列表模型
 * @date 2023年07月06日 10:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatListVO {

    private String pictureUrl;
    private String username;
    private Integer userId;
    private String lastMsg;

}
