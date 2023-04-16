package com.lin.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月01日 20:57
 */
public class PasswordEncodingUtil {

    public static String encoding(String password) {

        return new BCryptPasswordEncoder().encode(password);

    }

}
