package com.lin;

import com.lin.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XzsGameTradingPlatformApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(DateUtil.getDateTime());
    }

}
