package com.lin.filter;

import com.lin.mapper.MenuMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.LoginUser;
import com.lin.pojo.User;
import com.lin.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author 林炳昌
 * @date 2023年03月14日 16:17
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)) {
            filterChain.doFilter(request,response);
            return;
        }

        Claims claims = TokenUtil.parseToken(token);
        String userId = (String) claims.get("userId");
        User user = userMapper.selectById(userId);
        LoginUser loginUser = new LoginUser(user, menuMapper.selectPermsByUserId(user.getUserId()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }
}
