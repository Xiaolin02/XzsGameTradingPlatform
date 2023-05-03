package com.lin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.General.PageDTO;
import com.lin.pojo.Favorite;

/**
 * @Author czh
 * @desc TODO
 * @date 2023/5/3 19:21
 */
public interface FavoriteService {
    public ResponseResult<Object> addFavorite(String token, Integer commodityId);
    public ResponseResult<Object> deleteFavorite(String token, Integer commodityId);
    public ResponseResult<Object> searchFavorite(String token, PageDTO pageDTO);
}
