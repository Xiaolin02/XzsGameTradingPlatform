package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.general.PageDTO;

/**
 * @Author czh
 * @desc TODO 管理员service
 * @date 2023/5/31 16:10
 */
public interface ManagerService {
    ResponseResult<Object> UserReportDone(Integer reportId);

    ResponseResult<Object> UserReportViewAll(PageDTO pageDTO);

    ResponseResult<Object> UserViewOne(Integer userId);

    ResponseResult<Object> CommodityInspectAllow(Integer commodityId);

    ResponseResult<Object> CommodityInspectReject(Integer commodityId);

    ResponseResult<Object> CommodityInspectViewAll(PageDTO pageDTO);

    ResponseResult<Object> CommodityReportDone(Integer reportId);

    ResponseResult<Object> CommodityReportViewAll(PageDTO pageDTO);

    ResponseResult<Object> CommodityViewOne(Integer commodityId);
}
