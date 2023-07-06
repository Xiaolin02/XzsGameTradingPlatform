package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.CommodityStatusConstants;
import com.lin.common.ListOrderByConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.CommodityDetailedDTO;
import com.lin.controller.DTO.commodity.CommodityListDTO;
import com.lin.controller.DTO.commodity.SearchCommodityDTO;
import com.lin.controller.DTO.commodity.CommoditySimpleDTO;
import com.lin.controller.DTO.general.PageDTO;
import com.lin.controller.DTO.user.SearchUserDTO;
import com.lin.controller.DTO.user.UserDetailedDTO;
import com.lin.controller.DTO.user.UserListDTO;
import com.lin.controller.DTO.user.UserSimpleDTO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Commodity;
import com.lin.pojo.User;
import com.lin.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author czh
 * @desc 商品服务
 * @date 2023/4/23 22:30
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    CommodityMapper commodityMapper;

    /**
     * @Author czh
     * @desc 首页推荐商品
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<CommodityListDTO<CommoditySimpleDTO>> searchCommodityRecommend(PageDTO pageDTO) {
        QueryWrapper<Commodity> commodityQueryWrapper = new QueryWrapper<>();
        // 审核条件
        commodityQueryWrapper.eq("status", CommodityStatusConstants.STATUS_SELLING);
        // 查询商品
        List<Commodity> records = commodityMapper.selectPage(pageDTO.toPage(), commodityQueryWrapper).getRecords();
        // 查询总数
        Long total = commodityMapper.selectCount(commodityQueryWrapper);
        // 装配DTO
        List<CommoditySimpleDTO> commoditySimpleDTOList = new ArrayList<>();
        for (Commodity commodity : records) {
            commoditySimpleDTOList.add(new CommoditySimpleDTO(commodity, userMapper, commodityMapper));
        }
        // 返回结果
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, new CommodityListDTO<>(commoditySimpleDTOList, total));
    }

    /**
     * @Author czh
     * @desc 搜索商品，支持按时间、价格、议价、描述来搜索商品（仅能搜索售卖中(status=1)的商品）
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<CommodityListDTO<CommoditySimpleDTO>> searchCommodityList(SearchCommodityDTO searchCommodityDTO) {
        QueryWrapper<Commodity> commodityQueryWrapper = new QueryWrapper<>();
        // 价格条件
        if (searchCommodityDTO.getPriceMin() != null) {
            if (searchCommodityDTO.getPriceMin() < 0) {
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "最小价格小于0");
            }
            commodityQueryWrapper.ge("price", searchCommodityDTO.getPriceMin());
        }
        if (searchCommodityDTO.getPriceMax() != null) {
            if (searchCommodityDTO.getPriceMax() < 0) {
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "最大价格小于0");
            }
            commodityQueryWrapper.le("price", searchCommodityDTO.getPriceMax());
        }
        if (searchCommodityDTO.getPriceMax() != null && searchCommodityDTO.getPriceMin() != null && searchCommodityDTO.getPriceMax() < searchCommodityDTO.getPriceMin()) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "最大价格小于最小价格");
        }
        // 议价条件
        if (searchCommodityDTO.getAllowBargaining() != null) {
            commodityQueryWrapper.eq("allow_bargaining", searchCommodityDTO.getAllowBargaining());
        }
        // 审核条件
        commodityQueryWrapper.eq("status", CommodityStatusConstants.STATUS_SELLING);
        // 描述条件
        if (searchCommodityDTO.getKeyword() == null || "".equals(searchCommodityDTO.getKeyword())) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "关键字为空");
        }
        commodityQueryWrapper.and(i -> i.like("title", searchCommodityDTO.getKeyword())
                .or().like("description", searchCommodityDTO.getKeyword()));
        // 顺序条件
        if (searchCommodityDTO.getOrderBy() != null) {
            if (searchCommodityDTO.getOrderBy() == ListOrderByConstants.COMMODITY_ORDER_BY_RELEASE_AT_ASC) {
                commodityQueryWrapper.orderByAsc("release_at");
            } else if (searchCommodityDTO.getOrderBy() == ListOrderByConstants.COMMODITY_ORDER_BY_RELEASE_AT_DESC) {
                commodityQueryWrapper.orderByDesc("release_at");
            } else if (searchCommodityDTO.getOrderBy() == ListOrderByConstants.COMMODITY_ORDER_BY_PRICE_ASC) {
                commodityQueryWrapper.orderByAsc("price");
            } else if (searchCommodityDTO.getOrderBy() == ListOrderByConstants.COMMODITY_ORDER_BY_PRICE_DESC) {
                commodityQueryWrapper.orderByDesc("price");
            } else {
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "排序参数错误");
            }
        }
        // 查询商品
        List<Commodity> records = commodityMapper.selectPage(searchCommodityDTO.getPage().toPage(), commodityQueryWrapper).getRecords();
        // 查询总数
        Long total = commodityMapper.selectCount(commodityQueryWrapper);
        // 装配DTO
        List<CommoditySimpleDTO> commoditySimpleDTOList = new ArrayList<>();
        for (Commodity commodity : records) {
            commoditySimpleDTOList.add(new CommoditySimpleDTO(commodity, userMapper, commodityMapper));
        }
        // 返回结果
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, new CommodityListDTO<>(commoditySimpleDTOList, total));
    }

    /**
     * @Author czh
     * @desc 查看商品详情
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<CommodityDetailedDTO> searchCommodityOne(String commodityId) {
        Commodity commodity = commodityMapper.selectById(commodityId);
        if (commodity == null) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "商品不存在");
        }
        CommodityDetailedDTO commodityDetailedDTO = new CommodityDetailedDTO(commodity, userMapper, commodityMapper);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, commodityDetailedDTO);
    }

    /**
     * @Author czh
     * @desc 搜索用户，支持按用户名来搜索用户
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<UserListDTO<UserSimpleDTO>> searchUserList(SearchUserDTO searchUserDTO) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        // 用户名条件
        if (searchUserDTO.getKeyword() != null) {
            userQueryWrapper.like("username", searchUserDTO.getKeyword());
        }
        List<User> records = userMapper.selectPage(searchUserDTO.getPage().toPage(), userQueryWrapper).getRecords();
        // 查询总数
        Long total = userMapper.selectCount(userQueryWrapper);
        // 装配DTO
        List<UserSimpleDTO> userSimpleDTOList = new ArrayList<>();
        for (User user : records) {
            userSimpleDTOList.add(new UserSimpleDTO(user, userMapper));
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, new UserListDTO<>(userSimpleDTOList, total));
    }

    /**
     * @Author czh
     * @desc 查看某个用户
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<UserDetailedDTO> searchUserOne(String userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "用户不存在");
        }
        UserDetailedDTO userDetailedDTO = new UserDetailedDTO(user, userMapper);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, userDetailedDTO);
    }
}
