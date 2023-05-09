package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.pojo.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年05月09日 14:12
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
