package com.lin.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年05月05日 20:13
 */
@Component
public class ParseTokenUtil {

    @Autowired
    UserMapper userMapper;

    /**
     * @desc 解析token获取user对象
     * @date 2023/5/5 20:21
     */
    public User parseTokenToGetUser(String token) {
        Claims claims = TokenUtil.parseToken(token);
        String username = claims.get("username").toString();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

}
