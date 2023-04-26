package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.SearchDTO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Commodity;
import com.lin.pojo.User;
import com.lin.service.CommodityService;
import com.lin.utils.DateUtil;
import com.lin.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author czh
 * @desc 商品服务
 * @date 2023/4/23 22:30
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    CommodityMapper commodityMapper;

    @Override
    public ResponseResult searchCommodity(String token, SearchDTO searchDTO) {
//return null;
        if (searchDTO.getPriceMax() != null && searchDTO.getPriceMin() != null && searchDTO.getPriceMax() < searchDTO.getPriceMin()) {
            return new ResponseResult(CodeConstants.CODE_PARAMETER_ERROR, "最大价格小于最小价格");
        }
        if (searchDTO.getReleaseTimeLatest() != null && DateUtil.isDateValid(searchDTO.getReleaseTimeLatest())) {
            return new ResponseResult(CodeConstants.CODE_PARAMETER_ERROR, "最早时间格式错误");
        }
        if (searchDTO.getReleaseTimeEarliest() != null && DateUtil.isDateValid(searchDTO.getReleaseTimeEarliest())) {
            return new ResponseResult(CodeConstants.CODE_PARAMETER_ERROR, "最晚时间格式错误");
        }
        if (searchDTO.getReleaseTimeLatest() != null && searchDTO.getReleaseTimeEarliest() != null) {
            if (searchDTO.getReleaseTimeLatest().compareTo(searchDTO.getReleaseTimeEarliest()) < 0) {
                return new ResponseResult(CodeConstants.CODE_PARAMETER_ERROR, "最晚时间早于最早时间");
            }
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, commodityMapper.search(searchDTO));
    }

    @Override
    public ResponseResult favoriteCommodity(String token, Integer commodityId) {
        commodityMapper.favorite(commodityId, getUserIdByToken(token));
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "收藏成功");
    }

    @Override
    public ResponseResult reportCommodity(String token, Integer commodityId, String reason) {
//return null;
        commodityMapper.report(commodityId, getUserIdByToken(token), reason);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "举报成功");
    }
    private Integer getUserIdByToken(String token){
        Claims claims = TokenUtil.parseToken(token);
        String username = claims.get("username").toString();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        return user.getUserId();
    }
}
