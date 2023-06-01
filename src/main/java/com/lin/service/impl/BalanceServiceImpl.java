package com.lin.service.impl;

import com.lin.common.CodeConstants;
import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import com.lin.service.BalanceService;
import com.lin.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author czh
 * @desc 余额服务
 * @date 2023/6/1 10:38
 */
@Service
public class BalanceServiceImpl implements BalanceService {
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserMapper userMapper;

    /**
     * @desc 充值
     * @date 2023/6/1 10:51
     */
    @Override
    public ResponseResult<NullData> recharge(String token, Integer money) {
        Integer userId = tokenUtil.parseTokenToUserId(token);
        User user = userMapper.selectById(userId);
        user.setBalance(user.getBalance() + money);
        userMapper.updateById(user);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "充值成功");
    }

    /**
     * @desc 提现
     * @date 2023/6/1 10:51
     */
    @Override
    public ResponseResult<NullData> withdraw(String token, Integer money) {
        Integer userId = tokenUtil.parseTokenToUserId(token);
        User user = userMapper.selectById(userId);
        if (user.getBalance() < money) {
            return new ResponseResult<>(CodeConstants.CODE_INSUFFICIENT_BALANCE, "余额不足");
        }
        user.setBalance(user.getBalance() - money);
        userMapper.updateById(user);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "提现成功");
    }
}
