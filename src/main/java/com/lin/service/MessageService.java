package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.VO.ChatListVO;
import com.lin.controller.VO.GetSystemMessageVO;

import java.io.IOException;
import java.util.List;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月22日 10:18
 */
public interface MessageService {

    ResponseResult<NullData> pushMsgToOneUser(String token, Integer toId, String content) throws IOException;

    ResponseResult<NullData> pushSystemMsgToOneUser(String token, Integer toId, String content, String title);

    ResponseResult<NullData> pushMsg(String token, Integer toId, String content) throws IOException;

    ResponseResult<List<String>> getMessage(String token, Integer toId);

    ResponseResult<List<GetSystemMessageVO>> getSystemMessage(String token);

    ResponseResult<List<ChatListVO>> getList(String token);
}
