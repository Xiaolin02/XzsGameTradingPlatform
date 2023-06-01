package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.CommodityStatusConstants;
import com.lin.common.OrderStatusConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReleaseDTO;
import com.lin.controller.VO.ViewOrderVO;
import com.lin.controller.VO.ViewReleasedVO;
import com.lin.mapper.AccountMapper;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.OrderMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Account;
import com.lin.pojo.Commodity;
import com.lin.pojo.Order;
import com.lin.service.SellerService;
import com.lin.utils.DateUtil;
import com.lin.utils.OssUtil;
import com.lin.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 林炳昌
 * @desc SellerService实现类
 * @date 2023年05月07日 16:36
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    CommodityMapper commodityMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    OssUtil ossUtil;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseResult release(String token, ReleaseDTO releaseDTO, MultipartFile[] files) {

        Integer userId = tokenUtil.parseTokenToUserId(token);
        int count = commodityMapper.selectCount(null).intValue();
        Commodity commodity = new Commodity();
        commodity.setCommodityId(count + 1);
        commodity.setReleaseAt(DateUtil.getDateTime());
        commodity.setTitle(releaseDTO.getTitle());
        commodity.setDescription(releaseDTO.getDescription());
        commodity.setPrice(releaseDTO.getPrice());
        commodity.setSellerId(userId);
        commodity.setGame(releaseDTO.getGame());
        commodity.setAllowBargaining(releaseDTO.getAllowBargaining());
        commodity.setStatus(CommodityStatusConstants.STATUS_INSPECTING);
        commodityMapper.insert(commodity);
        Account account = new Account();
        account.setCommodityId(count + 1);
        account.setAccountNumber(releaseDTO.getAccountNumber());
        account.setAccountPassword(releaseDTO.getAccountPassword());
        accountMapper.insert(account);
        for (MultipartFile file : files) {
            String url = ossUtil.uploadfile(file, userId, "release");
            commodityMapper.insertPictureUrl(count + 1, url);
        }
        return new ResponseResult(CodeConstants.CODE_SUCCESS, "发布成功");

    }

    @Override
    public ResponseResult view(String token) {


        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id", tokenUtil.parseTokenToUserId(token));
        List<Commodity> commodities = commodityMapper.selectList(wrapper);
        ArrayList<ViewReleasedVO> releasedVOS = new ArrayList<>();
        Integer status;
        for (Commodity commodity : commodities) {

            ViewReleasedVO releasedVO = new ViewReleasedVO();
            releasedVO.setCommodityId(commodity.getCommodityId());
            releasedVO.setReleasedAt(commodity.getReleaseAt());
            releasedVO.setTitle(commodity.getTitle());
            releasedVO.setDescription(commodity.getDescription());
            releasedVO.setGame(commodity.getGame());
            releasedVO.setPrice(commodity.getPrice());
            releasedVO.setAllowBargaining(commodity.getAllowBargaining());
            status = commodity.getStatus();
            switch (status) {
                case CommodityStatusConstants.STATUS_INSPECTING -> releasedVO.setStatus("审核中");
                case CommodityStatusConstants.STATUS_SELLING -> releasedVO.setStatus("售卖中");
                case CommodityStatusConstants.STATUS_SOLD -> releasedVO.setStatus("已售出");
                case CommodityStatusConstants.STATUS_CANCEL -> releasedVO.setStatus("已下架");
                case CommodityStatusConstants.STATUS_FAILED -> releasedVO.setStatus("审核失败");
            }
            releasedVOS.add(releasedVO);

        }

        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, releasedVOS);


    }

    @Override
    public ResponseResult newPrice(Integer commodityId, Integer newPrice, String token) {

        Commodity commodity = commodityMapper.selectById(commodityId);
        Commodity newCommodity = new Commodity(commodity);
        newCommodity.setPrice(newPrice);
        commodityMapper.updateById(newCommodity);
        return new ResponseResult(CodeConstants.CODE_SUCCESS, "修改成功");

    }

    @Override
    public ResponseResult viewOrder(String token) {

        Integer userId = tokenUtil.parseTokenToUserId(token);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id", userId);
        List<Order> orderList = orderMapper.selectList(wrapper);
        ArrayList<ViewOrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderList) {

            ViewOrderVO viewOrderVO = new ViewOrderVO();
            viewOrderVO.setCommodityId(order.getCommodityId());
            viewOrderVO.setPrice(order.getPrice());
            viewOrderVO.setAddAt(order.getAddAt());
            viewOrderVO.setStatus(order.getStatus());
            viewOrderVO.setBuyerName(userMapper.selectById(order.getBuyerId()).getUsername());
            viewOrderVO.setBuyerPictureUrl(userMapper.getPictureUrl(order.getBuyerId()));
            orderVOList.add(viewOrderVO);

        }

        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, orderVOList);

    }

    @Override
    public ResponseResult delOrder(String token, String orderId) {

        Order order = orderMapper.selectById(orderId);
        if (Objects.isNull(order)) {
            return new ResponseResult<>(CodeConstants.CODE_NOT_FOUND, "订单不存在");
        } else {
            order.setStatus(OrderStatusConstants.STATUS_SELLER_CANCEL);
            orderMapper.updateById(order);
            return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "取消成功");
        }

    }

}
