package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.OfferDTO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Commodity;
import com.lin.pojo.User;
import com.lin.service.BuyerService;
import com.lin.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 林炳昌
 * @desc BuyerService实现类
 * @date 2023年04月20日 21:54
 */
@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommodityMapper commodityMapper;

    @Override
    public ResponseResult offer(String token, OfferDTO offerDTO) {

        Claims claims = TokenUtil.parseToken(token);
        String username = claims.get("username").toString();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        Commodity commodity = commodityMapper.selectById(offerDTO.getCommodityId());
        if(commodity.getPrice() <= offerDTO.getMoney()) {
            return new ResponseResult(CodeConstants.CODE_PARAMETER_ERROR, "出价小于等于商品价格");
        }
        commodityMapper.offer(offerDTO.getCommodityId(), user.getUserId(), offerDTO.getMoney());
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "出价成功");

    }


}
