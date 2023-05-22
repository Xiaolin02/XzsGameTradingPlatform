package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReportDTO;

/**
 * @Author czh
 * @desc 举报
 * @date 2023/5/4 8:50
 */
public interface ReportService {
    ResponseResult<Object> insertReport(String token, ReportDTO reportDTO);

    ResponseResult<Object> deleteReport(String token, Integer reportId);
}
