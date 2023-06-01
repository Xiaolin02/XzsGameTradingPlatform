package com.lin.controller;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author czh
 * @desc 余额模块
 * @date 2023/6/1 10:37
 */
@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {
    @Autowired
    BalanceService balanceService;

    /**
     * @desc 充值
     * @date 2023/6/1 10:51
     */
    @PutMapping("/recharge")
    public ResponseResult<NullData> recharge(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Integer money = (Integer) map.get("money");
        return balanceService.recharge(token, money);
    }

    /**
     * @desc 提现
     * @date 2023/6/1 10:51
     */
    @PutMapping("/withdraw")
    public ResponseResult<NullData> withdraw(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Integer money = (Integer) map.get("money");
        return balanceService.withdraw(token, money);
    }
}
