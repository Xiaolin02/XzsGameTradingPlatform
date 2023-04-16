package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.UserDTO;

/**
 * @author 林炳昌
 * @desc 基础功能
 * @date 2023年04月16日 16:40
 */
public interface BasicService {

    ResponseResult login(UserDTO userDTO);

}
