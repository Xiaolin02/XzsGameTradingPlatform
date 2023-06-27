package com.lin.utils;

import com.alibaba.fastjson.JSON;
import com.lin.common.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebUtil {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    public void renderString(HttpServletResponse response, String string) {
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
    public <T> void renderResponseResult(HttpServletResponse response, ResponseResult<T> result) {
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

    /**
     * @desc 获取请求接口的IP地址
     * @date 2023/6/27 10:51
     */
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * @desc 获取某个ip对某个接口的请求次数
     * @date 2023/6/27 11:03
     */
    public Integer getRequestNumber(String methodName, String IpAddress) {

        if(redisUtil.hasKey(IpAddress + " : " + methodName))
            return (Integer) redisUtil.get(IpAddress + " : " + methodName);
        else
            return 0;

    }

    /**
     * @desc 增加某个ip对某个接口的请求次数
     * @date 2023/6/27 11:30
     */
    public void addRequestNumber(String methodName, String IpAddress) {

        if(redisUtil.hasKey(IpAddress + " : " + methodName)) {
            redisUtil.set(IpAddress + " : " + methodName, (Integer) redisUtil.get(IpAddress + " : " + methodName) + 1);
        } else {
            redisUtil.set(IpAddress + " : " + methodName , 1);
        }

    }


}