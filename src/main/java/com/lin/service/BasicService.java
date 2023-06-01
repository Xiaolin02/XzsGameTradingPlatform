package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.*;
import com.lin.controller.DTO.user.LoginUserDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author 林炳昌
 * @desc 基础功能
 * @date 2023年04月16日 16:40
 */
public interface BasicService {

    ResponseResult<Map<String, String>> login(LoginUserDTO loginUserDTO);

    ResponseResult<NullData> getCode(String phone, String type) throws ExecutionException, InterruptedException;

    ResponseResult<Map<String, String>> register(RegisterDTO registerDTO);

    ResponseResult<NullData> forgetpwd(ForgetpwdDTO forgetpwdDTO);

    void contact(HttpServletResponse response);

    ResponseResult<Map<String, String>> codeLogin(CodeLoginDTO codeLoginDTO);

    String test();
}
