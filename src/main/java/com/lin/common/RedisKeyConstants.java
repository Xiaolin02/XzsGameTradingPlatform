package com.lin.common;

/**
 * @Author czh
 * @desc redis的key常量
 * @date 2023/5/19 11:16
 */
public interface RedisKeyConstants {
    // CODE的key，value为四位数字的code

    // 修改密码的验证码的key前缀
    String CODE_PASSWORD_MODIFY_PREFIX = "code:passwordModify:";
    // 修改手机号的验证码的key前缀
    String CODE_PHONE_MODIFY_PREFIX = "code:phoneModify:";
    // 验证码过期时间5分钟
    int CODE_EXPIRE_TIME_SECONDS = 60 * 5;

    // token的key，value为最后使用该token的时间

    // token的key前缀
    String TOKEN_PREFIX = "token:";
    // token未刷新过期时间2小时
    int TOKEN_MAX_UNREFRESHED_SECONDS = 60 * 60 * 2;
    // token最大持续时间24小时
    int TOKEN_MAX_DURATION_SECONDS = 60 * 60 * 24;

}
