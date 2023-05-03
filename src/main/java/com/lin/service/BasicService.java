package com.lin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ForgetpwdDTO;
import com.lin.controller.DTO.OfferDTO;
import com.lin.controller.DTO.RegisterDTO;
import com.lin.controller.DTO.UserDTO;
import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import com.lin.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.ExecutionException;

/**
 * @author 林炳昌
 * @desc 基础功能
 * @date 2023年04月16日 16:40
 */
public interface BasicService {

    ResponseResult login(UserDTO userDTO);

    ResponseResult getCode(String phone) throws ExecutionException, InterruptedException;

    ResponseResult register(RegisterDTO registerDTO);

    ResponseResult forgetpwd(String phone) throws ExecutionException, InterruptedException;

    ResponseResult forgetpwd(ForgetpwdDTO forgetpwdDTO);

    void contact(HttpServletResponse response);

    static Integer getUserIdByToken(UserMapper userMapper, String token) {
        Claims claims = TokenUtil.parseToken(token);
        String username = claims.get("username").toString();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        return user.getUserId();
    }
}
