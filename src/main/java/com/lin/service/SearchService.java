package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.CommodityDetailedDTO;
import com.lin.controller.DTO.commodity.CommodityListDTO;
import com.lin.controller.DTO.commodity.CommoditySimpleDTO;
import com.lin.controller.DTO.commodity.SearchCommodityDTO;
import com.lin.controller.DTO.general.PageDTO;
import com.lin.controller.DTO.user.SearchUserDTO;
import com.lin.controller.DTO.user.UserDetailedDTO;
import com.lin.controller.DTO.user.UserListDTO;
import com.lin.controller.DTO.user.UserSimpleDTO;


/**
 * @author czh
 * @desc 商品Service
 * @date 2023/4/23 21:59
 */
public interface SearchService {
    ResponseResult<CommodityListDTO<CommoditySimpleDTO>> searchCommodityRecommend(PageDTO pageDTO);

    ResponseResult<CommodityListDTO<CommoditySimpleDTO>> searchCommodityList(SearchCommodityDTO searchDTO);

    ResponseResult<CommodityDetailedDTO> searchCommodityOne(String commodityId);

    ResponseResult<UserListDTO<UserSimpleDTO>> searchUserList(SearchUserDTO searchUserDTO);

    ResponseResult<UserDetailedDTO> searchUserOne(String userId);

}
