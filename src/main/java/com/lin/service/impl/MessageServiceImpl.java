package com.lin.service.impl;

import com.lin.common.ResponseResult;
import com.lin.controller.WebSocketServer;
import com.lin.mapper.MessageMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Message;
import com.lin.pojo.User;
import com.lin.service.MessageService;
import com.lin.utils.DateUtil;
import com.lin.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月22日 10:18
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseResult pushMsgToOneUser(String token, Integer toId, String content) throws IOException {

        WebSocketServer.sendInfo(content, toId);
        return new ResponseResult<>(200);

    }

    @Override
    public ResponseResult pushSystemMsgToOneUser(String token, Integer toId, String content, String title) {

        User user = userMapper.selectById(toId);
        Message message = new Message();
        message.setToUser(user.getUsername());
        message.setFromUser("SYSTEM");
        message.setTitle(title);
        message.setContent(content);
        message.setSendTime(DateUtil.getDateTime());
        messageMapper.insert(message);
        return new ResponseResult(200);


    }

}
