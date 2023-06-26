package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.common.UserStatusConstants;
import com.lin.controller.DTO.CodeLoginDTO;
import com.lin.controller.DTO.ForgetpwdDTO;
import com.lin.controller.DTO.RegisterDTO;
import com.lin.controller.DTO.user.LoginUserDTO;
import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import com.lin.service.BasicService;
import com.lin.utils.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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

    static Integer MAX_NUMBER_OF_FAIL = 6;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SendMsgUtil sendMsgUtil;

    @Autowired
    OssUtil ossUtil;

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public ResponseResult<Map<String, String>> login(LoginUserDTO loginUserDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword());
        try{
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", loginUserDTO.getUsername());
            User user = userMapper.selectOne(wrapper);
            if(user.getStatus() == UserStatusConstants.STATUS_FREEZE)
                return new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "该用户已经被冻结");
            authenticationManager.authenticate(authenticationToken);
            if(redisUtil.hasKey("fail_login : " + loginUserDTO.getUsername()))
                redisUtil.delete("fail_login : " + loginUserDTO.getUsername());
            String jwt = tokenUtil.getTokenByUserId(user.getUserId());
            HashMap<String, String> map = new HashMap<>();
            map.put("token", jwt);
            return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "登陆成功", map);
        } catch (AuthenticationException e) {
            if(redisUtil.hasKey("fail_login : " + loginUserDTO.getUsername())) {
                redisUtil.set("fail_login : " + loginUserDTO.getUsername(), (Integer) redisUtil.get("fail_login : " + loginUserDTO.getUsername()) + 1);
                if((Integer) redisUtil.get("fail_login : " + loginUserDTO.getUsername()) > MAX_NUMBER_OF_FAIL) {
                    QueryWrapper<User> wrapper = new QueryWrapper<>();
                    wrapper.eq("username", loginUserDTO.getUsername());
                    User user = userMapper.selectOne(wrapper);
                    user.setStatus(UserStatusConstants.STATUS_FREEZE);
                    userMapper.updateById(user);
                    return new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "该用户已达最大密码错误次数,已经被暂时冻结账户");
                }
            } else {
                redisUtil.set("fail_login : " + loginUserDTO.getUsername(), 1);
            }
        }

        return new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "登陆失败");

    }

    @Override
    public ResponseResult<NullData> getCode(String phone, String type) throws ExecutionException, InterruptedException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        if (Objects.equals("register", type)) {
            User user = userMapper.selectOne(wrapper);
            if (!Objects.isNull(user)) {
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "该手机号已经被注册");
            }
        }
        String code = sendMsgUtil.sendMsg(phone);
        redisUtil.set(type + ":" + phone, code);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "验证码已发送到用户填写的手机号上，请注意查收");
    }

    @Override
    public ResponseResult<Map<String, String>> register(RegisterDTO registerDTO) {

        if (!Objects.equals(registerDTO.getCode(), redisUtil.get("register:" + registerDTO.getPhone()))) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "验证码错误");
        } else {
            User newUser = new User();
            int randomUserId = Integer.parseInt(RandomUtil.getNineBitRandom());
            String randomUsername = RandomStringUtils.randomAlphanumeric(10);
            if (randomUserId < 100000000) {
                randomUserId += new Random().nextInt(9) + 1;
            }
            newUser.setUserId(randomUserId);
            newUser.setUsername(randomUsername);
            newUser.setPhone(registerDTO.getPhone());
            newUser.setPassword(PasswordEncodingUtil.encoding("123456"));
            newUser.setBalance(0);
            newUser.setTransactionsNumber(0);
            newUser.setSuccessNumber(0);
            newUser.setStatus(UserStatusConstants.STATUS_NORMAL);
            userMapper.insert(newUser);
            HashMap<String, String> map = new HashMap<>();
            map.put("username", randomUsername);
            map.put("password", "123456");
            return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "注册成功", map);
        }

    }

    @Override
    public ResponseResult<NullData> forgetpwd(ForgetpwdDTO forgetpwdDTO) {

        if (!Objects.equals(redisUtil.get("forgetPwd:" + forgetpwdDTO.getPhone()), forgetpwdDTO.getCode())) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "验证码错误");
        } else {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", forgetpwdDTO.getPhone());
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("password", PasswordEncodingUtil.encoding("123456"));
            userMapper.update(userMapper.selectOne(queryWrapper), updateWrapper);
            return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "密码已经重置为123456");
        }

    }

    @Override
    public void contact(HttpServletResponse response) {

        ossUtil.downFile("https://xzs-gametradingplatform.oss-cn-shenzhen.aliyuncs.com/Xiaolin/QQ/MyQQ.png", response);

    }

    @Override
    public ResponseResult<Map<String, String>> codeLogin(CodeLoginDTO codeLoginDTO) {

        if (!Objects.equals(redisUtil.get("codeLogin:" + codeLoginDTO.getPhone()), codeLoginDTO.getCode())) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "验证码错误");
        } else {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("phone", codeLoginDTO.getPhone());
            User user = userMapper.selectOne(wrapper);
            String jwt = tokenUtil.getTokenByUserId(user.getUserId());
            HashMap<String, String> map = new HashMap<>();
            map.put("token", jwt);
            return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "登陆成功", map);
        }

    }

    @Override
    public String test() {
        User user = userMapper.selectById(1);
        return user.toString();
    }

}
