package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.CommodityMiniDTO;
import com.lin.controller.DTO.user.UserMiniDTO;

import java.util.List;
import java.util.Map;

/**
 * @Author czh
 * @desc 举报
 * @date 2023/5/4 8:50
 */
public interface ReportService {
    ResponseResult<NullData> insertReportCommodity(String token, Integer commodityId, String reason);

    ResponseResult<NullData> deleteReportCommodity(String token, Integer reportId);

    ResponseResult<Map<String, List<CommodityMiniDTO>>> selectReportCommodity(String token);

    ResponseResult<NullData> insertReportUser(String token, Integer userId, String reason);

    ResponseResult<NullData> deleteReportUser(String token, Integer reportId);

    ResponseResult<Map<String, List<UserMiniDTO>>> selectReportUser(String token);
}
