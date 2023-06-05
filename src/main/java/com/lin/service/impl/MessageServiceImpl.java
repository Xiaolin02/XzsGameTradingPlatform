package com.lin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.VO.GetSystemMessageVO;
import com.lin.controller.WebSocketServer;
import com.lin.mapper.MessageMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Message;
import com.lin.pojo.User;
import com.lin.service.MessageService;
import com.lin.utils.CheckMsgUtil;
import com.lin.utils.DateUtil;
import com.lin.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public ResponseResult<NullData> pushMsgToOneUser(String token, Integer toId, String content) throws IOException {

        if (CheckMsgUtil.checkMsg(content)) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "含有敏感信息");
        }
        WebSocketServer.sendInfo(content, toId);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "无异常");

    }

    @Override
    public ResponseResult<NullData> pushSystemMsgToOneUser(String token, Integer toId, String content, String title) {

        User user = userMapper.selectById(toId);
        Message message = new Message();
        message.setToUser(user.getUsername());
        message.setFromUser("SYSTEM");
        message.setTitle(title);
        message.setContent(content);
        message.setSendAt(DateUtil.getDateTime());
        messageMapper.insert(message);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS,"成功");
    }

    @Override
    public ResponseResult<NullData> pushMsg(String token, Integer toId, String content) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(content);
        if (CheckMsgUtil.checkMsg(content)) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "含有敏感信息");
        }
        String toStringContent = jsonObject.get("content").toString();
        User fromUser = tokenUtil.parseTokenToUser(token);
        User toUser = userMapper.selectById(toId);
        Message message = new Message();
        message.setToUser(toUser.getUsername());
        message.setFromUser(fromUser.getUsername());
        message.setContent(toStringContent);
        message.setSendAt(DateUtil.getDateTime());
        messageMapper.insert(message);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "无异常");
    }

    @Override
    public ResponseResult<List<String>> getMessage(String token, Integer toId) {

        String fromUser = tokenUtil.parseTokenToUser(token).getUsername();
        String toUser = userMapper.selectById(toId).getUsername();
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("from_user", fromUser);
        wrapper.eq("to_user", toUser);
        List<Message> messageList = messageMapper.selectList(wrapper);
        ArrayList<String> messages = new ArrayList<>();
        for (Message message : messageList) {
            messages.add(message.getContent());
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, messages);

    }

    @Override
    public ResponseResult<List<GetSystemMessageVO>> getSystemMessage(String token) {

        String toUser = tokenUtil.parseTokenToUser(token).getUsername();
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("to_user", toUser);
        wrapper.eq("from_user", "SYSTEM");
        List<Message> messages = messageMapper.selectList(wrapper);
        ArrayList<GetSystemMessageVO> systemMessages = new ArrayList<>();
        for (Message message : messages) {
            systemMessages.add(new GetSystemMessageVO(message.getTitle(), message.getContent()));
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, systemMessages);

    }

}
