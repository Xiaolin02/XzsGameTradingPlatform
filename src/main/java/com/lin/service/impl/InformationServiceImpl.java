package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.*;
import com.lin.controller.DTO.user.UserCompleteDTO;
import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import com.lin.service.InformationService;
import com.lin.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @Author czh
 * @desc 基本信息服务
 * @date 2023/5/17 10:54
 */
@Service
public class InformationServiceImpl implements InformationService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ParseTokenUtil parseTokenUtil;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    SendMsgUtil sendMsgUtil;
    @Autowired
    OssUtil ossUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * @Author czh
     * @desc 修改用户名
     * @date 2023/5/17 10:46
     */
    @Override
    public ResponseResult<Object> usernameModify(String token, String newUsername) {
        if (newUsername == null || newUsername.equals("")) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "新用户名不能为空");
        }
        Integer userId = parseTokenUtil.parseTokenToUserId(token);
        User user = userMapper.selectById(userId);
        if (newUsername.equals(user.getUsername())) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "新用户名不能和原用户名重复");
        }
        if (userMapper.selectOne(new QueryWrapper<User>().eq("username", newUsername)) != null) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "要修改的用户名已被使用");
        }
        user.setUsername(newUsername);
        userMapper.updateById(user);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "修改用户名成功");
    }

    /**
     * @Author czh
     * @desc 修改密码 通过旧密码
     * @date 2023/5/17 10:46
     */
    @Override
    public ResponseResult<Object> passwordModifyByPassword(String token, String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.equals("")) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "原密码不能为空");
        }
        if (newPassword == null || newPassword.equals("")) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "新密码不能为空");
        }
        if (oldPassword.equals(newPassword)) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "新密码不能和原密码重复");
        }
        if (PasswordConstants.isPasswordValid(oldPassword)) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "旧密码格式错误：" + PasswordConstants.RULE_TIP);
        }
        if (PasswordConstants.isPasswordValid(newPassword)) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "新密码格式错误：" + PasswordConstants.RULE_TIP);
        }
        Integer userId = parseTokenUtil.parseTokenToUserId(token);
        User user = userMapper.selectById(userId);
        // 校验密码
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), oldPassword));
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("修改密码失败");
        }
        user.setPassword(PasswordEncodingUtil.encoding(newPassword));
        userMapper.updateById(user);
        // TODO 修改密码后需要重新登录认证？
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "修改密码成功");
    }

    /**
     * @Author czh
     * @desc 修改密码 通过手机验证码
     * @date 2023/5/17 10:46
     */
    @Override
    public ResponseResult<Object> passwordModifyByPhone(String token, String code, String newPassword) {
        if (newPassword == null || newPassword.equals("")) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "新密码不能为空");
        }
        if (PasswordConstants.isPasswordValid(newPassword)) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "新密码格式错误：" + PasswordConstants.RULE_TIP);
        }
        User user = parseTokenUtil.parseTokenToUser(token);
        if (!Objects.equals(code, redisUtil.get(RedisKeyConstants.CODE_PASSWORD_MODIFY_PREFIX + user.getPhone()).toString())) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "验证码错误");
        }
        if (PasswordEncodingUtil.matches(newPassword, user.getPassword())) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "新密码不能和原密码重复");
        }
        // TODO 重新登录认证？
        user.setPassword(PasswordEncodingUtil.encoding(newPassword));
        userMapper.updateById(user);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "修改密码成功");
    }

    /**
     * @Author czh
     * @desc 修改密码 通过手机验证码 获取验证码(5分钟有效)
     * @date 2023/5/17 10:46
     */
    @Override
    public ResponseResult<Object> passwordModifyByPhoneGetCode(String token) throws ExecutionException, InterruptedException {
        User user = parseTokenUtil.parseTokenToUser(token);
        String phone = user.getPhone();
        String code = sendMsgUtil.sendMsg(phone);
        redisUtil.set(RedisKeyConstants.CODE_PASSWORD_MODIFY_PREFIX + phone, code, RedisKeyConstants.CODE_EXPIRE_TIME);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "验证码已发送到用户填写的手机号上，请注意查收");
    }

    /**
     * @Author czh
     * @desc 修改手机号
     * @date 2023/5/17 10:46
     */
    @Override
    public ResponseResult<Object> phoneModify(String token, String newPhoneNumber, String newPhoneCode) {
        User user = parseTokenUtil.parseTokenToUser(token);
        if (!Objects.equals(newPhoneCode, redisUtil.get(RedisKeyConstants.CODE_PHONE_MODIFY_PREFIX + newPhoneNumber).toString())) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "验证码错误");
        }
        user.setPhone(newPhoneNumber);
        userMapper.updateById(user);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "修改手机号成功");
    }

    /**
     * @Author czh
     * @desc 修改手机号(获取验证码)(5分钟有效)
     * @date 2023/5/17 10:46
     */
    @Override
    public ResponseResult<Object> phoneModifyGetCode(String token, String newPhoneNumber) throws ExecutionException, InterruptedException {
        String code = sendMsgUtil.sendMsg(newPhoneNumber);
        redisUtil.set(RedisKeyConstants.CODE_PHONE_MODIFY_PREFIX + newPhoneNumber, code, RedisKeyConstants.CODE_EXPIRE_TIME);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "验证码已发送到用户填写的手机号上，请注意查收");
    }

    /**
     * @Author czh
     * @desc 修改头像
     * @date 2023/5/17 10:46
     */
    @Override
    public ResponseResult<Object> modifyPicture(String token, MultipartFile file) {
        if (file == null) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "文件不能为空");
        }
        Integer userId = parseTokenUtil.parseTokenToUserId(token);
        String url = ossUtil.uploadfile(file, userId, OssFilePathConstants.USER_PICTURE_TYPE);

        userMapper.replacePictureUrl(userId, url);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "修改头像成功");
    }

    /**
     * @Author czh
     * @desc 查看个人信息，自己查看自己的主页
     * @date 2023/5/17 10:46
     */
    @Override
    public ResponseResult<Object> viewInformation(String token) {
        User user = parseTokenUtil.parseTokenToUser(token);
        UserCompleteDTO userCompleteDTO = new UserCompleteDTO();
        userCompleteDTO.setUserId(user.getUserId());
        userCompleteDTO.setUsername(user.getUsername());
        userCompleteDTO.setPictureUrl(userMapper.getPictureUrl(user.getUserId()));
        userCompleteDTO.setPhone(user.getPhone());
        userCompleteDTO.setBalance(user.getBalance());
        userCompleteDTO.setRegisterAt(user.getRegisterAt());
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, userCompleteDTO);
    }
}
