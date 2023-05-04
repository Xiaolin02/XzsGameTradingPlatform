package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReportCommodityDTO;
import com.lin.controller.DTO.ReportDTO;
import com.lin.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author czh
 * @desc TODO
 * @date 2023/5/4 9:14
 */
@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @PostMapping("")
    public ResponseResult<Object> insertReport(@RequestHeader String token, @RequestBody ReportDTO reportDTO) {
        return reportService.insertReport(token, reportDTO);
    }

    @DeleteMapping("/{reportId}")
    public ResponseResult<Object> deleteReport(@RequestHeader String token, @PathVariable Integer reportId) {
        return reportService.deleteReport(token, reportId);
    }

}
