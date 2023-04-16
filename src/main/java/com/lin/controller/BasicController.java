package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.RegisterDTO;
import com.lin.controller.DTO.UserDTO;
import com.lin.service.impl.BasicServiceImpl;
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

    /**
     * @desc 注册时获取手机验证码接口
     * @date 2023/4/16 19:26
     */
    @GetMapping("/register")
    public ResponseResult getCode(@RequestHeader String phone) throws ExecutionException, InterruptedException {
        if (!Objects.isNull(phone)) {
            if(!Pattern.matches("^1[3-9]\\d{9}$", phone))
                return new ResponseResult<>(403,"手机号格式错误");
        } else {
            return new ResponseResult(403,"手机号不得为空");
        }
        return basicService.getCode(phone);
    }

    /**
     * @desc 注册接口
     * @date 2023/4/16 20:54
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterDTO registerDTO) {

        //验证码固定四位
        if(!Objects.equals(registerDTO.getCode().length(),4)) {
            return new ResponseResult<>(403,"验证码错误");
        } else {
            return basicService.register(registerDTO);
        }

    }

}
