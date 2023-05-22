package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReportCommodityDTO;
import com.lin.controller.DTO.ReportUserDTO;
import com.lin.service.ReportService;
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

    /**
     * @Author czh
     * @desc 新增举报商品（目前可重复举报）
     * @date 2023/5/4 13:50
     */
    @PostMapping("/commodity")
    public ResponseResult<Object> insertReportCommodity(@RequestHeader String token, @RequestBody ReportCommodityDTO reportCommodityDTO) {
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
    public ResponseResult<Object> deleteReportCommodity(@RequestHeader String token, @PathVariable Integer reportId) {
        return reportService.deleteReportCommodity(token, reportId);
    }

    /**
     * @Author czh
     * @desc 查看举报商品（只能查看自己的举报记录）
     * @date 2023/5/20 22:12
     */
    @GetMapping("/commodity")
    public ResponseResult<Object> selectReportCommodity(@RequestHeader String token) {
        return reportService.selectReportCommodity(token);
    }

    /**
     * @Author czh
     * @desc 新增举报用户（目前可重复举报）
     * @date 2023/5/20 22:12
     */
    @PostMapping("/user")
    public ResponseResult<Object> insertReportUser(@RequestHeader String token, @RequestBody ReportUserDTO reportUserDTO) {
        String reason = reportUserDTO.getReason();
        Integer UserId = reportUserDTO.getUserId();
        return reportService.insertReportUser(token, UserId, reason);
    }

    /**
     * @Author czh
     * @desc 删除举报用户（只能删除自己的举报记录）
     * @date 2023/5/20 22:12
     */
    @DeleteMapping("/user/{reportId}")
    public ResponseResult<Object> deleteReportUser(@RequestHeader String token, @PathVariable Integer reportId) {
        return reportService.deleteReportUser(token, reportId);
    }

    /**
     * @Author czh
     * @desc 查看举报用户（只能查看自己的举报记录）
     * @date 2023/5/20 22:12
     */
    @GetMapping("/user")
    public ResponseResult<Object> selectReportUser(@RequestHeader String token) {
        return reportService.selectReportUser(token);
    }

}
