package com.lin.common;

/**
 * @author 林炳昌
 * @desc 状态码常量
 * @date 2023年04月19日 21:50
 */
public interface CodeConstants {

    int CODE_SUCCESS = 200;                 // 成功
    int CODE_UNAUTHORIZED = 401;            // 授权错误，token错误
    int CODE_PARAMETER_ERROR = 402;         // 参数不合理
    int CODE_USER_EXCEPTION = 403;          // 用户异常
    int CODE_ACCESS_LIMIT = 403;            // 访问限制
    int CODE_NOT_FOUND = 404;               // 查询未找到
    int CODE_SERVER_ERROR = 500;            // 服务器错误

}
