package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.SearchCommodityDTO;
import com.lin.pojo.Commodity;

import java.util.List;

/**
 * @author czh
 * @desc 商品Service
 * @date 2023/4/23 21:59
 */
public interface CommodityService {
    ResponseResult<Object> searchCommodity(SearchCommodityDTO searchDTO);

}
