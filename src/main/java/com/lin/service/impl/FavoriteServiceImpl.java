package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.general.PageDTO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.FavoriteMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Commodity;
import com.lin.pojo.Favorite;
import com.lin.service.FavoriteService;
import com.lin.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author czh
 * @desc 收藏
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
    @Autowired
    TokenUtil tokenUtil;

    /**
     * @Author czh
     * @desc 添加收藏（不可重复添加）
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<Object> insertFavorite(String token, Integer commodityId) {

        Integer userId = tokenUtil.parseTokenToUserId(token);
        QueryWrapper<Favorite> favoriteQueryWrapper = new QueryWrapper<>();
        favoriteQueryWrapper.eq("commodity_id", commodityId);
        favoriteQueryWrapper.eq("user_id", userId);
        if (favoriteMapper.selectOne(favoriteQueryWrapper) != null) {
            return new ResponseResult<>(CodeConstants.CODE_CONFLICT, "重复添加收藏记录");
        }
        Favorite favorite = new Favorite();
        favorite.setCommodityId(commodityId);
        favorite.setUserId(userId);
        favoriteMapper.insert(favorite);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "成功添加收藏记录");
    }

    /**
     * @Author czh
     * @desc 删除收藏（不可删除不存在的收藏）
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<Object> deleteFavorite(String token, Integer commodityId) {
        QueryWrapper<Favorite> favoriteQueryWrapper = new QueryWrapper<>();
        favoriteQueryWrapper.eq("commodity_id", commodityId);
        favoriteQueryWrapper.eq("user_id", tokenUtil.parseTokenToUserId(token));
        if (favoriteMapper.delete(favoriteQueryWrapper) == 0) {
            return new ResponseResult<>(CodeConstants.CODE_CONFLICT, "不存在这个收藏记录");
        } else {
            return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "成功删除收藏记录");
        }
    }

    /**
     * @Author czh
     * @desc 查看收藏的商品List
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<Object> selectFavoriteCommodity(String token, PageDTO pageDTO) {
        QueryWrapper<Favorite> favoriteQueryWrapper = new QueryWrapper<>();
        favoriteQueryWrapper.eq("user_id", tokenUtil.parseTokenToUserId(token));
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
