package com.lin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.VO.ChatListVO;
import com.lin.controller.VO.GetSystemMessageVO;
import com.lin.controller.WebSocketServer;
import com.lin.mapper.ChatListMapper;
import com.lin.mapper.MessageMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.ChatList;
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
    ChatListMapper chatListMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public ResponseResult<NullData> pushMsgToOneUser(String token, Integer toId, String content) throws IOException {

        if (CheckMsgUtil.checkMsg(content)) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "含有敏感信息");
        }
        WebSocketServer.sendInfo(content, toId);
        User fromUser = tokenUtil.parseTokenToUser(token);
        User toUser = userMapper.selectById(toId);
        Message message = new Message();
        message.setFromId(fromUser.getUserId());
        message.setFromUser(fromUser.getUsername());
        message.setToId(toUser.getUserId());
        message.setToUser(toUser.getUsername());
        message.setContent((String) JSONObject.parseObject(content).get("content"));
        message.setSendAt(DateUtil.getDateTime());
        messageMapper.insert(message);
        QueryWrapper<ChatList> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("first_user_id", fromUser.getUserId());
        wrapper1.eq("second_user_id", toUser.getUserId());
        QueryWrapper<ChatList> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("first_user_id", toUser.getUserId());
        wrapper2.eq("second_user_id", fromUser.getUserId());
        if(chatListMapper.selectCount(wrapper1) == 0) {
            chatListMapper.insert(new ChatList(fromUser.getUserId(), toUser.getUserId(), message.getContent()));
            chatListMapper.insert(new ChatList(toUser.getUserId(), fromUser.getUserId(), message.getContent()));
        } else {
            chatListMapper.update(new ChatList(fromUser.getUserId(), toUser.getUserId(), message.getContent()), wrapper1);
            chatListMapper.update(new ChatList(toUser.getUserId(), fromUser.getUserId(), message.getContent()), wrapper2);
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "无异常");

    }

    @Override
    public ResponseResult<NullData> pushSystemMsgToOneUser(String token, Integer toId, String content, String title) {

        User user = userMapper.selectById(toId);
        Message message = new Message();
        message.setToId(user.getUserId());
        message.setToUser(user.getUsername());
        message.setFromId(0);
        message.setFromUser("SYSTEM");
        message.setTitle(title);
        message.setContent(content);
        message.setSendAt(DateUtil.getDateTime());
        messageMapper.insert(message);
        QueryWrapper<ChatList> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("first_user_id", 0);
        wrapper1.eq("second_user_id", user.getUserId());
        QueryWrapper<ChatList> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("first_user_id", user.getUserId());
        wrapper2.eq("second_user_id", 0);
        if(chatListMapper.selectCount(wrapper1) == 0) {
            chatListMapper.insert(new ChatList(0, user.getUserId(), message.getContent()));
            chatListMapper.insert(new ChatList(user.getUserId(), 0, message.getContent()));
        } else {
            chatListMapper.update(new ChatList(0, user.getUserId(), message.getContent()), wrapper1);
            chatListMapper.update(new ChatList(user.getUserId(), 0, message.getContent()), wrapper2);
        }
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
        message.setToId(toUser.getUserId());
        message.setToUser(toUser.getUsername());
        message.setFromId(fromUser.getUserId());
        message.setFromUser(fromUser.getUsername());
        message.setContent(toStringContent);
        message.setSendAt(DateUtil.getDateTime());
        messageMapper.insert(message);
        QueryWrapper<ChatList> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("first_user_id", fromUser.getUserId());
        wrapper1.eq("second_user_id", toUser.getUserId());
        QueryWrapper<ChatList> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("first_user_id", toUser.getUserId());
        wrapper2.eq("second_user_id", fromUser.getUserId());
        if(chatListMapper.selectCount(wrapper1) == 0) {
            chatListMapper.insert(new ChatList(fromUser.getUserId(), toUser.getUserId(), message.getContent()));
            chatListMapper.insert(new ChatList(toUser.getUserId(), fromUser.getUserId(), message.getContent()));
        } else {
            chatListMapper.update(new ChatList(fromUser.getUserId(), toUser.getUserId(), message.getContent()), wrapper1);
            chatListMapper.update(new ChatList(toUser.getUserId(), fromUser.getUserId(), message.getContent()), wrapper2);
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "无异常");
    }

    @Override
    public ResponseResult<List<String>> getMessage(String token, Integer toId) {

        Integer userId = tokenUtil.parseTokenToUserId(token);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("from_id", userId).eq("to_id", toId);
        wrapper.or().eq("to_id", userId).eq("from_id", toId);
        List<Message> messageList = messageMapper.selectList(wrapper);
        ArrayList<String> messages = new ArrayList<>();
        for (Message message : messageList) {
            messages.add(message.getContent());
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, messages);

    }

    @Override
    public ResponseResult<List<GetSystemMessageVO>> getSystemMessage(String token) {

        User toUser = tokenUtil.parseTokenToUser(token);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("to_id", toUser.getUserId());
        wrapper.eq("from_user", "SYSTEM");
        List<Message> messages = messageMapper.selectList(wrapper);
        ArrayList<GetSystemMessageVO> systemMessages = new ArrayList<>();
        for (Message message : messages) {
            systemMessages.add(new GetSystemMessageVO(message.getTitle(), message.getContent()));
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, systemMessages);

    }

    @Override
    public ResponseResult<List<ChatListVO>> getList(String token) {

        User user = tokenUtil.parseTokenToUser(token);
        QueryWrapper<ChatList> wrapper = new QueryWrapper<>();
        wrapper.eq("first_user_id", user.getUserId());
        List<ChatList> chatLists = chatListMapper.selectList(wrapper);
        ArrayList<ChatListVO> chatListVOS = new ArrayList<>();
        for (ChatList chatList : chatLists) {
            User secondUser = userMapper.selectById(chatList.getSecondUserId());
            String pictureUrl = userMapper.getPictureUrl(secondUser.getUserId());
            chatListVOS.add(new ChatListVO(pictureUrl, secondUser.getUsername(), chatList.getLastMessage()));
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, chatListVOS);

    }

}
