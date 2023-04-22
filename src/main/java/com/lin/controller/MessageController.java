package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.MessageDTO;
import com.lin.service.impl.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author 林炳昌
 * @desc 在线聊天、系统信息、私聊
 * @date 2023年04月22日 10:13
 */
@Slf4j
@RestController
public class MessageController {

    @Autowired
    MessageServiceImpl messageService;

    /**
     * @desc 向某用户推送信息
     * @date 2023/4/22 10:45
     */
    @GetMapping("/pushMsg/{toId}")
    public ResponseResult pushMsgToOneUser(@RequestHeader String token, @PathVariable Integer toId, @RequestBody String content) throws IOException {

        log.info("访问了{}接口",Thread.currentThread().getStackTrace()[1].getMethodName());
        return messageService.pushMsgToOneUser(token,toId,content);

    }

    /**
     * @desc 向某用户推送系统信息
     * @date 2023/4/22 11:41
     */
    @PostMapping("/pushSystemMsg/{toId}")
    public ResponseResult pushSystemMsgToOneUser(@RequestHeader String token, @PathVariable Integer toId, @RequestBody MessageDTO messageDTO) {

        return messageService.pushSystemMsgToOneUser(token, toId, messageDTO.getContent(), messageDTO.getTitle());

    }

}
