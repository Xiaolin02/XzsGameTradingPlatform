package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.mapper.MenuMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.LoginUser;
import com.lin.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 林炳昌
 * @date 2023年03月13日 21:19
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = userMapper.selectOne(userQueryWrapper);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名不存在");
        }

        List<String> list = menuMapper.selectPermsByUserId(user.getUserId());

        return new LoginUser(user, list);


    }

}
