package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;

/**
 * @Author czh
 * @desc 余额服务
 * @date 2023/6/1 10:38
 */
public interface BalanceService {
    ResponseResult<NullData> recharge(String token, Integer money);
    ResponseResult<NullData> withdraw(String token, Integer money);
}
