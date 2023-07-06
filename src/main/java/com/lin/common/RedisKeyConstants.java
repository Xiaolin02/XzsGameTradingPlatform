package com.lin.common;

/**
 * @Author czh
 * @desc redis的key常量
 * @date 2023/5/19 11:16
 */
public interface RedisKeyConstants {
    // CODE的key，key为手机号，value为四位数字的code

    // 修改密码的验证码的key前缀
    String CODE_PASSWORD_MODIFY_PREFIX = "code:passwordModify:";
    // 修改手机号的验证码的key前缀
    String CODE_PHONE_MODIFY_PREFIX = "code:phoneModify:";
    // 验证码过期时间5分钟
    int CODE_EXPIRE_TIME_SECONDS = 60 * 5;

    // token的key，key为token，value为最后使用该token的时间

    // token的key前缀
    String TOKEN_PREFIX = "token:";
    // token未刷新过期时间2小时
    int TOKEN_MAX_UNREFRESHED_SECONDS = 60 * 60 * 2;
    // token最大持续时间24小时
    int TOKEN_MAX_DURATION_SECONDS = 60 * 60 * 24;
    // token延迟删除时间20分钟（删除缓冲时间）
    int TOKEN_DEFERRED_DELETE_SECONDS = 60 * 20;

    // 用户的访问次数的key，key为userId，value为访问次数

    // 用户访问次数的key前缀
    String USER_ACCESS_COUNT_PREFIX = "user:accessCount:";
    // 限制访问次数的时间5分钟
    int USER_ACCESS_COUNT_EXPIRE_TIME_SECONDS = 60 * 1;
    // 限制时间内用户的最大访问次数100次
    int USER_ACCESS_COUNT_MAX_COUNT = 2000;

    // IP的访问次数的key，key为userId，value为访问次数

    // IP访问次数的key前缀
    String IP_ACCESS_COUNT_PREFIX = "ip:accessCount:";
    // 限制访问次数的时间5分钟
    int IP_ACCESS_COUNT_EXPIRE_TIME_SECONDS = 60 * 1;
    // 限制时间内IP的最大访问次数100次
    int IP_ACCESS_COUNT_MAX_COUNT = 2000;
}
