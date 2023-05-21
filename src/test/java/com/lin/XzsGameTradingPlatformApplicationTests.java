package com.lin;

import com.lin.common.RedisKeyConstants;
import com.lin.mapper.OrderMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Order;
import com.lin.pojo.User;
import com.lin.utils.DateUtil;
import com.lin.utils.PasswordEncodingUtil;
import com.lin.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class XzsGameTradingPlatformApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisUtil redisUtil;

    @Test
    void contextLoads() {
        System.out.println("test success");
    }

    @Test
    void UserMapperTest() {
        Integer userId = 1111;
        String url = "testUrl";
        User user = new User();
        user.setUserId(userId);
        user.setUsername("test");
        user.setPassword("test");
        user.setPhone("12345678901");
        user.setRegisterAt(DateUtil.getDateTime());
        user.setBalance(8888);
        System.out.println("userMapper.insert(user): " + userMapper.insert(user));
        System.out.println("userMapper.selectById(userId): " + userMapper.selectById(userId));
        System.out.println("userMapper.selectRoleByUserId(userId): " + userMapper.addUrl(userId, url));
        System.out.println("userMapper.getUrl(userId): " + userMapper.getUrl(userId));
        System.out.println("userMapper.deleteUrl(userId): " + userMapper.deleteUrl(userId));
        System.out.println("userMapper.deleteById(userId): " + userMapper.deleteById(userId));
    }

    @Test
    void PasswordEncoderTest() {
        User user = userMapper.selectById(1);
//        String password = "123456";
        String password = "123456";
//        user.setPassword(PasswordEncodingUtil.encoding(password));
//        userMapper.updateById(user);
//        String encoded = new BCryptPasswordEncoder().encode(password);
        String encoded = user.getPassword();
        System.out.println("encoded: " + encoded);
        System.out.println("matches: " + new BCryptPasswordEncoder().matches(password, encoded));
    }

    @Test
    void AuthenticationTest() {
        User user = userMapper.selectById(1);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), "123456");
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
    }

    @Test
    void RedisTest() {
        String newPhoneNumber = "18350404077";
        String code = "2329";
        redisUtil.set(RedisKeyConstants.CODE_PHONE_MODIFY_PREFIX + newPhoneNumber, code, RedisKeyConstants.CODE_EXPIRE_TIME);
    }

}
;