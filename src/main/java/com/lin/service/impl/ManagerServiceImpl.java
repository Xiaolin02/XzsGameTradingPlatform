package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.CommodityStatusConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReportCommodityViewDTO;
import com.lin.controller.DTO.ReportUserViewDTO;
import com.lin.controller.DTO.commodity.CommodityCompleteDTO;
import com.lin.controller.DTO.commodity.CommoditySimpleDTO;
import com.lin.controller.DTO.general.PageDTO;
import com.lin.controller.DTO.user.UserCompleteDTO;
import com.lin.mapper.*;
import com.lin.pojo.Commodity;
import com.lin.pojo.ReportCommodity;
import com.lin.pojo.ReportUser;
import com.lin.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author czh
 * @desc 管理员serviceImpl
 * @date 2023/5/31 16:10
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    ReportUserMapper reportUserMapper;
    @Autowired
    ReportCommodityMapper reportCommodityMapper;

    /**
     * @desc 完成举报处理（举报用户）
     * @date 2023/5/31 17:36
     */
    @Override
    public ResponseResult<Object> UserReportDone(Integer reportId) {
        if (reportUserMapper.deleteById(reportId) == 0) {
            return new ResponseResult<>(CodeConstants.CODE_CONFLICT, "试图完成不存在的举报记录");
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "完成举报处理成功");
    }

    /**
     * @desc 查看未处理的全部举报（举报用户）
     * @date 2023/5/31 17:36
     */
    @Override
    public ResponseResult<Object> UserReportViewAll(PageDTO pageDTO) {
        QueryWrapper<ReportUser> reportUserQueryWrapper = new QueryWrapper<>();
        List<ReportUser> records = reportUserMapper.selectPage(pageDTO.toPage(), reportUserQueryWrapper).getRecords();
        List<ReportUserViewDTO> reportUserViewDTOList = new ArrayList<>();
        for (ReportUser record : records) {
            reportUserViewDTOList.add(new ReportUserViewDTO(record, userMapper));
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, Map.of("reportUserList", reportUserViewDTOList));
    }

    /**
     * @desc 查看某个用户（全部信息）
     * @date 2023/5/31 17:36
     */
    @Override
    public ResponseResult<Object> UserViewOne(Integer userId) {
        UserCompleteDTO userCompleteDTO = new UserCompleteDTO(userMapper.selectById(userId), userMapper);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, userCompleteDTO);
    }

    /**
     * @desc 审核通过商品
     * @date 2023/5/31 17:36
     */
    @Override
    public ResponseResult<Object> CommodityInspectAllow(Integer commodityId) {
        Commodity commodity = commodityMapper.selectById(commodityId);
        commodity.setStatus(CommodityStatusConstants.STATUS_SELLING);
        commodityMapper.updateById(commodity);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "审核通过成功");
    }

    /**
     * @desc 审核下架商品
     * @date 2023/5/31 17:36
     */
    @Override
    public ResponseResult<Object> CommodityInspectReject(Integer commodityId) {
        Commodity commodity = commodityMapper.selectById(commodityId);
        commodity.setStatus(CommodityStatusConstants.STATUS_FAILED);
        commodityMapper.updateById(commodity);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "审核下架成功");
    }

    /**
     * @desc 查看未审核的全部商品
     * @date 2023/5/31 17:36
     */
    @Override
    public ResponseResult<Object> CommodityInspectViewAll(PageDTO pageDTO) {
        QueryWrapper<Commodity> commodityQueryWrapper = new QueryWrapper<>();
        commodityQueryWrapper.eq("status", CommodityStatusConstants.STATUS_INSPECTING);
        List<Commodity> records = commodityMapper.selectPage(pageDTO.toPage(), commodityQueryWrapper).getRecords();
        List<CommoditySimpleDTO> commoditySimpleDTOList = new ArrayList<>();
        for (Commodity record : records) {
            commoditySimpleDTOList.add(new CommoditySimpleDTO(record, userMapper));
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, Map.of("commodityList", commoditySimpleDTOList));
    }

    /**
     * @desc 完成举报处理（举报商品）
     * @date 2023/5/31 17:36
     */
    @Override
    public ResponseResult<Object> CommodityReportDone(Integer reportId) {
        if (reportCommodityMapper.deleteById(reportId) == 0) {
            return new ResponseResult<>(CodeConstants.CODE_CONFLICT, "不存在这个举报记录");
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "完成举报处理成功");
    }

    /**
     * @desc 查看未处理的全部举报（举报用户）
     * @date 2023/5/31 17:36
     */
    @Override
    public ResponseResult<Object> CommodityReportViewAll(PageDTO pageDTO) {
        QueryWrapper<ReportCommodity> reportCommodityQueryWrapper = new QueryWrapper<>();
        List<ReportCommodity> records = reportCommodityMapper.selectPage(pageDTO.toPage(), reportCommodityQueryWrapper).getRecords();
        List<ReportCommodityViewDTO> reportCommodityViewDTOList = new ArrayList<>();
        for (ReportCommodity record : records) {
            reportCommodityViewDTOList.add(new ReportCommodityViewDTO(record, userMapper, commodityMapper));
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, Map.of("reportCommodityList", reportCommodityViewDTOList));
    }

    /**
     * @desc 查看某个商品（全部信息）
     * @date 2023/5/31 17:36
     */
    @Override
    public ResponseResult<Object> CommodityViewOne(Integer commodityId) {
        CommodityCompleteDTO commodityCompleteDTO = new CommodityCompleteDTO(commodityMapper.selectById(commodityId), userMapper, commodityMapper, accountMapper);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, commodityCompleteDTO);
    }
}