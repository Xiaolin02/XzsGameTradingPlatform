package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.CommodityListDTO;
import com.lin.controller.DTO.commodity.CommodityMiniDTO;
import com.lin.controller.DTO.user.UserListDTO;
import com.lin.controller.DTO.user.UserMiniDTO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.ReportCommodityMapper;
import com.lin.mapper.ReportUserMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.ReportCommodity;
import com.lin.pojo.ReportUser;
import com.lin.service.ReportService;
import com.lin.utils.DateUtil;
import com.lin.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author czh
 * @desc 举报
 * @date 2023/5/4 8:55
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportCommodityMapper reportCommodityMapper;
    @Autowired
    ReportUserMapper reportUserMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    TokenUtil tokenUtil;

    /**
     * @Author czh
     * @desc 添加 举报的商品
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<NullData> insertReportCommodity(String token, Integer commodityId, String reason) {
        Integer reporterId = tokenUtil.parseTokenToUserId(token);
        ReportCommodity reportCommodity = new ReportCommodity();
        reportCommodity.setReason(reason);
        reportCommodity.setReporterId(reporterId);
        reportCommodity.setCommodityId(commodityId);
        reportCommodity.setReportAt(DateUtil.getDateTime());
        reportCommodityMapper.insert(reportCommodity);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "举报成功");
    }

    /**
     * @Author czh
     * @desc 撤销/删除 举报的商品
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<NullData> deleteReportCommodity(String token, Integer reportId) {
        Integer reporterId = tokenUtil.parseTokenToUserId(token);
        QueryWrapper<ReportCommodity> reportCommodityQueryWrapper = new QueryWrapper<>();
        reportCommodityQueryWrapper.eq("report_id", reportId);
        reportCommodityQueryWrapper.eq("reporter_id", reporterId);
        if (reportCommodityMapper.delete(reportCommodityQueryWrapper) == 0) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "找不到这个举报记录，或请求删除者和举报记录举报者不匹配");
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "成功删除举报记录");
    }

    /**
     * @Author czh
     * @desc 查看 举报的商品
     * @date 2023/5/22 21:58
     */
    @Override
    public ResponseResult<CommodityListDTO<CommodityMiniDTO>> selectReportCommodity(String token) {
        Integer reporterId = tokenUtil.parseTokenToUserId(token);
        QueryWrapper<ReportCommodity> reportCommodityQueryWrapper = new QueryWrapper<>();
        reportCommodityQueryWrapper.eq("reporter_id", reporterId);
        List<ReportCommodity> reportCommodityList = reportCommodityMapper.selectList(reportCommodityQueryWrapper);
        Long total = reportCommodityMapper.selectCount(reportCommodityQueryWrapper);
        List<CommodityMiniDTO> commodityMiniDTOList = new ArrayList<>();
        for (ReportCommodity reportCommodity : reportCommodityList) {
            Integer commodityId = reportCommodity.getCommodityId();
            commodityMiniDTOList.add(commodityId == null ? null : new CommodityMiniDTO(commodityMapper.selectById(commodityId)));
        }
        CommodityListDTO<CommodityMiniDTO> commodityListDTO = new CommodityListDTO<>(commodityMiniDTOList, total);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, commodityListDTO);
    }

    /**
     * @Author czh
     * @desc 添加 举报的用户
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<NullData> insertReportUser(String token, Integer userId, String reason) {
        Integer reporterId = tokenUtil.parseTokenToUserId(token);
        ReportUser reportUser = new ReportUser();
        reportUser.setReason(reason);
        reportUser.setReporterId(reporterId);
        reportUser.setUserId(userId);
        reportUser.setReportAt(DateUtil.getDateTime());
        reportUserMapper.insert(reportUser);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "举报成功");
    }

    /**
     * @Author czh
     * @desc 撤销/删除 举报的用户
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<NullData> deleteReportUser(String token, Integer reportId) {
        Integer userId = tokenUtil.parseTokenToUserId(token);
        QueryWrapper<ReportUser> reportUserQueryWrapper = new QueryWrapper<>();
        reportUserQueryWrapper.eq("report_id", reportId);
        reportUserQueryWrapper.eq("reporter_id", userId);
        if (reportUserMapper.delete(reportUserQueryWrapper) == 0) {
            return new ResponseResult<>(CodeConstants.CODE_PARAMETER_ERROR, "找不到这个举报记录，或请求删除者和举报记录举报者不匹配");
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "成功删除举报记录");
    }

    /**
     * @Author czh
     * @desc 查看 举报的用户
     * @date 2023/5/22 21:58
     */
    @Override
    public ResponseResult<UserListDTO<UserMiniDTO>> selectReportUser(String token) {
        Integer reporterId = tokenUtil.parseTokenToUserId(token);
        QueryWrapper<ReportUser> reportUserQueryWrapper = new QueryWrapper<>();
        reportUserQueryWrapper.eq("reporter_id", reporterId);
        List<ReportUser> reportUserList = reportUserMapper.selectList(reportUserQueryWrapper);
        Long total = reportUserMapper.selectCount(reportUserQueryWrapper);
        List<UserMiniDTO> userMiniDTOList = new ArrayList<>();
        for (ReportUser reportUser : reportUserList) {
            Integer userId = reportUser.getUserId();
            userMiniDTOList.add(userId == null ? null : new UserMiniDTO(userMapper.selectById(userId)));
        }
        UserListDTO<UserMiniDTO> userListDTO = new UserListDTO<>(userMiniDTOList, total);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, userListDTO);
    }

}
