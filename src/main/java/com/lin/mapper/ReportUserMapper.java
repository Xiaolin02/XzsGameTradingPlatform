package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.pojo.ReportUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author czh
 * @desc 举报用户
 * @date 2023/5/4 7:54
 */
@Mapper
public interface ReportUserMapper extends BaseMapper<ReportUser> {
}
