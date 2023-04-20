package com.lin;

import com.lin.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XzsGameTradingPlatformApplicationTests {

    @Test
    void contextLoads() {
        Claims claims = TokenUtil.parseToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlhpYW9saW4ifQ.gHjej5G3eww5i2PCbJfIgB4hGdKCPAKUFloXp0aY8K8");
        Object username = claims.get("username");
        System.out.println(username);
    }

}
