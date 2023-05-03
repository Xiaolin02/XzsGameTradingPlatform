package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.General.PageDTO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.FavoriteMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Commodity;
import com.lin.pojo.Favorite;
import com.lin.service.BasicService;
import com.lin.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author czh
 * @desc TODO
 * @date 2023/5/3 19:23
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteMapper favoriteMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommodityMapper commodityMapper;

    @Override
    public ResponseResult<Object> addFavorite(String token, Integer commodityId) {

        QueryWrapper<Favorite> favoriteQueryWrapper = new QueryWrapper<>();
        favoriteQueryWrapper.eq("commodity_id", commodityId);
        favoriteQueryWrapper.eq("user_id", BasicService.getUserIdByToken(userMapper, token));
        if (favoriteMapper.selectOne(favoriteQueryWrapper) != null) {
            return new ResponseResult<>(CodeConstants.CODE_CONFLICT, "重复添加收藏记录");
        }
        favoriteMapper.insert(new Favorite(commodityId, BasicService.getUserIdByToken(userMapper, token)));
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "成功添加收藏记录");
    }

    @Override
    public ResponseResult<Object> deleteFavorite(String token, Integer commodityId) {
        QueryWrapper<Favorite> favoriteQueryWrapper = new QueryWrapper<>();
        favoriteQueryWrapper.eq("commodity_id", commodityId);
        favoriteQueryWrapper.eq("user_id", BasicService.getUserIdByToken(userMapper, token));
        if (favoriteMapper.delete(favoriteQueryWrapper) == 0) {
            return new ResponseResult<>(CodeConstants.CODE_CONFLICT, "不存在这个收藏记录");
        } else {
            return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "成功删除收藏记录");
        }
    }

    @Override
    public ResponseResult<Object> searchFavorite(String token, PageDTO pageDTO) {
        QueryWrapper<Favorite> favoriteQueryWrapper = new QueryWrapper<>();
        favoriteQueryWrapper.eq("user_id", BasicService.getUserIdByToken(userMapper, token));
        List<Favorite> favoriteList = favoriteMapper.selectPage(pageDTO.toPage(), favoriteQueryWrapper).getRecords();
        List<Commodity> commodityList = new ArrayList<>();
        for (Favorite favorite : favoriteList) {
            QueryWrapper<Commodity> commodityQueryWrapper = new QueryWrapper<>();
            commodityQueryWrapper.eq("commodity_id", favorite.getCommodityId());
            commodityList.add(commodityMapper.selectOne(commodityQueryWrapper));
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, commodityList);
    }
}
