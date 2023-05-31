package com.lin.utils;

import com.alibaba.fastjson.JSON;
import com.lin.common.ResponseResult;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class WebUtil {
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 将ResponseResult渲染到客户端
     *
     * @param response 渲染对象
     * @param result   待渲染的ResponseResult
     */
    public static <T> void renderResponseResult(HttpServletResponse response, ResponseResult<T> result) {
        String json = JSON.toJSONString(result);
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}