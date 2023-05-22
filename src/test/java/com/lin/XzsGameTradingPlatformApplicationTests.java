package com.lin;

import com.lin.common.RedisKeyConstants;
import com.lin.config.AliyunConfig;
import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import com.lin.utils.DateUtil;
import com.lin.utils.OssUtil;
import com.lin.utils.RedisUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Locale;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class XzsGameTradingPlatformApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    OssUtil ossUtil;
    @Autowired
    AliyunConfig aliyunConfig;

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
        System.out.println("userMapper.selectRoleByUserId(userId): " + userMapper.replacePictureUrl(userId, url));
        System.out.println("userMapper.getUrl(userId): " + userMapper.getPictureUrl(userId));
        System.out.println("userMapper.deleteUrl(userId): " + userMapper.deletePictureUrl(userId));
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

    @Test
    void userPictureTest() throws IOException {
//        Integer userId = 1;
//        User user = userMapper.selectById(userId);
        String filePath = "C:\\Users\\emoration\\Desktop\\test.jpg";
        String imageUrl = "http://xzs-gametradingplatform.oss-cn-shenzhen.aliyuncs.com/personalPicture/1/2023/05/21/%E6%97%A0%E6%A0%87%E9%A2%98.png?Expires=1684679336&OSSAccessKeyId=LTAI5tSNS5KVv5795nBQMBX9&Signature=x9IcXWYAmX5AbTXLUZ5Q3OGUXn8%3D";
        // url to bytes
        URL url = new URL(imageUrl);
        ByteArrayOutputStream urlOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                urlOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] imageBytes = urlOutputStream.toByteArray();
        // bytes to file
        try {
            try (OutputStream outputStream = new FileOutputStream(filePath)) {
                outputStream.write(imageBytes);
            }
            System.out.println("图片已成功保存为文件");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
;