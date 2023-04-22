package com.lin.service;

import com.lin.common.ResponseResult;

import java.io.IOException;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月22日 10:18
 */
public interface MessageService {

    ResponseResult pushMsgToOneUser(String token, Integer toId, String content) throws IOException;

    ResponseResult pushSystemMsgToOneUser(String token, Integer toId, String content, String title);

}
