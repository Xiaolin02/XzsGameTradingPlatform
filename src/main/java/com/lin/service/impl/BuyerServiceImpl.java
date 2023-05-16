package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.OrderStatusConstants;
import com.lin.common.ResponseResult;
import com.lin.common.SystemMsgTitleConstants;
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
import com.lin.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    MessageServiceImpl messageService;


    @Override
    public ResponseResult offer(String token, OfferDTO offerDTO) {

        Integer userId = parseTokenUtil.parseTokenToUserId(token);
        Commodity commodity = commodityMapper.selectById(offerDTO.getCommodityId());
        if(commodity.getPrice() <= offerDTO.getMoney()) {
            return new ResponseResult(CodeConstants.CODE_PARAMETER_ERROR, "出价小于等于商品价格");
        }
        commodityMapper.offer(offerDTO.getCommodityId(), userId, offerDTO.getMoney());
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "出价成功");

    }

    @Override
    public ResponseResult addOrder(String token, Integer commodityId) {

        Integer userId = parseTokenUtil.parseTokenToUserId(token);
        Commodity commodity = commodityMapper.selectById(commodityId);
        if(Objects.isNull(commodity))
            return new ResponseResult<>(CodeConstants.CODE_NOT_FOUND, "未找到该商品");
        orderMapper.insert(new Order(RandomUtil.getEighteenBitRandom(), commodityId, commodity.getSellerId(), userId, commodity.getPrice(), DateUtil.getDateTime(), OrderStatusConstants.STATUS_UNPAID));
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS,"提交成功");

    }

    @Override
    public ResponseResult getOrder(String token) {

        Integer userId = parseTokenUtil.parseTokenToUserId(token);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id", userId);
        List<Order> orders = orderMapper.selectList(wrapper);
        if (Objects.equals(orders.size(), 0 ))
            return new ResponseResult(CodeConstants.CODE_SUCCESS, "该用户没有订单");
        ArrayList<GetOrderVO> list = new ArrayList<>();
        for (Order order : orders) {
            list.add(
                    new GetOrderVO(
                    order.getId(),
                    order.getCommodityId(),
                    userMapper.selectById(order.getSellerId()).getUsername(),
                    order.getPrice(),
                    order.getAddTime(),
                    order.getStatus()
                )
            );
        }
        return new ResponseResult(CodeConstants.CODE_SUCCESS, list);

    }

    @Override
    public ResponseResult delOrder(String token, Integer orderId) {

        Order order = orderMapper.selectById(orderId);
        if(Objects.isNull(order))
            return new ResponseResult(CodeConstants.CODE_NOT_FOUND, "该订单不存在");
        order.setStatus(OrderStatusConstants.STATUS_BUYER_CANCEL);
        orderMapper.updateById(order);
        return new ResponseResult(CodeConstants.CODE_SUCCESS,"取消成功");

    }

    @Override
    public ResponseResult payOrder(String token, Integer orderId) {

        User user = parseTokenUtil.parseTokenToUser(token);
        Order order = orderMapper.selectById(orderId);
        if(user.getBalance() < order.getPrice())
            return new ResponseResult<>(CodeConstants.CODE_INSUFFICIENT_BALANCE, "用户余额不足");
        else {
            user.setBalance(user.getBalance() - order.getPrice());
            order.setStatus(OrderStatusConstants.STATUS_PAID);
            userMapper.updateById(user);
            orderMapper.updateById(order);
            Commodity commodity = commodityMapper.selectById(order.getCommodityId());
            messageService.pushSystemMsgToOneUser(
                    token,
                    order.getSellerId(),
                    "买家 " + user.getUsername() + " 购买了您 \"" + commodity.getTitle() + "\" 的商品,请尽快与买家完成验货",
                    SystemMsgTitleConstants.SYSTEM_MSG_PAID_ORDER
            );
            return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "付款成功");
        }

    }


}
