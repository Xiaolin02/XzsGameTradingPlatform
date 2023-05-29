package com.lin.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年05月05日 20:13
 */
@Component
public class ParseTokenUtil {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    /**
     * @desc 解析token获取user对象
     * @date 2023/5/16 20:21
     */
    // TODO 之后改成redis获取
    public User parseTokenToUser(String token) {
//        // redis 获取，key格式为{token}:userId，value为userId
//        Integer userId = (Integer) redisUtil.get(token + ":userId");
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("userId", userId);
//        return userMapper.selectOne(wrapper);
        Claims claims = TokenUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return userMapper.selectOne(wrapper);
    }

    /**
     * @desc 解析token获取userId
     * @date 2023/5/16 20:21
     */
    // TODO 之后改成redis获取
    public Integer parseTokenToUserId(String token) {
//        // redis 获取，key格式为{token}:userId，value为userId
//        return (Integer) redisUtil.get(token + ":userId");
        Claims claims = TokenUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return userMapper.selectOne(wrapper).getUserId();
    }

    /**
     * @desc 解析token获取userRole
     * @date 2023/5/16 20:21
     */
    // TODO 之后改成redis获取
    public List<String> parseTokenToUserRole(String token) {
//        // redis 获取，key格式为{token}:userId，value格式为","分割的字符串
//        return new ArrayList<>(Arrays.asList(redisUtil.get(token + ":userRole").toString().split(",")));
        // 暂时伪造返回
        List<String> permissions = new ArrayList<>();
        permissions.add("roleUser");
        permissions.add("roleManger");
        return permissions;
    }


}
