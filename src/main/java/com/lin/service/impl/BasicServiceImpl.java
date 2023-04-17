package com.lin.service.impl;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.RegisterDTO;
import com.lin.controller.DTO.UserDTO;
import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import com.lin.service.BasicService;
import com.lin.utils.RandomUtil;
import com.lin.utils.SendMsgUtil;
import com.lin.utils.TokenUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * @author 林炳昌
 * @desc BasicService实现类
 * @date 2023年04月16日 16:41
 */
@Service
public class BasicServiceImpl implements BasicService {

    //由于redis服务容易使服务器受到攻击,在类中临时使用一个code变量来保存手机验证码
    private static String code;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserMapper userMapper;

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

    @Override
    public ResponseResult getCode(String phone) throws ExecutionException, InterruptedException {
        code = SendMsgUtil.sendMsg(phone);
//        redisUtil.set("code",code);
//        HashMap<String, String> map = new HashMap<>();
//        map.put("phone",phone);
        return new ResponseResult<>(200,"验证码已发送到用户填写的手机号上，请注意查收");
    }

    @Override
    public ResponseResult register(RegisterDTO registerDTO) {

        if(!Objects.equals(registerDTO.getCode(),code)) {
            return new ResponseResult<>(403,"验证码错误");
        } else {
            User newUser = new User();
            Integer randomUserId = Integer.parseInt(RandomUtil.getNineBitRandom());
            String randomUsername = RandomStringUtils.randomAlphanumeric(10);
            if(randomUserId < 100000000)
                randomUserId += new Random().nextInt(9) + 1;
            newUser.setUserId(randomUserId);
            newUser.setUsername(randomUsername);
            newUser.setPhone(registerDTO.getPhone());
            newUser.setPassword(new BCryptPasswordEncoder().encode("123456"));
            userMapper.insert(newUser);
            HashMap<String, String> map = new HashMap<>();
            map.put("username", randomUsername);
            map.put("password", "123456");
            return new ResponseResult<>(200,"注册成功",map);
        }

    }


}
