package com.lin.common;

/**
 * @Author czh
 * @desc redis的key常量
 * @date 2023/5/19 11:16
 */
public interface RedisKeyConstants {
    String CODE_PASSWORD_MODIFY_PREFIX = "code:passwordModify:";
    String CODE_PHONE_MODIFY_PREFIX = "code:phoneModify:";
    int CODE_EXPIRE_TIME = 60 * 5;
}
