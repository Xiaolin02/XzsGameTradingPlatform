package com.lin.expression;

import com.lin.pojo.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 林炳昌
 * @date 2023年03月21日 15:42
 */
@Component("ex")
public class MyExpressionRoot {

    public boolean hasAuthority(String authority) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        return permissions.contains(authority);

    }

}
