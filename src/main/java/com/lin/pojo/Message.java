package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 聊天信息
 * @date 2023年04月22日 10:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String fromUser;
    private String toUser;
    private String title;
    private String content;
    private String sendAt;


}
