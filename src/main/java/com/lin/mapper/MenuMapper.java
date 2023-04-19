package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林炳昌
 * @desc MenuMapper
 * @date 2023年03月31日 22:42
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Integer userid);


}
