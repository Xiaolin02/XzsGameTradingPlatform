package com.lin.service;

import com.lin.common.ResponseResult;

/**
 * @Author czh
 * @desc 举报
 * @date 2023/5/4 8:50
 */
public interface ReportService {
    ResponseResult<Object> insertReportCommodity(String token, Integer commodityId, String reason);

    ResponseResult<Object> deleteReportCommodity(String token, Integer reportId);

    ResponseResult<Object> selectReportCommodity(String token);

    ResponseResult<Object> insertReportUser(String token, Integer userId, String reason);

    ResponseResult<Object> deleteReportUser(String token, Integer reportId);

    ResponseResult<Object> selectReportUser(String token);
}
