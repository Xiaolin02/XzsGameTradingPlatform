package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.SearchCommodityDTO;
import com.lin.controller.DTO.general.PageDTO;
import com.lin.controller.DTO.user.SearchUserDTO;

/**
 * @author czh
 * @desc 商品Service
 * @date 2023/4/23 21:59
 */
public interface SearchService {
    ResponseResult<Object> searchCommodityRecommend(PageDTO pageDTO);
    ResponseResult<Object> searchCommodityList(SearchCommodityDTO searchDTO);
    ResponseResult<Object> searchCommodityOne(String commodityId);
    ResponseResult<Object> searchUserList(SearchUserDTO searchUserDTO);
    ResponseResult<Object> searchUserOne(String userId);

}
