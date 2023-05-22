package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 林炳昌
 * @desc UserMapper
 * @date 2023年03月31日 15:44
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    String selectRoleByUserId(Integer userId);

    int replacePictureUrl(Integer userId, String url);

    String getPictureUrl(Integer userId);

    int deletePictureUrl(Integer userId);

}
