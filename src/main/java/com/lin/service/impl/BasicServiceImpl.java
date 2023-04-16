package com.lin.service.impl;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.UserDTO;
import com.lin.pojo.LoginUser;
import com.lin.service.BasicService;
import com.lin.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author 林炳昌
 * @desc BasicService实现类
 * @date 2023年04月16日 16:41
 */
@Service
public class BasicServiceImpl implements BasicService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(UserDTO userDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }

        String jwt = TokenUtil.getToken(userDTO.getUsername());
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);

        return new ResponseResult(200,"登陆成功",map);

    }


}
