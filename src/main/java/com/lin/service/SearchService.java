package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.SearchCommodityDTO;

/**
 * @author czh
 * @desc 商品Service
 * @date 2023/4/23 21:59
 */
public interface SearchService {
    ResponseResult<Object> searchCommodity(SearchCommodityDTO searchDTO);

}
