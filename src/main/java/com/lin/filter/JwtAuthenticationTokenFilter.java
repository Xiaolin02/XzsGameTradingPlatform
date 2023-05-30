package com.lin.filter;

import com.lin.common.RedisKeyConstants;
import com.lin.mapper.MenuMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.LoginUser;
import com.lin.pojo.User;
import com.lin.utils.DateUtil;
import com.lin.utils.RedisUtil;
import com.lin.utils.TokenUtil;
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
import java.text.ParseException;

/**
 * @author 林炳昌
 * @desc 身份认证过滤器
 * @date 2023年03月14日 16:17
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 确保有token
        String token = request.getHeader("token");

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            // TODO 传递信息，无token
            return;
        }

        // 检查token是否有效
        String tokenFreshAt = (String) redisUtil.get(RedisKeyConstants.TOKEN_PREFIX + token);
        if (tokenFreshAt == null) {
            // TODO 传递信息，无效的token
            return;
        }

        // 检查token的未刷新时间
        if (tokenFreshAt.equals("")) {
            throw new RuntimeException("服务端错误，redis未存储token刷新时间");
        }
        long freshDuration;
        try {
            freshDuration = DateUtil.getDurationSecondsUntilNow(tokenFreshAt);
        } catch (ParseException e) {
            throw new RuntimeException("服务端错误，redis存储时间格式错误");
        }
        if (freshDuration > RedisKeyConstants.TOKEN_MAX_UNREFRESHED_SECONDS) {
            // TODO 传递信息，达到最大未刷新时间，请重新登录
            return;
        }

        // 检查token中的用户信息
        User user = tokenUtil.parseTokenToUser(token);
        if (user == null) {
            // TODO 传递信息，无效的token，没有这个用户
            return;
        }

        // 检查token的持续有效时间
        String tokenCreateAt = tokenUtil.parseTokenToCreateAt(token);
        if (tokenCreateAt == null || tokenCreateAt.equals("")) {
            throw new RuntimeException("服务端错误，token未存储创建时间");
        }
        long createDuration;
        try {
            createDuration = DateUtil.getDurationSecondsUntilNow(tokenCreateAt);
        } catch (ParseException e) {
            throw new RuntimeException("服务端错误，token存储时间格式错误");
        }
//        if (createDuration > RedisKeyConstants.TOKEN_MAX_DURATION_SECONDS) {
//            // TODO 传递信息，达到最大持续时间，请接收新的token
//            String newToken = tokenUtil.getTokenByUserId(user.getUserId());
//            // TODO controller接收token
//
//            request.setAttribute("token", newToken);
//            response.setHeader("token", newToken);
//            return;
//        }

        LoginUser loginUser = new LoginUser(user, menuMapper.selectPermsByUserId(user.getUserId()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
