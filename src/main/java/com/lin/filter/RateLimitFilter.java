package com.lin.filter;

import com.lin.common.CodeConstants;
import com.lin.common.RedisKeyConstants;
import com.lin.common.ResponseResult;
import com.lin.mapper.MenuMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.LoginUser;
import com.lin.pojo.User;
import com.lin.utils.RedisUtil;
import com.lin.utils.TokenUtil;
import com.lin.utils.WebUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author czh
 * @desc 访问次数限制过滤器
 * @date 2023年07月3日 19:58
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    WebUtil webUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        // 限制ip访问频率
        String ip = request.getRemoteAddr();
        if (doLimit(RedisKeyConstants.IP_ACCESS_COUNT_PREFIX + ip, RedisKeyConstants.IP_ACCESS_COUNT_EXPIRE_TIME_SECONDS, RedisKeyConstants.IP_ACCESS_COUNT_MAX_COUNT)) {
            webUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_ACCESS_LIMIT, "Access limit reached for IP"));
            return;
        }

        // 限制userId访问频率
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        Integer userId = tokenUtil.parseTokenToUserId(token);
        if (doLimit(RedisKeyConstants.USER_ACCESS_COUNT_PREFIX + userId, RedisKeyConstants.USER_ACCESS_COUNT_EXPIRE_TIME_SECONDS, RedisKeyConstants.USER_ACCESS_COUNT_MAX_COUNT)) {
            webUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_ACCESS_LIMIT, "Access limit reached for userId"));
            return;
        }

        User user = tokenUtil.parseTokenToUser(token);

        LoginUser loginUser = new LoginUser(user, menuMapper.selectPermsByUserId(user.getUserId()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }

    /**
     * 访问次数限制
     * @param key redis中的key
     * @param expireTime 过期时间
     * @param maxCount 最大访问次数
     * @desc 第一次访问设置过期时间，每次访问count+1
     * @return 达到最大访问次数后返回true，未达到则false
     */
    private boolean doLimit(String key, int expireTime, int maxCount) {
        Long countNow = redisUtil.incr(key);
        if (countNow == null) {
            // incr无key则自动设置0并自增，所以一般不会进入这个分支，仅以防万一
            redisUtil.set(key, 1, expireTime);
            return false;
        }
        // 设置过期时间
        if (redisUtil.ttl(key) == -1) {
            redisUtil.set(key, Math.toIntExact(countNow), expireTime);
        }
        // 判断是否达到最大访问次数
        return countNow >= maxCount;
    }
}
