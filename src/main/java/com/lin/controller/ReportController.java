package com.lin.controller;

import com.lin.common.CodeConstants;
import com.lin.common.NullData;
import com.lin.common.ParameterConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReportCommodityDTO;
import com.lin.controller.DTO.ReportUserDTO;
import com.lin.controller.DTO.commodity.CommodityListDTO;
import com.lin.controller.DTO.commodity.CommodityMiniDTO;
import com.lin.controller.DTO.user.UserListDTO;
import com.lin.controller.DTO.user.UserMiniDTO;
import com.lin.service.ReportService;
import com.lin.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author czh
 * @desc 举报：支持 增删 举报记录
 * @date 2023/5/4 9:14
 */
@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    WebUtil webUtil;

    /**
     * @Author czh
     * @desc 新增举报商品（目前可重复举报）
     * @date 2023/5/4 13:50
     */
    @PostMapping("/commodity")
    public ResponseResult<NullData> insertReportCommodity(HttpServletRequest request, @RequestHeader String token, @RequestBody ReportCommodityDTO reportCommodityDTO) {
        String ipAddress = webUtil.getIpAddress(request);
        Integer requestNumber = webUtil.getRequestNumber(Thread.currentThread().getStackTrace()[1].getMethodName(), ipAddress);
        if (requestNumber >= ParameterConstants.MAX_REQUEST_NUMBER) {
            return new ResponseResult<>(CodeConstants.CODE_USER_EXCEPTION, "该Ip短期内对该接口进行大量请求, 已经被限制访问该接口");
        } else {
            webUtil.addRequestNumber(Thread.currentThread().getStackTrace()[1].getMethodName(), ipAddress);
        }
        String reason = reportCommodityDTO.getReason();
        Integer commodityId = reportCommodityDTO.getCommodityId();
        return reportService.insertReportCommodity(token, commodityId, reason);
    }

    /**
     * @Author czh
     * @desc 删除举报商品（只能删除自己的举报记录）
     * @date 2023/5/4 13:50
     */
    @DeleteMapping("/commodity/{reportId}")
    public ResponseResult<NullData> deleteReportCommodity(@RequestHeader String token, @PathVariable Integer reportId) {
        return reportService.deleteReportCommodity(token, reportId);
    }

    /**
     * @Author czh
     * @desc 查看举报商品（只能查看自己的举报记录）
     * @date 2023/5/20 22:12
     */
    @GetMapping("/commodity")
    public ResponseResult<CommodityListDTO<CommodityMiniDTO>> selectReportCommodity(@RequestHeader String token) {
        return reportService.selectReportCommodity(token);
    }

    /**
     * @Author czh
     * @desc 新增举报用户（目前可重复举报）
     * @date 2023/5/20 22:12
     */
    @PostMapping("/user")
    public ResponseResult<NullData> insertReportUser(@RequestHeader String token, @RequestBody ReportUserDTO reportUserDTO) {
        String reason = reportUserDTO.getReason();
        Integer userId = reportUserDTO.getUserId();
        return reportService.insertReportUser(token, userId, reason);
    }

    /**
     * @Author czh
     * @desc 删除举报用户（只能删除自己的举报记录）
     * @date 2023/5/20 22:12
     */
    @DeleteMapping("/user/{reportId}")
    public ResponseResult<NullData> deleteReportUser(@RequestHeader String token, @PathVariable Integer reportId) {
        return reportService.deleteReportUser(token, reportId);
    }

    /**
     * @Author czh
     * @desc 查看举报用户（只能查看自己的举报记录）
     * @date 2023/5/20 22:12
     */
    @GetMapping("/user")
    public ResponseResult<UserListDTO<UserMiniDTO>> selectReportUser(@RequestHeader String token) {
        return reportService.selectReportUser(token);
    }

}
