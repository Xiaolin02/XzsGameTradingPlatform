package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.CommodityListDTO;
import com.lin.controller.DTO.commodity.CommodityMiniDTO;
import com.lin.controller.DTO.user.UserListDTO;
import com.lin.controller.DTO.user.UserMiniDTO;


/**
 * @Author czh
 * @desc 举报
 * @date 2023/5/4 8:50
 */
public interface ReportService {
    ResponseResult<NullData> insertReportCommodity(String token, Integer commodityId, String reason);

    ResponseResult<NullData> deleteReportCommodity(String token, Integer reportId);

    ResponseResult<CommodityListDTO<CommodityMiniDTO>> selectReportCommodity(String token);

    ResponseResult<NullData> insertReportUser(String token, Integer userId, String reason);

    ResponseResult<NullData> deleteReportUser(String token, Integer reportId);

    ResponseResult<UserListDTO<UserMiniDTO>> selectReportUser(String token);
}
