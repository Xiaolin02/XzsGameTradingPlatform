package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.CommodityStatusConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.SearchCommodityDTO;
import com.lin.controller.DTO.commodity.CommoditySimpleDTO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Commodity;
import com.lin.service.SearchService;
import com.lin.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @desc 搜索商品，支持按时间、价格、议价、描述来搜索商品（仅能搜索售卖中(status=1)的商品）
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<Object> searchCommodity(SearchCommodityDTO searchDTO) {
        QueryWrapper<Commodity> commodityQueryWrapper = new QueryWrapper<>();
        // 价格条件
        if (searchDTO.getPriceMin() != null) {
            if (searchDTO.getPriceMin() < 0) {
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "最小价格小于0");
            }
            commodityQueryWrapper.ge("price", searchDTO.getPriceMin());
        }
        if (searchDTO.getPriceMax() != null) {
            if (searchDTO.getPriceMax() < 0) {
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "最大价格小于0");
            }
            commodityQueryWrapper.le("price", searchDTO.getPriceMax());
        }
        if (searchDTO.getPriceMax() != null && searchDTO.getPriceMin() != null && searchDTO.getPriceMax() < searchDTO.getPriceMin()) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "最大价格小于最小价格");
        }
        // 时间条件
        if (searchDTO.getReleaseTimeEarliest() != null) {
            if (DateUtil.isNotValidDate(searchDTO.getReleaseTimeEarliest())) {
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "最早时间格式错误，接受的格式为" + DateUtil.DEFAULT_DATA_FORMAT);
            }
            commodityQueryWrapper.ge("release_time", searchDTO.getReleaseTimeEarliest());
        }
        if (searchDTO.getReleaseTimeLatest() != null) {
            if (DateUtil.isNotValidDate(searchDTO.getReleaseTimeLatest())) {
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "最晚时间格式错误，接受的格式为" + DateUtil.DEFAULT_DATA_FORMAT);
            }
            commodityQueryWrapper.le("release_time", searchDTO.getReleaseTimeLatest());
        }
        if (searchDTO.getReleaseTimeLatest() != null && searchDTO.getReleaseTimeEarliest() != null) {
            if (searchDTO.getReleaseTimeLatest().compareTo(searchDTO.getReleaseTimeEarliest()) < 0) {
                return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "最晚时间早于最早时间");
            }
        }
        // 议价条件
        if (searchDTO.getAllowBargaining() != null) {
            commodityQueryWrapper.eq("allow_bargaining", searchDTO.getAllowBargaining());
        }
        // 描述条件
        if (searchDTO.getKeyword() != null) {
            commodityQueryWrapper.and(i -> i.like("title", searchDTO.getKeyword())
                    .or().like("description", searchDTO.getKeyword()));
        }
        // 审核条件
        commodityQueryWrapper.eq("status", CommodityStatusConstants.STATUS_SELLING);
        // 查询商品
        List<Commodity> records = commodityMapper.selectPage(searchDTO.getPage().toPage(), commodityQueryWrapper).getRecords();
        // 装配DTO
        List<CommoditySimpleDTO> commoditySimpleDTOList = new ArrayList<>();
        for (Commodity commodity : records) {
            commoditySimpleDTOList.add(new CommoditySimpleDTO(commodity, userMapper));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("commodityList", commoditySimpleDTOList);
        // 返回结果
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, map);
    }
}
