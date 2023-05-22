package com.lin.handler;

import com.alibaba.fastjson.JSON;
import com.lin.common.ResponseResult;
import com.lin.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 林炳昌
 * @desc 自定义异常处理器
 * @date 2023年03月21日 15:05
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);

    }
}
