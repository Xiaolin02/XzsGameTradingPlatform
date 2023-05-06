package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.OrderStatusConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.OfferDTO;
import com.lin.controller.VO.GetOrderVO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.OrderMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Commodity;
import com.lin.pojo.Order;
import com.lin.pojo.User;
import com.lin.service.BuyerService;
import com.lin.utils.DateUtil;
import com.lin.utils.ParseTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ParseTokenUtil parseTokenUtil;


    @Override
    public ResponseResult offer(String token, OfferDTO offerDTO) {

        User user = parseTokenUtil.parseTokenToGetUser(token);
        Commodity commodity = commodityMapper.selectById(offerDTO.getCommodityId());
        if(commodity.getPrice() <= offerDTO.getMoney()) {
            return new ResponseResult(CodeConstants.CODE_PARAMETER_ERROR, "出价小于等于商品价格");
        }
        commodityMapper.offer(offerDTO.getCommodityId(), user.getUserId(), offerDTO.getMoney());
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "出价成功");

    }

    @Override
    public ResponseResult addOrder(String token, Integer commodityId) {

        User user = parseTokenUtil.parseTokenToGetUser(token);
        Commodity commodity = commodityMapper.selectById(commodityId);
        orderMapper.insert(new Order(commodityId, commodity.getSellerId(), user.getUserId(), commodity.getPrice(), DateUtil.getDateTime(), OrderStatusConstants.STATUS_UNPAID));
        return new ResponseResult<>(200,"提交成功");

    }

    @Override
    public ResponseResult getOrder(String token) {

        User user = parseTokenUtil.parseTokenToGetUser(token);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id", user.getUserId());
        List<Order> orders = orderMapper.selectList(wrapper);
        ArrayList<GetOrderVO> list = new ArrayList<>();
        for (Order order : orders) {
            list.add(
                    new GetOrderVO(
                    order.getCommodityId(),
                    userMapper.selectById(order.getSellerId()).getUsername(),
                    order.getMoney(),
                    order.getAddTime(),
                    order.getStatus()
                )
            );
        }
        return new ResponseResult(200, list);

    }


}
