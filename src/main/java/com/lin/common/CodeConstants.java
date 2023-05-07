package com.lin.common;

/**
 * @author 林炳昌
 * @desc 状态码常量
 * @date 2023年04月19日 21:50
 */
public interface CodeConstants {

    int CODE_SUCCESS = 200;                 // 成功
    int CODE_PARAMETER_ERROR = 401;         // 参数错误
    int CODE_PARAMETER_EMPTY = 402;         // 参数为空
    int CODE_NOT_FOUND = 404;               // 查询未找到
    int CODE_CONFLICT = 409;                // 重复添加记录，或者删除不存在的记录
    int CODE_INSUFFICIENT_BALANCE = 501;    // 余额不足

}
