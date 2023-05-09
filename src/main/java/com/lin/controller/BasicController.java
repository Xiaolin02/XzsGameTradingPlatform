package com.lin.controller;

import com.lin.common.CodeConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.*;
import com.lin.service.impl.BasicServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * @author 林炳昌
 * @desc 基础功能
 * @date 2023年04月16日 15:48
 */
@RestController
public class BasicController {

    @Autowired
    BasicServiceImpl basicService;

    /**
     * @desc 登录接口
     * @date 2023/4/16 19:26
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDTO userDTO) {
        return basicService.login(userDTO);
    }

    @PostMapping("/login/code")
    public ResponseResult codeLogin(@RequestBody CodeLoginDTO codeLoginDTO) {
        return basicService.codeLogin(codeLoginDTO);
    }

    /**
     * @desc 注册获取手机验证码接口
     * @date 2023/4/16 19:26
     */
    @GetMapping("/getCode")
    public ResponseResult getCode(@RequestBody GetCodeDTO getCodeDTO) throws ExecutionException, InterruptedException {
        if (!Objects.isNull(getCodeDTO.getPhone())) {
            if (!Pattern.matches("^1[3-9]\\d{9}$", getCodeDTO.getPhone()))
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "手机号格式错误");
        } else {
            return new ResponseResult(CodeConstants.CODE_PARAMETER_EMPTY, "手机号不得为空");
        }
        return basicService.getCode(getCodeDTO.getPhone(), getCodeDTO.getType());
    }

    /**
     * @desc 注册接口
     * @date 2023/4/16 20:54
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterDTO registerDTO) {

        //验证码固定四位
        if (!Objects.equals(registerDTO.getCode().length(), 4)) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "验证码错误");
        } else {
            return basicService.register(registerDTO);
        }

    }

    /**
     * @desc 忘记密码(重置密码)接口
     * @date 2023/4/19 21:27
     */
    @PostMapping("/forgetpwd")
    public ResponseResult forgetpwd(@RequestBody ForgetpwdDTO forgetpwdDTO) throws ExecutionException, InterruptedException {
        //验证码固定四位
        if (!Objects.equals(forgetpwdDTO.getCode().length(), 4)) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "验证码错误");
        } else {
            return basicService.forgetpwd(forgetpwdDTO);
        }
    }

    /**
     * @desc 联系我们接口, 从oss上下载qq个人二维码
     * @date 2023/4/20 16:28
     */
    @PostMapping("/contact")
    public void contact(HttpServletResponse response) {
        basicService.contact(response);
    }

}
