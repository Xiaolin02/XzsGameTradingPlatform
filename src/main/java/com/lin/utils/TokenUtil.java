package com.lin.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.RedisKeyConstants;
import com.lin.mapper.MenuMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 林炳昌
 * @desc token工具类
 * @date 2023年02月21日 22:40
 */
@Component
public class TokenUtil {

    private static final String signature = "user";

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    MenuMapper menuMapper;

    /**
     * @desc 通过UserId获得token，自动设置过期时间
     * @date 2023/4/16 20:21
     */
    public String getTokenByUserId(Integer userId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder.setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("userId", userId)
                .claim("createAt", DateUtil.getDateTime())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        redisUtil.set(RedisKeyConstants.TOKEN_PREFIX + token, DateUtil.getDateTime(), RedisKeyConstants.TOKEN_MAX_UNREFRESHED_SECONDS);
        return token;
    }

    /**
     * @desc 刷新token过期时间
     * @date 2023/5/29 20:21
     */
    public void freshToken(String token) {
        redisUtil.set(RedisKeyConstants.TOKEN_PREFIX + token, DateUtil.getDateTime(), RedisKeyConstants.TOKEN_MAX_UNREFRESHED_SECONDS);
    }

    /**
     * @desc 解析token获得Claims对象
     * @date 2023/4/16 20:21
     */
    private Claims parseTokenToClaims(String token) {
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        return claimsJws.getBody();
    }

    /**
     * @desc 解析token获取createAt
     * @date 2023/5/29 20:21
     */
    public String parseTokenToCreateAt(String token) {
        Claims claims = parseTokenToClaims(token);
        return claims.get("createAt", String.class);
    }

    /**
     * @desc 解析token获取userId
     * @date 2023/5/16 20:21
     */
    // TODO 之后改成redis获取
    public Integer parseTokenToUserId(String token) {
//        // redis 获取，key格式为{token}:userId，value为userId
//        return (Integer) redisUtil.get(token + ":userId");
        Claims claims = parseTokenToClaims(token);
        return claims.get("userId", Integer.class);
    }

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
        Integer userId = parseTokenToUserId(token);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return userMapper.selectOne(wrapper);
    }

    /**
     * @desc 解析token获取userRole
     * @date 2023/5/16 20:21
     */
    // TODO 之后改成redis获取
    public List<String> parseTokenToUserRole(String token) {
//        // redis 获取，key格式为{token}:userId，value格式为","分割的字符串
        return menuMapper.selectPermsByUserId(parseTokenToUserId(token));
    }

}
