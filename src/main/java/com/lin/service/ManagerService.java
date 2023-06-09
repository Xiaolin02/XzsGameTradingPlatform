package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReportCommodityViewDTO;
import com.lin.controller.DTO.ReportUserViewDTO;
import com.lin.controller.DTO.commodity.CommodityCompleteDTO;
import com.lin.controller.DTO.commodity.CommodityListDTO;
import com.lin.controller.DTO.commodity.CommoditySimpleDTO;
import com.lin.controller.DTO.general.PageDTO;
import com.lin.controller.DTO.user.UserCompleteDTO;
import com.lin.controller.DTO.user.UserListDTO;


/**
 * @Author czh
 * @desc 管理员service
 * @date 2023/5/31 16:10
 */
public interface ManagerService {
    ResponseResult<NullData> userReportDone(Integer reportId);

    ResponseResult<UserListDTO<ReportUserViewDTO>> userReportViewAll(PageDTO pageDTO);

    ResponseResult<UserCompleteDTO> userViewOne(Integer userId);

    ResponseResult<NullData> commodityInspectAllow(Integer commodityId);

    ResponseResult<NullData> commodityInspectReject(Integer commodityId);

    ResponseResult<CommodityListDTO<CommoditySimpleDTO>> commodityInspectViewAll(PageDTO pageDTO);

    ResponseResult<NullData> commodityReportDone(Integer reportId);

    ResponseResult<CommodityListDTO<ReportCommodityViewDTO>> commodityReportViewAll(PageDTO pageDTO);

    ResponseResult<CommodityCompleteDTO> commodityViewOne(Integer commodityId);
}
