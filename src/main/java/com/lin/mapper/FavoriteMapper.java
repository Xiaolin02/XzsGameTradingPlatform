package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.pojo.Favorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author czh
 * @desc 收藏
 * @date 2023/5/3 19:18
 */
@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

}
