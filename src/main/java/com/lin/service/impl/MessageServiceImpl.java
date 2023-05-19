package com.lin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.WebSocketServer;
import com.lin.mapper.MessageMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Message;
import com.lin.pojo.User;
import com.lin.service.MessageService;
import com.lin.utils.CheckMsgUtil;
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
        if (CheckMsgUtil.checkMsg(content))
            return new ResponseResult(CodeConstants.CODE_SUCCESS,"含有敏感信息");
        else
            return new ResponseResult(CodeConstants.CODE_SUCCESS,"无异常");

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
        return new ResponseResult(CodeConstants.CODE_SUCCESS);


    }

    @Override
    public ResponseResult pushMsg(String token, Integer toId, String content) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(content);
        String toStringContent = jsonObject.get("content").toString();
        Claims claims = TokenUtil.parseToken(token);
        String username = claims.get("username").toString();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User fromUser = userMapper.selectOne(wrapper);
        User toUser = userMapper.selectById(toId);
        Message message = new Message();
        message.setToUser(toUser.getUsername());
        message.setFromUser(fromUser.getUsername());
        message.setContent(toStringContent);
        message.setSendTime(DateUtil.getDateTime());
        messageMapper.insert(message);
        if (CheckMsgUtil.checkMsg(content))
            return new ResponseResult(CodeConstants.CODE_SUCCESS,"含有敏感信息");
        else
            return new ResponseResult(CodeConstants.CODE_SUCCESS,"无异常");
    }

}
