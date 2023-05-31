package com.lin.filter;

import com.lin.common.CodeConstants;
import com.lin.common.RedisKeyConstants;
import com.lin.common.ResponseResult;
import com.lin.mapper.MenuMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.LoginUser;
import com.lin.pojo.User;
import com.lin.utils.DateUtil;
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
            WebUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "无token"));
            return;
        }

        // 检查token是否有效
        String tokenFreshAt = tokenUtil.getFreshAtByToken(token);
        if (tokenFreshAt == null) {
            // 传递信息，无效的token
            WebUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "无效的token"));
            return;
        }

        // 检查token的未刷新时间
        if (tokenFreshAt.equals("")) {
            logger.error("服务端错误，redis未存储token刷新时间");
            WebUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_SERVER_ERROR, "服务端错误，redis未存储token刷新时间"));
            return;
        }
        long freshDuration;
        try {
            freshDuration = DateUtil.getDurationSecondsUntilNow(tokenFreshAt);
        } catch (ParseException e) {
            logger.error("服务端错误，redis存储时间格式错误");
            WebUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_SERVER_ERROR, "服务端错误，redis存储时间格式错误"));
            return;
        }
        if (freshDuration > RedisKeyConstants.TOKEN_MAX_UNREFRESHED_SECONDS) {
            WebUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "token已失效，达到最大未刷新时间，请重新登录"));
            return;
        }

        // 检查token中的用户信息
        User user = tokenUtil.parseTokenToUser(token);
        if (user == null) {
            WebUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "无效的token，没有这个用户"));
            return;
        }

        // 检查token的持续有效时间
        String tokenCreateAt = tokenUtil.parseTokenToCreateAt(token);
        if (tokenCreateAt == null || tokenCreateAt.equals("")) {
            logger.error("服务端错误，token未存储创建时间");
            WebUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_SERVER_ERROR, "服务端错误，token未存储创建时间"));
            return;
        }

        LoginUser loginUser = new LoginUser(user, menuMapper.selectPermsByUserId(user.getUserId()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
