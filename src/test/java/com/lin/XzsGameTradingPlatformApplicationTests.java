package com.lin;

import com.lin.mapper.OrderMapper;
import com.lin.pojo.Order;
import com.lin.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XzsGameTradingPlatformApplicationTests {

    @Autowired
    OrderMapper orderMapper;

    @Test
    void contextLoads() {

        orderMapper.insert(new Order(1,1,1,1,1));

    }

}
