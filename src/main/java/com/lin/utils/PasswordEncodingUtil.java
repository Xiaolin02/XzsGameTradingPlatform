package com.lin.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月01日 20:57
 */
public class PasswordEncodingUtil {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encoding(String password) {

        return bCryptPasswordEncoder.encode(password);

    }

    /**
     * 匹配密码是否正确
     *
     * @param password        待检验的密码（encode前的密码）
     * @param encodedPassword 加密的密码
     * @return 是否密码正确
     * @Auther czh
     */
    public static boolean matches(String password, String encodedPassword) {

        return bCryptPasswordEncoder.matches(password, encodedPassword);

    }
}
