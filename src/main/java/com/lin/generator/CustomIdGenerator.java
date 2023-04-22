package com.lin.generator;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Xiaolin
 * @desc mybatis-plus主键自增设置
 * @date 2023/4/18 16:20
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private final AtomicLong al = new AtomicLong(1);

    @Override
    public Long nextId(Object entity) {
        //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
        String bizKey = entity.getClass().getName();
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        final long id = al.getAndAdd(1);
        return id;
    }
}

