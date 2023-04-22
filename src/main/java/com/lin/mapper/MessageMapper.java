package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月22日 11:35
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
