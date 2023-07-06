package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.CommodityListDTO;
import com.lin.controller.DTO.commodity.CommoditySimpleDTO;
import com.lin.controller.DTO.general.PageDTO;


/**
 * @Author czh
 * @desc 收藏
 * @date 2023/5/3 19:21
 */
public interface FavoriteService {
    ResponseResult<NullData> insertFavorite(String token, Integer commodityId);

    ResponseResult<NullData> deleteFavorite(String token, Integer commodityId);

    ResponseResult<CommodityListDTO<CommoditySimpleDTO>> selectFavoriteCommodity(String token, PageDTO pageDTO);
}
