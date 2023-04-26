package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.SearchDTO;

/**
 * @author czh
 * @desc 商品Service
 * @date 2023/4/23 21:59
 */
public interface CommodityService {
    ResponseResult searchCommodity(String token, SearchDTO searchDTO);
    ResponseResult favoriteCommodity(String token, Integer commodityId);
    ResponseResult reportCommodity(String token, Integer commodityId, String reason);
}
