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

    @Autowired
    WebUtil webUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 确保有token
        String token = request.getHeader("token");
//        String ip = request.getRemoteAddr();
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 检查token是否有效
        String tokenLastRefreshAt = tokenUtil.getRefreshAtByToken(token);
        if (tokenLastRefreshAt == null) {
            webUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "无效的token"));
            return;
        }

        // 检查token的未刷新时间
        if ("".equals(tokenLastRefreshAt)) {
            logger.error("服务端错误，redis未存储token刷新时间");
            webUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_SERVER_ERROR, "服务端错误，redis未存储token刷新时间"));
            return;
        }
        long tokenDurationSinceLastRefresh;
        try {
            tokenDurationSinceLastRefresh = DateUtil.getDurationSecondsUntilNow(tokenLastRefreshAt);
        } catch (ParseException e) {
            logger.error("服务端错误，redis存储时间格式错误");
            webUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_SERVER_ERROR, "服务端错误，redis存储时间格式错误"));
            return;
        }
        if (tokenDurationSinceLastRefresh > RedisKeyConstants.TOKEN_MAX_UNREFRESHED_SECONDS) {
            webUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "token已失效，达到最大未刷新时间，请重新登录"));
            return;
        }

        // 检查token中的用户信息
        User user = tokenUtil.parseTokenToUser(token);
        if (user == null) {
            webUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_UNAUTHORIZED, "无效的token，没有这个用户"));
            return;
        }

        // 检查token的持续有效时间
        String tokenCreateAt = tokenUtil.parseTokenToCreateAt(token);
        if (tokenCreateAt == null || "".equals(tokenCreateAt)) {
            logger.error("服务端错误，token未存储创建时间");
            webUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_SERVER_ERROR, "服务端错误，token未存储创建时间"));
            return;
        }

        // 检查token的创建时间
        long tokenDurationSinceCreate;
        try {
            tokenDurationSinceCreate = DateUtil.getDurationSecondsUntilNow(tokenCreateAt);
        } catch (ParseException e) {
            logger.error("服务端错误，token存储时间格式错误");
            webUtil.renderResponseResult(response, new ResponseResult<>(CodeConstants.CODE_SERVER_ERROR, "服务端错误，token存储时间格式错误"));
            return;
        }
        if (tokenDurationSinceCreate > RedisKeyConstants.TOKEN_MAX_DURATION_SECONDS) {
            // token过期，重新生成token并返回
            tokenUtil.deferredDisableToken(token);
            response.setHeader("token", tokenUtil.getTokenByUserId(user.getUserId()));
        } else {
            // 刷新token
            tokenUtil.refreshToken(token);
        }

        LoginUser loginUser = new LoginUser(user, menuMapper.selectPermsByUserId(user.getUserId()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
