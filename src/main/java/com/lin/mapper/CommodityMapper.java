package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.controller.DTO.SearchCommodityDTO;
import com.lin.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月20日 21:52
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
    void offer(Integer commodityId, Integer buyerId, Integer offer);
}
