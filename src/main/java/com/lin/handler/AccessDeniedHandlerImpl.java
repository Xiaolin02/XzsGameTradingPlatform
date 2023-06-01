package com.lin.handler;

import com.alibaba.fastjson.JSON;
import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 林炳昌
 * @desc 自定义权限异常处理器
 * @date 2023年03月21日 15:08
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ResponseResult<NullData> result = new ResponseResult<>(HttpStatus.FORBIDDEN.value(), "用户权限不足");
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);

    }
}
