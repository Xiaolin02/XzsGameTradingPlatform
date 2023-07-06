package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 聊天列表实体类
 * @date 2023年07月06日 17:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatList {

    private Integer firstUserId;
    private Integer secondUserId;
    private String lastMessage;

}
