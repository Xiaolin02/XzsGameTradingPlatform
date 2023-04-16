package com.lin.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

/**
 * @author 林炳昌
 * @desc token工具类
 * @date 2023年02月21日 22:40
 */
public class TokenUtil {

    private static final String signature = "user";

    public static String getToken(String userId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder.setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
    }

    public static Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        return claimsJws.getBody();
    }

}
