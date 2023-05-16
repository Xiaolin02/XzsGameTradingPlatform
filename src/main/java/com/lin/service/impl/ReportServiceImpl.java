package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReportDTO;
import com.lin.mapper.ReportMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.Report;
import com.lin.service.BasicService;
import com.lin.service.ReportService;
import com.lin.utils.DateUtil;
import com.lin.utils.ParseTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author czh
 * @desc 举报
 * @date 2023/5/4 8:55
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportMapper reportMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ParseTokenUtil parseTokenUtil;

    /**
     * @Author czh
     * @desc 添加举报（用户或商品只能举报一个）
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<Object> insertReport(String token, ReportDTO reportDTO) {
        Integer userId = parseTokenUtil.parseTokenToUserId(token);
        if (reportDTO.getReportedCommodityId() == null && reportDTO.getReportedUserId() == null) {
            return new ResponseResult<>(CodeConstants.CODE_CONFLICT, "至少举报商品或用户之一");
        }
        if (reportDTO.getReportedCommodityId() != null && reportDTO.getReportedUserId() != null) {
            return new ResponseResult<>(CodeConstants.CODE_CONFLICT, "不能同时举报商品和用户");
        }
        Report report = new Report(null, reportDTO.getReason(), userId,
                reportDTO.getReportedUserId(), reportDTO.getReportedCommodityId(),
                DateUtil.getDateTime());
        reportMapper.insert(report);
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "举报成功");
    }

    /**
     * @Author czh
     * @desc 撤销/删除 举报
     * @date 2023/5/4 13:50
     */
    @Override
    public ResponseResult<Object> deleteReport(String token, Integer reportId) {
        Integer userId = parseTokenUtil.parseTokenToUserId(token);
        QueryWrapper<Report> reportQueryWrapper = new QueryWrapper<>();
        reportQueryWrapper.eq("report_id", reportId);
        reportQueryWrapper.eq("reporter_id", userId);
        if (reportMapper.delete(reportQueryWrapper) == 0) {
            return new ResponseResult<>(CodeConstants.CODE_CONFLICT, "找不到这个举报记录，或请求删除者和举报记录举报者不匹配");
        }
        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "成功删除举报记录");
    }
}
