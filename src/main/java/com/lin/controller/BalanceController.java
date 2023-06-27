package com.lin.controller;

import com.lin.common.CodeConstants;
import com.lin.common.NullData;
import com.lin.common.ParameterConstants;
import com.lin.common.ResponseResult;
import com.lin.service.BalanceService;
import com.lin.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    WebUtil webUtil;

    /**
     * @desc 充值
     * @date 2023/6/1 10:51
     */
    @PutMapping("/recharge")
    public ResponseResult<NullData> recharge(HttpServletRequest request, @RequestHeader String token, @RequestBody Map<String, Object> map) {
        String ipAddress = webUtil.getIpAddress(request);
        Integer requestNumber = webUtil.getRequestNumber(Thread.currentThread().getStackTrace()[1].getMethodName(), ipAddress);
        if(requestNumber >= ParameterConstants.MAX_REQUEST_NUMBER) {
            return new ResponseResult<>(CodeConstants.CODE_USER_EXCEPTION, "该Ip短期内对该接口进行大量请求, 已经被限制访问该接口");
        } else {
            webUtil.addRequestNumber(Thread.currentThread().getStackTrace()[1].getMethodName(), ipAddress);
        }
        Integer money = (Integer) map.get("money");
        return balanceService.recharge(token, money);
    }

    /**
     * @desc 提现
     * @date 2023/6/1 10:51
     */
    @PutMapping("/withdraw")
    public ResponseResult<NullData> withdraw(HttpServletRequest request, @RequestHeader String token, @RequestBody Map<String, Object> map) {
        String ipAddress = webUtil.getIpAddress(request);
        Integer requestNumber = webUtil.getRequestNumber(Thread.currentThread().getStackTrace()[1].getMethodName(), ipAddress);
        if(requestNumber >= ParameterConstants.MAX_REQUEST_NUMBER) {
            return new ResponseResult<>(CodeConstants.CODE_USER_EXCEPTION, "该Ip短期内对该接口进行大量请求, 已经被限制访问该接口");
        } else {
            webUtil.addRequestNumber(Thread.currentThread().getStackTrace()[1].getMethodName(), ipAddress);
        }
        Integer money = (Integer) map.get("money");
        return balanceService.withdraw(token, money);
    }
}
