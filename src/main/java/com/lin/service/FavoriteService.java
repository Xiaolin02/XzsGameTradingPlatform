package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.General.PageDTO;

/**
 * @Author czh
 * @desc 收藏
 * @date 2023/5/3 19:21
 */
public interface FavoriteService {
    ResponseResult<Object> insertFavorite(String token, Integer commodityId);
    ResponseResult<Object> deleteFavorite(String token, Integer commodityId);
    ResponseResult<Object> selectFavoriteCommodity(String token, PageDTO pageDTO);
}
